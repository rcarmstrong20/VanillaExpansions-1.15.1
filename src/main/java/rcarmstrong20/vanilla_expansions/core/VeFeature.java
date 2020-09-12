package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BigRedMushroomFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.gen.feature.structure.VeCabinStructure;

/**
 *
 * @author Ryan
 *
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeFeature
{
    private static final List<Feature<?>> FEATURES = new ArrayList<>();
    private static final List<Structure<?>> STRUCTURES = new ArrayList<>();

    public static final Feature<BigMushroomFeatureConfig> HUGE_PURPLE_MUSHROOM = register("huge_purple_mushroom",
            new BigRedMushroomFeature(BigMushroomFeatureConfig::deserialize));
    public static final Structure<VillageConfig> CABIN = register("cabin",
            new VeCabinStructure(VillageConfig::deserialize));

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
        // Register the features
        FEATURES.forEach(feature -> event.getRegistry().register(feature));
        FEATURES.clear();

        // Register the structures
        STRUCTURES.forEach(structure -> event.getRegistry().register(structure));
        STRUCTURES.clear();

        VanillaExpansions.LOGGER.info("Features registered.");
    }
}