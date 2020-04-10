package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.VeBlockStateProperties;
import rcarmstrong20.vanilla_expansions.util.VeCollisionUtil;

public class VePufferfishPlushBlock extends VePlushBlock
{
	public static final IntegerProperty PUFFED = VeBlockStateProperties.PUFFED_0_2;
	
	//Pufferfish 0 Bounding Boxes
	
	protected static final VoxelShape PUFFERFISH_0_NORTH_BODY_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 3.0D, 11.0D, 4.0D, 11.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 2.0D, 2.5D, 9.0D, 3.0D, 3.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_RIGHT_FIN_SHAPE = Block.makeCuboidShape(4.0D, 1.0D, 3.0D, 5.0D, 2.0D, 8.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_LEFT_FIN_SHAPE = Block.makeCuboidShape(11.0D, 1.0D, 3.0D, 12.0D, 2.0D, 8.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(5.0D, 4.0D, 3.0D, 6.5D, 5.5D, 4.5D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(9.5D, 4.0D, 3.0D, 11.0D, 5.5D, 4.5D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_TAIL_MIDDLE_SHAPE = Block.makeCuboidShape(6.0D, 1.0D, 11.0D, 10.0D, 2.0D, 13.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_TAIL_RIGHT_SHAPE = Block.makeCuboidShape(6.0D, 1.0D, 13.0D, 7.0D, 2.0D, 14.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_TAIL_LEFT_SHAPE = Block.makeCuboidShape(9.0D, 1.0D, 13.0D, 10.0D, 2.0D, 14.0D);
	protected static final VoxelShape PUFFERFISH_0_NORTH_FINS_SHAPE = VoxelShapes.or(PUFFERFISH_0_NORTH_RIGHT_FIN_SHAPE, PUFFERFISH_0_NORTH_LEFT_FIN_SHAPE);
	protected static final VoxelShape PUFFERFISH_0_NORTH_EYES_SHAPE = VoxelShapes.or(PUFFERFISH_0_NORTH_RIGHT_EYE_SHAPE, PUFFERFISH_0_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape PUFFERFISH_0_NORTH_TAIL_SHAPE = VoxelShapes.or(PUFFERFISH_0_NORTH_TAIL_MIDDLE_SHAPE, PUFFERFISH_0_NORTH_TAIL_RIGHT_SHAPE, PUFFERFISH_0_NORTH_TAIL_LEFT_SHAPE);
	protected static final VoxelShape PUFFERFISH_0_NORTH_SHAPE = VoxelShapes.or(PUFFERFISH_0_NORTH_BODY_SHAPE, PUFFERFISH_0_NORTH_MOUTH_SHAPE, PUFFERFISH_0_NORTH_FINS_SHAPE, PUFFERFISH_0_NORTH_EYES_SHAPE, PUFFERFISH_0_NORTH_TAIL_SHAPE);
	
	protected static final VoxelShape PUFFERFISH_0_SOUTH_SHAPE = VeCollisionUtil.rotate180(Axis.Y, PUFFERFISH_0_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_0_WEST_SHAPE = VeCollisionUtil.rotate270(Axis.Y, PUFFERFISH_0_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_0_EAST_SHAPE = VeCollisionUtil.rotate90(Axis.Y, PUFFERFISH_0_NORTH_SHAPE);
	
	//Pufferfish 1 Bounding Boxes
	
	protected static final VoxelShape PUFFERFISH_1_NORTH_BODY_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 3.0D, 12.0D, 8.0D, 11.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_RIGHT_FIN_SHAPE = Block.makeCuboidShape(2.0D, 5.0D, 4.0D, 4.0D, 6.0D, 9.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_LEFT_FIN_SHAPE = Block.makeCuboidShape(12.0D, 5.0D, 4.0D, 14.0D, 6.0D, 9.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_MOUTH_SHAPE = Block.makeCuboidShape(7.5D, 2.0D, 2.5D, 8.5D, 3.0D, 3.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(5.0D, 4.0D, 2.5D, 6.5D, 5.5D, 3.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(9.5D, 4.0D, 2.5D, 11.0D, 5.5D, 3.0D);
	protected static final VoxelShape PUFFERFISH_1_NORTH_FINS_SHAPE = VoxelShapes.or(PUFFERFISH_1_NORTH_RIGHT_FIN_SHAPE, PUFFERFISH_1_NORTH_LEFT_FIN_SHAPE);
	protected static final VoxelShape PUFFERFISH_1_NORTH_EYES_SHAPE = VoxelShapes.or(PUFFERFISH_1_NORTH_RIGHT_EYE_SHAPE, PUFFERFISH_1_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape PUFFERFISH_1_NORTH_SHAPE = VoxelShapes.or(PUFFERFISH_1_NORTH_BODY_SHAPE, PUFFERFISH_1_NORTH_MOUTH_SHAPE, PUFFERFISH_1_NORTH_FINS_SHAPE, PUFFERFISH_1_NORTH_EYES_SHAPE);
	
	protected static final VoxelShape PUFFERFISH_1_SOUTH_SHAPE = VeCollisionUtil.rotate180(Axis.Y, PUFFERFISH_1_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_1_WEST_SHAPE = VeCollisionUtil.rotate270(Axis.Y, PUFFERFISH_1_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_1_EAST_SHAPE = VeCollisionUtil.rotate90(Axis.Y, PUFFERFISH_1_NORTH_SHAPE);
	
	//Pufferfish 2 Bounding Boxes
	
	protected static final VoxelShape PUFFERFISH_2_NORTH_BODY_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 9.0D, 12.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_RIGHT_FIN_SHAPE = Block.makeCuboidShape(1.0D, 6.0D, 4.0D, 3.0D, 7.0D, 9.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_LEFT_FIN_SHAPE = Block.makeCuboidShape(13.0D, 6.0D, 4.0D, 15.0D, 7.0D, 9.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_MOUTH_SHAPE = Block.makeCuboidShape(6.5D, 3.0D, 1.5D, 9.5D, 4.0D, 2.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(4.0D, 5.0D, 1.5D, 7.0D, 6.5D, 2.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(9.0D, 5.0D, 1.5D, 12.0D, 6.5D, 2.0D);
	protected static final VoxelShape PUFFERFISH_2_NORTH_FINS_SHAPE = VoxelShapes.or(PUFFERFISH_2_NORTH_RIGHT_FIN_SHAPE, PUFFERFISH_2_NORTH_LEFT_FIN_SHAPE);
	protected static final VoxelShape PUFFERFISH_2_NORTH_EYES_SHAPE = VoxelShapes.or(PUFFERFISH_2_NORTH_RIGHT_EYE_SHAPE, PUFFERFISH_2_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape PUFFERFISH_2_NORTH_SHAPE = VoxelShapes.or(PUFFERFISH_2_NORTH_BODY_SHAPE, PUFFERFISH_2_NORTH_MOUTH_SHAPE, PUFFERFISH_2_NORTH_FINS_SHAPE, PUFFERFISH_2_NORTH_EYES_SHAPE);
	
	protected static final VoxelShape PUFFERFISH_2_SOUTH_SHAPE = VeCollisionUtil.rotate180(Axis.Y, PUFFERFISH_2_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_2_WEST_SHAPE = VeCollisionUtil.rotate270(Axis.Y, PUFFERFISH_2_NORTH_SHAPE);
	protected static final VoxelShape PUFFERFISH_2_EAST_SHAPE = VeCollisionUtil.rotate90(Axis.Y, PUFFERFISH_2_NORTH_SHAPE);
	
	public VePufferfishPlushBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace)
	{
		int puffed_property = state.get(PUFFED);
		
		if(puffed_property < 2)
		{
			world.playSound(null, pos, SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
			world.setBlockState(pos, state.with(PUFFED, Integer.valueOf(puffed_property + 1)), 3);
		}
		else
		{
			world.playSound(null, pos, SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
			world.setBlockState(pos, state.with(PUFFED, 0), 3);
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return VePufferfishPlushBlock.defineShapes(state, PUFFERFISH_0_NORTH_SHAPE, PUFFERFISH_1_NORTH_SHAPE, PUFFERFISH_2_NORTH_SHAPE, PUFFERFISH_0_SOUTH_SHAPE, PUFFERFISH_1_SOUTH_SHAPE, PUFFERFISH_2_SOUTH_SHAPE, PUFFERFISH_0_WEST_SHAPE, PUFFERFISH_1_WEST_SHAPE, PUFFERFISH_2_WEST_SHAPE, PUFFERFISH_0_EAST_SHAPE, PUFFERFISH_1_EAST_SHAPE, PUFFERFISH_2_EAST_SHAPE);
	}
	
	private static VoxelShape defineShapes(BlockState state, VoxelShape northPuffShape0, VoxelShape northPuffShape1, VoxelShape northPuffShape2, VoxelShape southPuffShape0, VoxelShape southPuffShape1, VoxelShape southPuffShape2, VoxelShape westPuffShape0, VoxelShape westPuffShape1, VoxelShape westPuffShape2, VoxelShape eastPuffShape0, VoxelShape eastPuffShape1, VoxelShape eastPuffShape2)
	{
		switch((Direction)state.get(HORIZONTAL_FACING))
		{
		case NORTH:
			return getPuffedShapes(state, northPuffShape0, northPuffShape1, northPuffShape2);
		case SOUTH:
			return getPuffedShapes(state, southPuffShape0, southPuffShape1, southPuffShape2);
		case WEST:
			return getPuffedShapes(state, westPuffShape0, westPuffShape1, westPuffShape2);
		default:
			return getPuffedShapes(state, eastPuffShape0, eastPuffShape1, eastPuffShape2);
		}
	}
	
	private static VoxelShape getPuffedShapes(BlockState state, VoxelShape puffShape1, VoxelShape puffShape2, VoxelShape puffShape3)
	{
		switch((int)state.get(PUFFED))
		{
		case 0:
			return puffShape1;
		case 1:
			return puffShape2;
		default:
			return puffShape3;
		}
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, WATERLOGGED, PUFFED);
	}
}
