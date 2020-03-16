package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BigRedMushroomFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.gen.feature.structure.CabinPiece;
import rcarmstrong20.vanilla_expansions.gen.feature.structure.CabinStructure;

/**
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeFeature
{
	private static final List<Feature<?>> FEATURES = new ArrayList<>();
	private static final List<IStructurePieceType> STRUCTURE_PIECES = new ArrayList<>();
	private static final List<Structure<?>> STRUCTURES = new ArrayList<>();
	
	public static final IStructurePieceType CABIN_PIECE = register("cabin", CabinPiece::new);
	
	public static final Feature<BigMushroomFeatureConfig> HUGE_PURPLE_MUSHROOM = register("huge_purple_mushroom", new BigRedMushroomFeature(BigMushroomFeatureConfig::deserialize));
	public static final Structure<NoFeatureConfig> CABIN = register("cabin", new CabinStructure(NoFeatureConfig::deserialize));
	
	/**
	 * Set the registry name for the features and add them to the registry list.
	 */
	private static <C extends IFeatureConfig> Feature<C> register(String name, Feature<C> feature)
	{
		feature.setRegistryName(VanillaExpansions.MOD_ID, name);
		FEATURES.add(feature);
		return feature;
	}
	
	/**
	 * Create registers for the structure pieces and add them to the registry list.
	 */
	private static IStructurePieceType register(String name, IStructurePieceType structurePieceType)
	{
		IStructurePieceType pieceRegistry = Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(VanillaExpansions.MOD_ID, name), structurePieceType);
		STRUCTURE_PIECES.add(pieceRegistry);
		return pieceRegistry;
	}
	
	/**
	 * Set the registry name for the structures and add them to the registry list.
	 */
	private static <C extends IFeatureConfig> Structure<C> register(String name, Structure<C> structure)
	{
		structure.setRegistryName(VanillaExpansions.MOD_ID, name);
		STRUCTURES.add(structure);
		return structure;
	}
	
	/**
	 * Register the Features to the game
	 */
	@SubscribeEvent
	public static void registerFeaturesAndStructures(final RegistryEvent.Register<Feature<?>> event)
	{
		//Register the features
		FEATURES.forEach(feature -> event.getRegistry().register(feature));
		FEATURES.clear();
		
		//Register the cabin pieces
		STRUCTURE_PIECES.forEach(structurePiece -> STRUCTURE_PIECES.iterator());
		STRUCTURE_PIECES.clear();
		
		//Register the structures
		STRUCTURES.forEach(structure -> event.getRegistry().register(structure));
		STRUCTURES.clear();
		
		VanillaExpansions.LOGGER.info("Features registered.");
	}
}