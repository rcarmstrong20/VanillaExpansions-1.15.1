package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class VeSinglePaintingBlock extends VeDirectionalBlock
{
	private static final VoxelShape PAINTING_EAST_AABB =  Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	private static final VoxelShape PAINTING_WEST_AABB =  Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape PAINTING_SOUTH_AABB =  Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	private static final VoxelShape PAINTING_NORTH_AABB =  Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	
	public VeSinglePaintingBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	}
	/*
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	*/
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = state.get(HORIZONTAL_FACING);
		BlockState air = Blocks.AIR.getDefaultState();
		direction.getOpposite().getAxisDirection();
		BlockPos blockPosNeg = pos.offset(direction, AxisDirection.NEGATIVE.getOffset());
		direction.getOpposite().getAxisDirection();
		BlockPos blockPosPos = pos.offset(direction, AxisDirection.POSITIVE.getOffset());
		if(worldIn.getBlockState(blockPosNeg) != air || worldIn.getBlockState(blockPosPos) != air)
		{
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch((Direction)state.get(HORIZONTAL_FACING))
		{
			case EAST:
			default:
				return PAINTING_EAST_AABB;
			case WEST:
				return PAINTING_WEST_AABB;
			case SOUTH:
				return PAINTING_SOUTH_AABB;
			case NORTH:
				return PAINTING_NORTH_AABB;
		}
	}
}
