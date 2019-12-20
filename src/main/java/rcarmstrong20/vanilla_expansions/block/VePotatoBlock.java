package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PotatoBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import rcarmstrong20.vanilla_expansions.VeBlockTags;

public class VePotatoBlock extends PotatoBlock
{
	public VePotatoBlock(Block.Properties properties)
	{
		super(properties);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return BlockTags.getCollection().getOrCreate(VeBlockTags.OVERWORLD_SOILS).contains(state.getBlock());
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		//Use the blocks from the isValidGround method.
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}
}
