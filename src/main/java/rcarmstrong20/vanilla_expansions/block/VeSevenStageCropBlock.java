package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.VeBlockTags;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeSevenStageCropBlock extends CropsBlock
{
	public VeSevenStageCropBlock(Block.Properties properties) 
	{
		super(properties);
	}
	
	/**
	 * This method is called when the player right-clicks a block
	 */
	@Override
	public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult p_225533_6_)
	{
		if(!world.isRemote && state.get(this.getAgeProperty()) == this.getMaxAge())
		{
			spawnDrops(state, world, pos);
			world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F + RANDOM.nextFloat(), RANDOM.nextFloat() * 0.7F + 0.3F);
			world.setBlockState(pos, this.getDefaultState().with(this.getAgeProperty(), 0), 2);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
	
	@Override
    protected IItemProvider getSeedsItem()
    {
		Block block = this.getBlock();
		
    	if(block == VeBlocks.bok_choy)
    	{
    		return VeItems.bok_choy_seeds;
    	}
    	else if(block == VeBlocks.ginger)
    	{
    		return VeItems.ginger_root;
    	}
    	else if(block == VeBlocks.quinoa)
    	{
    		return VeItems.quinoa;
    	}
		return VeItems.wheat_seeds;
    }
	
	/**
	 * Decide what blocks can sustain this plant
	 */
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return VeBlockTags.CompareBlock(state.getBlock(), VeBlockTags.OVERWORLD_SOILS);
	}
	
	/**
	 * Use the blocks from the isValidGround method.
	 */
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(this.getAgeProperty());
	}
}
