package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VePlantingPotBlock extends Block implements IWaterLoggable
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private static final VoxelShape PILLAR_1_AABB = Block.makeCuboidShape(4.0, 1.0, 4.0, 6.0, 8.0, 6.0);
	private static final VoxelShape PILLAR_2_AABB = Block.makeCuboidShape(12.0, 1.0, 12.0, 10.0, 8.0, 10.0);
	private static final VoxelShape PILLAR_3_AABB = Block.makeCuboidShape(6.0, 1.0, 12.0, 4.0, 8.0, 10.0);
	private static final VoxelShape PILLAR_4_AABB = Block.makeCuboidShape(12.0, 1.0, 6.0, 10.0, 8.0, 4.0);
	private static final VoxelShape SIDE_1_AABB = Block.makeCuboidShape(2.0, 8.0, 3.0, 14.0, 18.0, 2.0);
	private static final VoxelShape SIDE_2_AABB = Block.makeCuboidShape(3.0, 8.0, 2.0, 2.0, 18.0, 14.0);
	private static final VoxelShape SIDE_3_AABB = Block.makeCuboidShape(13.0, 8.0, 3.0, 14.0, 18.0, 14.0);
	private static final VoxelShape SIDE_4_AABB = Block.makeCuboidShape(3.0, 8.0, 13.0, 14.0, 18.0, 14.0);
	private static final VoxelShape INSIDE_AABB = Block.makeCuboidShape(2.0, 8.0, 2.0, 14.0, 16.0, 14.0);
	private static final VoxelShape BASE_AABB = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 1.0, 14.0);
	private static final VoxelShape LIQUID_AABB = Block.makeCuboidShape(10.0, 1.0, 10.0, 6.0, 8.0, 6.0);
	
	private static final VoxelShape PILLAR_1_AND_2_AABB = VoxelShapes.or(PILLAR_1_AABB, PILLAR_2_AABB);
	private static final VoxelShape PILLAR_3_AND_4_AABB = VoxelShapes.or(PILLAR_3_AABB, PILLAR_4_AABB);
	private static final VoxelShape PILLARS_AABB = VoxelShapes.or(PILLAR_1_AND_2_AABB, PILLAR_3_AND_4_AABB);
	private static final VoxelShape SIDES_1_AND_2_AABB = VoxelShapes.or(SIDE_1_AABB, SIDE_2_AABB);
	private static final VoxelShape SIDES_3_AND_4_AABB = VoxelShapes.or(SIDE_3_AABB, SIDE_4_AABB);
	private static final VoxelShape SIDES_AABB = VoxelShapes.or(SIDES_1_AND_2_AABB, SIDES_3_AND_4_AABB);
	private static final VoxelShape TOP_AABB = VoxelShapes.or(INSIDE_AABB, SIDES_AABB);
	private static final VoxelShape TOP_AND_PILLARS_AABB = VoxelShapes.or(TOP_AABB, PILLARS_AABB);
	private static final VoxelShape TOP_PILLARS_AND_LIQUID_AABB = VoxelShapes.or(TOP_AND_PILLARS_AABB, LIQUID_AABB);
	private static final VoxelShape PLANTING_POT_AABB = VoxelShapes.or(TOP_PILLARS_AND_LIQUID_AABB, BASE_AABB);
	
	public VePlantingPotBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return PLANTING_POT_AABB;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
		return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return stateIn;
	}
	
	@Override
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable)
	{
		PlantType plantType = plantable.getPlantType(world, pos);
		Block plantBlock = plantable.getPlant(world, pos).getBlock();
		if(this == VeBlocks.nether_planting_pot && plantType == PlantType.Nether)
		{
			return true;
		}
		else if(this == VeBlocks.oak_planting_pot || this == VeBlocks.spruce_planting_pot || this == VeBlocks.birch_planting_pot || this == VeBlocks.jungle_planting_pot || this == VeBlocks.acacia_planting_pot || this == VeBlocks.dark_oak_planting_pot)
		{
			if(plantBlock instanceof CropsBlock || plantBlock instanceof SweetBerryBushBlock)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(WATERLOGGED);
	}
}
