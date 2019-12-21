package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.VeBlockTags;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeBerryBushBlock extends SweetBerryBushBlock
{
	private static final VoxelShape WITCHS_CRADLE_STAGE_0_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);
	
	public VeBerryBushBlock(Properties properties)
	{
		super(properties);
	}
	
	private IntegerProperty getAgeProperty()
	{
		return AGE;
	}
	
	private int getMaxAge()
	{
		return 3;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if(this == VeBlocks.witchs_cradle && state.get(AGE) == 0)
		{
			return WITCHS_CRADLE_STAGE_0_SHAPE;
		}
		return super.getShape(state, worldIn, pos, context);
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
	{
		if(this == VeBlocks.blueberry_bush)
		{
			return new ItemStack(VeItems.blueberries);
		}
		else if(this == VeBlocks.cranberry_bush)
		{
			return new ItemStack(VeItems.cranberries);
		}
		else if(this == VeBlocks.witchs_cradle)
		{
			return new ItemStack(VeItems.witchs_cradle_branch);
		}
		return new ItemStack(VeItems.sweet_berries);
	}
	
	/**
	 * This method is called when the player right-clicks a block
	 */
	@Override
	public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult p_225533_6_)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		if(!world.isRemote && state.get(this.getAgeProperty()) == this.getMaxAge() || state.get(this.getAgeProperty()) == this.getMaxAge() - 1 && itemstack.getItem() != Items.BONE_MEAL)
		{
			spawnDrops(state, world, pos);
			world.playSound(null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
			world.setBlockState(pos, this.getDefaultState().with(this.getAgeProperty(), 1), 2);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return VeBlockTags.CompareBlock(state.getBlock(), VeBlockTags.OVERWORLD_GROUNDS) || VeBlockTags.CompareBlock(state.getBlock(), VeBlockTags.OVERWORLD_SOILS);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		//Use the blocks from the isValidGround method.
		BlockPos blockpos = pos.down();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if(this == VeBlocks.sweet_berry_bush || this == VeBlocks.witchs_cradle)
		{
			super.onEntityCollision(state, worldIn, pos, entityIn);
		}
	}
}
