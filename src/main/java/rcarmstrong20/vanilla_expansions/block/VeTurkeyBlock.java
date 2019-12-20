package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class VeTurkeyBlock extends CakeBlock
{
	protected static final VoxelShape[] TURKEY_AABB = new VoxelShape[] {Block.makeCuboidShape(2.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(2.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(4.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(5.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(7.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(9.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D), Block.makeCuboidShape(11.5D, 1.0D, 3.5D, 12.5D, 6.5D, 12.5D)};
	protected static final VoxelShape PLATE_TOP_1_AABB = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 1.0D, 2.0D, 16.0D);
	protected static final VoxelShape PLATE_TOP_2_AABB = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 2.0D, 1.0D);
	protected static final VoxelShape PLATE_TOP_3_AABB = Block.makeCuboidShape(0.0D, 1.0D, 15.0D, 16.0D, 2.0D, 16.0D);
	protected static final VoxelShape PLATE_TOP_4_AABB = Block.makeCuboidShape(15.0D, 1.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	protected static final VoxelShape PLATE_BOTTOM_AABB = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
	protected static final VoxelShape PLATE_TOP_1_AND_2_AABB = VoxelShapes.or(PLATE_TOP_1_AABB, PLATE_TOP_2_AABB);
	protected static final VoxelShape PLATE_TOP_3_AND_4_AABB = VoxelShapes.or(PLATE_TOP_3_AABB, PLATE_TOP_4_AABB);
	protected static final VoxelShape PLATE_TOP_AABB = VoxelShapes.or(PLATE_TOP_1_AND_2_AABB, PLATE_TOP_3_AND_4_AABB);
	protected static final VoxelShape PLATE_AABB = VoxelShapes.or(PLATE_TOP_AABB, PLATE_BOTTOM_AABB);
	protected static final VoxelShape[] TURKEY_STAGES_AABB = new VoxelShape[] {VoxelShapes.or(TURKEY_AABB[0], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[1], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[2], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[3], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[4], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[5], PLATE_AABB), VoxelShapes.or(TURKEY_AABB[6], PLATE_AABB)};
	
	public VeTurkeyBlock(Block.Properties properties)
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		int i = state.get(BITES);
		return TURKEY_STAGES_AABB[i];
	}
	
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			return this.eatTurkey(worldIn, pos, state, player);
		}
		else
		{
			ItemStack itemstack = player.getHeldItem(handIn);
			return this.eatTurkey(worldIn, pos, state, player) || itemstack.isEmpty();
		}
	}
	
	private boolean eatTurkey(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		if (player.canEat(false))
		{
			player.getFoodStats().addStats(3, 0.5F);
			int i = state.get(BITES);
			
			if (i < 6)
			{
				worldIn.setBlockState(pos, state.with(BITES, Integer.valueOf(i + 1)), 3);
			}
			else
			{
				worldIn.removeBlock(pos, false);
			}
			return true;
		}
		return false;
	}
}
