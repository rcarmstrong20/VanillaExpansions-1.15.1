package rcarmstrong20.vanilla_expansions.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.VeBlockStateProperties;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VeMultiPlushBlock extends VePlushBlock
{
	//Block Properties
	
	public static final IntegerProperty PLUSH_STACK = VeBlockStateProperties.PLUSH_STACK_1_3;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
	//One Magma Cube Bounding Boxes
	
	protected static final VoxelShape ONE_MAGMA_CUBE_BODY_AABB = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
	protected static final VoxelShape ONE_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB = Block.makeCuboidShape(4.0D, 5.0D, 13.0D, 6.0D, 7.0D, 13.5D);
	protected static final VoxelShape ONE_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB = Block.makeCuboidShape(10.0D, 5.0D, 13.0D, 12.0D, 7.0D, 13.5D);
	protected static final VoxelShape ONE_MAGMA_CUBE_SOUTH_EYES_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB, ONE_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_SOUTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_SOUTH_EYES_AABB, ONE_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_NORTH_LEFT_EYE_AABB = Block.makeCuboidShape(10.0D, 5.0D, 3.0D, 12.0D, 7.0D, 2.5D);
	protected static final VoxelShape ONE_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB = Block.makeCuboidShape(4.0D, 5.0D, 3.0D, 6.0D, 7.0D, 2.5D);
	protected static final VoxelShape ONE_MAGMA_CUBE_BODY_AND_NORTH_RIGHT_EYE_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AABB, ONE_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_NORTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AND_NORTH_RIGHT_EYE_AABB, ONE_MAGMA_CUBE_NORTH_LEFT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_WEST_LEFT_EYE_AABB = Block.makeCuboidShape(2.5D, 5.0D, 10.0D, 3.0D, 7.0D, 12.0D);
	protected static final VoxelShape ONE_MAGMA_CUBE_WEST_RIGHT_EYE_AABB = Block.makeCuboidShape(2.5D, 5.0D, 4.0D, 3.0D, 7.0D, 6.0D);
	protected static final VoxelShape ONE_MAGMA_CUBE_BODY_AND_WEST_RIGHT_EYE_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AABB, ONE_MAGMA_CUBE_WEST_RIGHT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_WEST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AND_WEST_RIGHT_EYE_AABB, ONE_MAGMA_CUBE_WEST_LEFT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_EAST_LEFT_EYE_AABB = Block.makeCuboidShape(13.0D, 5.0D, 10.0D, 13.5D, 7.0D, 12.0D);
	protected static final VoxelShape ONE_MAGMA_CUBE_EAST_RIGHT_EYE_AABB = Block.makeCuboidShape(13.0D, 5.0D, 4.0D, 13.5D, 7.0D, 6.0D);
	protected static final VoxelShape ONE_MAGMA_CUBE_BODY_AND_EAST_RIGHT_EYE_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AABB, ONE_MAGMA_CUBE_EAST_RIGHT_EYE_AABB);
	protected static final VoxelShape ONE_MAGMA_CUBE_EAST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_BODY_AND_EAST_RIGHT_EYE_AABB, ONE_MAGMA_CUBE_EAST_LEFT_EYE_AABB);
	
	//Two Magma Cube Bounding Box
	
	protected static final VoxelShape TWO_MAGMA_CUBE_BODY_AABB = Block.makeCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 17.0D, 12.0D);
	protected static final VoxelShape TWO_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB = Block.makeCuboidShape(5.5D, 14.0D, 12.0D, 7.0D, 15.5D, 12.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB = Block.makeCuboidShape(9.5D, 14.0D, 12.0D, 11.0D, 15.5D, 12.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_SOUTH_EYES_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB, TWO_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_SOUTH_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_SOUTH_EYES_AABB, TWO_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape ONE_AND_TWO_MAGMA_CUBE_SOUTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_SOUTH_AABB, TWO_MAGMA_CUBE_SOUTH_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_NORTH_LEFT_EYE_AABB = Block.makeCuboidShape(9.0D, 14.0D, 4.0D, 10.5D, 15.5D, 3.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB = Block.makeCuboidShape(5.0D, 14.0D, 4.0D, 6.5D, 15.5D, 3.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_NORTH_EYES_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_NORTH_LEFT_EYE_AABB, TWO_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_NORTH_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_NORTH_EYES_AABB, TWO_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape ONE_AND_TWO_MAGMA_CUBE_NORTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_NORTH_AABB, TWO_MAGMA_CUBE_NORTH_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_WEST_LEFT_EYE_AABB = Block.makeCuboidShape(3.5D, 14.0D, 9.5D, 4.0D, 15.5D, 11.0D);
	protected static final VoxelShape TWO_MAGMA_CUBE_WEST_RIGHT_EYE_AABB = Block.makeCuboidShape(3.5D, 14.0D, 5.5D, 4.0D, 15.5D, 7.0D);
	protected static final VoxelShape TWO_MAGMA_CUBE_WEST_EYES_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_WEST_LEFT_EYE_AABB, TWO_MAGMA_CUBE_WEST_RIGHT_EYE_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_WEST_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_WEST_EYES_AABB, TWO_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape ONE_AND_TWO_MAGMA_CUBE_WEST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_WEST_AABB, TWO_MAGMA_CUBE_WEST_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_EAST_LEFT_EYE_AABB = Block.makeCuboidShape(12.0D, 14.0D, 9.0D, 12.5D, 15.5D, 10.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_EAST_RIGHT_EYE_AABB = Block.makeCuboidShape(12.0D, 14.0D, 5.0D, 12.5D, 15.5D, 6.5D);
	protected static final VoxelShape TWO_MAGMA_CUBE_EAST_EYES_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_EAST_LEFT_EYE_AABB, TWO_MAGMA_CUBE_EAST_RIGHT_EYE_AABB);
	protected static final VoxelShape TWO_MAGMA_CUBE_EAST_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_EAST_EYES_AABB, TWO_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape ONE_AND_TWO_MAGMA_CUBE_EAST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_EAST_AABB, TWO_MAGMA_CUBE_EAST_AABB);
	
	//Three Magma Cube Bounding Box
	
	protected static final VoxelShape THREE_MAGMA_CUBE_BODY_AABB = Block.makeCuboidShape(6.0D, 1.0D, 6.0D, 10.0D, 5.0D, 10.0D);
	protected static final VoxelShape THREE_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB = Block.makeCuboidShape(6.5D, 3.0D, 10.0D, 7.5D, 4.0D, 10.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB = Block.makeCuboidShape(8.5D, 3.0D, 10.0D, 9.5D, 4.0D, 10.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_SOUTH_EYES_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_SOUTH_LEFT_EYE_AABB, THREE_MAGMA_CUBE_SOUTH_RIGHT_EYE_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_SOUTH_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_SOUTH_EYES_AABB, THREE_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_NORTH_LEFT_EYE_AABB = Block.makeCuboidShape(6.5D, 3.0D, 6.0D, 7.5D, 4.0D, 5.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB = Block.makeCuboidShape(8.5D, 3.0D, 6.0D, 9.5D, 4.0D, 5.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_NORTH_EYES_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_NORTH_LEFT_EYE_AABB, THREE_MAGMA_CUBE_NORTH_RIGHT_EYE_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_NORTH_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_NORTH_EYES_AABB, THREE_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_WEST_LEFT_EYE_AABB = Block.makeCuboidShape(5.5D, 3.0D, 6.5D, 6.0D, 4.0D, 7.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_WEST_RIGHT_EYE_AABB = Block.makeCuboidShape(5.5D, 3.0D, 9.5D, 6.0D, 4.0D, 8.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_WEST_EYES_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_WEST_LEFT_EYE_AABB, THREE_MAGMA_CUBE_WEST_RIGHT_EYE_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_WEST_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_WEST_EYES_AABB, THREE_MAGMA_CUBE_BODY_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_EAST_LEFT_EYE_AABB = Block.makeCuboidShape(10.5D, 3.0D, 6.5D, 10.0D, 4.0D, 7.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_EAST_RIGHT_EYE_AABB = Block.makeCuboidShape(10.5D, 3.0D, 9.5D, 10.0D, 4.0D, 8.5D);
	protected static final VoxelShape THREE_MAGMA_CUBE_EAST_EYES_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_EAST_LEFT_EYE_AABB, THREE_MAGMA_CUBE_EAST_RIGHT_EYE_AABB);
	protected static final VoxelShape THREE_MAGMA_CUBE_EAST_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_EAST_EYES_AABB, THREE_MAGMA_CUBE_BODY_AABB);
	
	//One Slime Bounding Boxes
	
	protected static final VoxelShape ONE_SLIME_SOUTH_MOUTH_AABB = Block.makeCuboidShape(9.0D, 2.0D, 13.0D, 10.0D, 3.0D, 13.5D);
	protected static final VoxelShape ONE_SLIME_SOUTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_SOUTH_AABB, ONE_SLIME_SOUTH_MOUTH_AABB);
	protected static final VoxelShape ONE_SLIME_NORTH_MOUTH_AABB = Block.makeCuboidShape(7.0D, 2.0D, 3.0D, 6.0D, 3.0D, 2.5D);
	protected static final VoxelShape ONE_SLIME_NORTH_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_NORTH_AABB, ONE_SLIME_NORTH_MOUTH_AABB);
	protected static final VoxelShape ONE_SLIME_WEST_MOUTH_AABB = Block.makeCuboidShape(2.5D, 2.0D, 9.0D, 3.0D, 3.0D, 10.0D);
	protected static final VoxelShape ONE_SLIME_WEST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_WEST_AABB, ONE_SLIME_WEST_MOUTH_AABB);
	protected static final VoxelShape ONE_SLIME_EAST_MOUTH_AABB = Block.makeCuboidShape(13.0D, 2.0D, 6.0D, 13.5D, 3.0D, 7.0D);
	protected static final VoxelShape ONE_SLIME_EAST_AABB = VoxelShapes.or(ONE_MAGMA_CUBE_EAST_AABB, ONE_SLIME_EAST_MOUTH_AABB);
	
	//Two Slime Bounding Boxes
	
	protected static final VoxelShape TWO_SLIME_SOUTH_MOUTH_EYE_AABB = Block.makeCuboidShape(8.5D, 11.5D, 12.0D, 9.5D, 12.5D, 12.5D);
	protected static final VoxelShape TWO_SLIME_SOUTH_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_SOUTH_AABB, TWO_SLIME_SOUTH_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_AND_TWO_SLIME_SOUTH_AABB = VoxelShapes.or(ONE_SLIME_SOUTH_AABB, TWO_SLIME_SOUTH_AABB);
	protected static final VoxelShape TWO_SLIME_NORTH_MOUTH_EYE_AABB = Block.makeCuboidShape(6.5D, 11.5D, 4.0D, 7.5D, 12.5D, 3.5D);
	protected static final VoxelShape TWO_SLIME_NORTH_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_NORTH_AABB, TWO_SLIME_NORTH_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_AND_TWO_SLIME_NORTH_AABB = VoxelShapes.or(ONE_SLIME_NORTH_AABB, TWO_SLIME_NORTH_AABB);
	protected static final VoxelShape TWO_SLIME_WEST_MOUTH_EYE_AABB = Block.makeCuboidShape(3.5D, 11.5D, 8.5D, 4.0D, 12.5D, 9.5D);
	protected static final VoxelShape TWO_SLIME_WEST_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_WEST_AABB, TWO_SLIME_WEST_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_AND_TWO_SLIME_WEST_AABB = VoxelShapes.or(ONE_SLIME_WEST_AABB, TWO_SLIME_WEST_AABB);
	protected static final VoxelShape TWO_SLIME_EAST_MOUTH_EYE_AABB = Block.makeCuboidShape(12.0D, 11.5D, 6.5D, 12.5D, 12.5D, 7.5D);
	protected static final VoxelShape TWO_SLIME_EAST_AABB = VoxelShapes.or(TWO_MAGMA_CUBE_EAST_AABB, TWO_SLIME_EAST_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_AND_TWO_SLIME_EAST_AABB = VoxelShapes.or(ONE_SLIME_EAST_AABB, TWO_SLIME_EAST_AABB);
	
	//Three Slime Bunding Boxes
	
	protected static final VoxelShape THREE_SLIME_SOUTH_MOUTH_EYE_AABB = Block.makeCuboidShape(8.5D, 2.0D, 10.0D, 9.0D, 2.5D, 10.5D);
	protected static final VoxelShape THREE_SLIME_SOUTH_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_SOUTH_AABB, THREE_SLIME_SOUTH_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_TWO_AND_THREE_SLIME_SOUTH_AABB = VoxelShapes.or(ONE_AND_TWO_SLIME_SOUTH_AABB, THREE_SLIME_SOUTH_AABB);
	protected static final VoxelShape THREE_SLIME_NORTH_MOUTH_EYE_AABB = Block.makeCuboidShape(7.0D, 2.0D, 6.0D, 7.5D, 2.5D, 5.5D);
	protected static final VoxelShape THREE_SLIME_NORTH_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_NORTH_AABB, THREE_SLIME_NORTH_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_TWO_AND_THREE_SLIME_NORTH_AABB = VoxelShapes.or(ONE_AND_TWO_SLIME_NORTH_AABB, THREE_SLIME_NORTH_AABB);
	protected static final VoxelShape THREE_SLIME_WEST_MOUTH_EYE_AABB = Block.makeCuboidShape(6.0D, 2.0D, 8.5D, 5.5D, 2.5D, 9.0D);
	protected static final VoxelShape THREE_SLIME_WEST_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_WEST_AABB, THREE_SLIME_WEST_MOUTH_EYE_AABB);
	protected static final VoxelShape ONE_TWO_AND_THREE_SLIME_WEST_AABB = VoxelShapes.or(ONE_AND_TWO_SLIME_WEST_AABB, THREE_SLIME_WEST_AABB);
	protected static final VoxelShape THREE_SLIME_EAST_MOUTH_EYE_AABB = Block.makeCuboidShape(10.5D, 2.0D, 7.0D, 10.0D, 2.5D, 7.5D);
	protected static final VoxelShape THREE_SLIME_EAST_AABB = VoxelShapes.or(THREE_MAGMA_CUBE_EAST_AABB, THREE_SLIME_EAST_MOUTH_EYE_AABB);
	
	public VeMultiPlushBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(PLUSH_STACK, 1));
	}
	/*
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	*/
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if(state.get(PLUSH_STACK) == 3)
		{
			worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
		}
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if (blockstate.getBlock() == this)
		{
			return blockstate.with(PLUSH_STACK, Integer.valueOf(Math.min(3, blockstate.get(PLUSH_STACK) + 1)));
		}
		else
		{
			IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
			boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
			return super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(flag));
		}
	}
	
	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		BlockPos pos = useContext.getPos().up();
		return useContext.getItem().getItem() == this.asItem() && state.get(PLUSH_STACK) == 1 || useContext.getWorld().getBlockState(pos) == Blocks.AIR.getDefaultState() && state.get(PLUSH_STACK) < 3 ? true : false;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		if(state.has(HALF))
		{
			if(state.get(HALF) == DoubleBlockHalf.UPPER)
			{
				worldIn.destroyBlock(pos.down(), false);
			}
			else if(state.get(HALF) == DoubleBlockHalf.LOWER)
			{
				worldIn.destroyBlock(pos.up(), false);
			}
			if(player.isCreative())
			{
				worldIn.destroyBlock(pos, false);
			}
			else
			{
				worldIn.destroyBlock(pos, true);
			}
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, WATERLOGGED, PLUSH_STACK, HALF);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if(this == VeBlocks.slime_plush)
		{
			return VeMultiPlushBlock.defineShapes(state, ONE_SLIME_SOUTH_AABB, ONE_AND_TWO_SLIME_SOUTH_AABB, THREE_SLIME_SOUTH_AABB, ONE_SLIME_NORTH_AABB, ONE_AND_TWO_SLIME_NORTH_AABB, THREE_SLIME_NORTH_AABB, ONE_SLIME_WEST_AABB, ONE_AND_TWO_SLIME_WEST_AABB, THREE_SLIME_WEST_AABB, ONE_SLIME_EAST_AABB, ONE_AND_TWO_SLIME_EAST_AABB, THREE_SLIME_EAST_AABB);
		}
		else if(this == VeBlocks.magma_cube_plush)
		{
			return VeMultiPlushBlock.defineShapes(state, ONE_MAGMA_CUBE_SOUTH_AABB, ONE_AND_TWO_MAGMA_CUBE_SOUTH_AABB, THREE_MAGMA_CUBE_SOUTH_AABB, ONE_MAGMA_CUBE_NORTH_AABB, ONE_AND_TWO_MAGMA_CUBE_NORTH_AABB, THREE_MAGMA_CUBE_NORTH_AABB, ONE_MAGMA_CUBE_WEST_AABB, ONE_AND_TWO_MAGMA_CUBE_WEST_AABB, THREE_MAGMA_CUBE_WEST_AABB, ONE_MAGMA_CUBE_EAST_AABB, ONE_AND_TWO_MAGMA_CUBE_EAST_AABB, THREE_MAGMA_CUBE_EAST_AABB);
		}
		return NORMAL_CUBE;
	}
	
	private static VoxelShape defineShapes(BlockState state, VoxelShape southShape1, VoxelShape southShape2, VoxelShape southShape3, VoxelShape northShape1, VoxelShape northShape2, VoxelShape northShape3, VoxelShape westShape1, VoxelShape westShape2, VoxelShape westShape3, VoxelShape eastShape1, VoxelShape eastShape2, VoxelShape eastShape3)
	{
		if(state.get(HORIZONTAL_FACING) == Direction.SOUTH)
		{
			if(state.get(PLUSH_STACK) == 1)
			{
				return southShape1;
			}
			else if(state.get(PLUSH_STACK) == 2)
			{
				return southShape2;
			}
			else if(state.get(PLUSH_STACK) == 3)
			{
				return state.get(HALF) == DoubleBlockHalf.UPPER ? southShape3 : southShape2;
			}
		}
		else if(state.get(HORIZONTAL_FACING) == Direction.NORTH)
		{
			if(state.get(PLUSH_STACK) == 1)
			{
				return northShape1;
			}
			else if(state.get(PLUSH_STACK) == 2)
			{
				return northShape2;
			}
			else if(state.get(PLUSH_STACK) == 3)
			{
				return state.get(HALF) == DoubleBlockHalf.UPPER ? northShape3 : northShape2;
			}
		}
		else if(state.get(HORIZONTAL_FACING) == Direction.WEST)
		{
			if(state.get(PLUSH_STACK) == 1)
			{
				return westShape1;
			}
			else if(state.get(PLUSH_STACK) == 2)
			{
				return westShape2;
			}
			else if(state.get(PLUSH_STACK) == 3)
			{
				return state.get(HALF) == DoubleBlockHalf.UPPER ? westShape3 : westShape2;
			}
		}
		else if(state.get(HORIZONTAL_FACING) == Direction.EAST)
		{
			if(state.get(PLUSH_STACK) == 1)
			{
				return eastShape1;
			}
			else if(state.get(PLUSH_STACK) == 2)
			{
				return eastShape2;
			}
			else if(state.get(PLUSH_STACK) == 3)
			{
				return state.get(HALF) == DoubleBlockHalf.UPPER ? eastShape3 : eastShape2;
			}
		}
		return NORMAL_CUBE;
	}
}
