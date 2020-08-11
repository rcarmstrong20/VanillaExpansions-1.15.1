package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

public class VeBlockTags
{
	public static final Tag<Block> OVERWORLD_PLANTABLE = makeWrapperTag("overworld_plantable");
	public static final Tag<Block> NETHER_PLANTABLE = makeWrapperTag("nether_plantable");
	public static final Tag<Block> END_PLANTABLE = makeWrapperTag("end_plantable");
	public static final Tag<Block> OVERWORLD_POTTABLE = makeWrapperTag("overworld_pottable");
	public static final Tag<Block> NETHER_POTTABLE = makeWrapperTag("nether_pottable");
	public static final Tag<Block> END_POTTABLE = makeWrapperTag("end_pottable");
	
	private static Tag<Block> makeWrapperTag(String name)
	{
		return new BlockTags.Wrapper(new ResourceLocation(VanillaExpansions.MOD_ID, name));
	}
}
