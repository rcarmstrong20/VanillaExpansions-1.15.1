package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VeGlassBlock extends GlassBlock
{
	public VeGlassBlock(Block.Properties properties)
	{
		super(properties);
	}
	
	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		if(this == VeBlocks.glass_of_darkness)
		{
			return worldIn.getMaxLightLevel();
		}
		return state.propagatesSkylightDown(worldIn, pos) ? 0 : 1;
	}
}