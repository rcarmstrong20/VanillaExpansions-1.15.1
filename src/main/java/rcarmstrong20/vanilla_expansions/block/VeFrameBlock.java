package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeItemTags;
import rcarmstrong20.vanilla_expansions.tile_entity.VeFrameTileEntity;
import rcarmstrong20.vanilla_expansions.util.VeCollisionUtil;

public class VeFrameBlock extends ContainerBlock implements IWaterLoggable
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private static final VoxelShape PAINTING_NORTH_BACK_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape PAINTING_NORTH_RIGHT_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 1.0D, 16.0D, 15.0D);
	private static final VoxelShape PAINTING_NORTH_LEFT_SHAPE =  Block.makeCuboidShape(15.0D, 0.0D, 14.0D, 16.0D, 16.0D, 15.0D);
	private static final VoxelShape PAINTING_NORTH_TOP_SHAPE =  Block.makeCuboidShape(0.0D, 15.0D, 14.0D, 16.0D, 16.0D, 15.0D);
	private static final VoxelShape PAINTING_NORTH_BOTTOM_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 1.0D, 15.0D);
	
	private static final VoxelShape PAINTING_NORTH_SHAPE = VoxelShapes.or(PAINTING_NORTH_BACK_SHAPE, PAINTING_NORTH_RIGHT_SHAPE, PAINTING_NORTH_LEFT_SHAPE, PAINTING_NORTH_TOP_SHAPE, PAINTING_NORTH_BOTTOM_SHAPE);
	
	private static final VoxelShape PAINTING_SOUTH_SHAPE = VeCollisionUtil.rotate180(Axis.Y, PAINTING_NORTH_SHAPE);
	private static final VoxelShape PAINTING_WEST_SHAPE = VeCollisionUtil.rotate270(Axis.Y, PAINTING_NORTH_SHAPE);
	private static final VoxelShape PAINTING_EAST_SHAPE = VeCollisionUtil.rotate90(Axis.Y, PAINTING_NORTH_SHAPE);
	
	public VeFrameBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult rayTrace)
	{
		ItemStack heldItem = player.getHeldItem(handIn);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos);
			
			//If the inventory slot is empty and the paintings tag contains the block add the item and consume it
			if(!worldIn.isRemote && frameTileEntity.getInventory().get(0) == ItemStack.EMPTY && VeItemTags.PAINTINGS.contains(heldItem.getItem()))
			{
				frameTileEntity.addItem(heldItem);
				worldIn.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState northState = worldIn.getBlockState(pos.offset(Direction.NORTH.getOpposite()));
		BlockState southState = worldIn.getBlockState(pos.offset(Direction.SOUTH.getOpposite()));
		BlockState westState = worldIn.getBlockState(pos.offset(Direction.WEST.getOpposite()));
		BlockState eastState = worldIn.getBlockState(pos.offset(Direction.EAST.getOpposite()));
		
		if(northState.isSolid() && state.get(FACING) == Direction.NORTH ||
		   southState.isSolid() && state.get(FACING) == Direction.SOUTH ||
		   westState.isSolid() && state.get(FACING) == Direction.WEST   ||
		   eastState.isSolid() && state.get(FACING) == Direction.EAST)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		IWorld iworld = context.getWorld();
		BlockPos blockpos = context.getPos();
		boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
		return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag)).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		
		if(facing.getOpposite() == stateIn.get(FACING) && !stateIn.isValidPosition(worldIn, currentPos))
		{
			this.onBlockHarvested(worldIn.getWorld(), currentPos, stateIn, null);
			return Blocks.AIR.getDefaultState();
		}
		else
		{
			return stateIn;
		}
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) tileentity;
			
			InventoryHelper.dropItems(worldIn, pos, frameTileEntity.getInventory());
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch((Direction)state.get(FACING))
		{
			case EAST:
			default:
				return PAINTING_EAST_SHAPE;
			case WEST:
				return PAINTING_WEST_SHAPE;
			case SOUTH:
				return PAINTING_SOUTH_SHAPE;
			case NORTH:
				return PAINTING_NORTH_SHAPE;
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new VeFrameTileEntity();
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, WATERLOGGED);
	}
}
