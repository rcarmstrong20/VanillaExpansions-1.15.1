package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.gen.feature.structure.VeCabinPieces;

/**
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeStructurePieceTypes
{
	public static final IStructurePieceType CABIN_PIECE = register(VanillaExpansions.MOD_ID, "cabin", VeCabinPieces.VePiece::new);
	
	/**
	 * A registry method for registering new structure pieces.
	 */
	private static IStructurePieceType register(String id, String name, IStructurePieceType type)
	{
		return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(id, name), type);
	}
}
