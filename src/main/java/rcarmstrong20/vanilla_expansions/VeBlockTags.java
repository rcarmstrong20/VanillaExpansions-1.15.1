package rcarmstrong20.vanilla_expansions;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;

public class VeBlockTags
{
	public static final ResourceLocation OVERWORLD_SOILS = VanillaExpansions.location("overworld_soils");
	public static final ResourceLocation NETHER_SOILS = VanillaExpansions.location("nether_soils");
	public static final ResourceLocation END_SOILS = VanillaExpansions.location("end_soils");
	public static final ResourceLocation OVERWORLD_GROUNDS = VanillaExpansions.location("overworld_grounds");
	public static final ResourceLocation OVERWORLD_SAPLING_GROUNDS = VanillaExpansions.location("overworld_sapling_grounds");
	public static final ResourceLocation OVERWORLD_SANDY_GROUNDS = VanillaExpansions.location("overworld_sandy_grounds");
	
	public static boolean CompareBlock(Block block, ResourceLocation tagLocation)
	{
		return BlockTags.getCollection().getOrCreate(tagLocation).getAllElements().contains(block);
	}
}