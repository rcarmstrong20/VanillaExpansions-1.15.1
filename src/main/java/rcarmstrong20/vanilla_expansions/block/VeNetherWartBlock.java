package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import rcarmstrong20.vanilla_expansions.VeBlockTags;

public class VeNetherWartBlock extends NetherWartBlock
{
	public VeNetherWartBlock(Block.Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return VeBlockTags.CompareBlock(state.getBlock(), VeBlockTags.NETHER_SOILS);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		//Use the blocks from the isValidGround method.
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}
}
