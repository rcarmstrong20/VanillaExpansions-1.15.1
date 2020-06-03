package rcarmstrong20.vanilla_expansions.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
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
import net.minecraft.util.NonNullList;
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
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.core.VeItemTags;
import rcarmstrong20.vanilla_expansions.core.VeItems;
import rcarmstrong20.vanilla_expansions.tile_entity.VeFrameTileEntity;
import rcarmstrong20.vanilla_expansions.util.VeCollisionUtil;

public class VeFrameBlock extends ContainerBlock implements IWaterLoggable
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty UP = SixWayBlock.UP;
	public static final BooleanProperty DOWN = SixWayBlock.DOWN;
	
	private static final VoxelShape FRAME_NORTH_BACK_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape FRAME_NORTH_RIGHT_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 1.0D, 16.0D, 15.0D);
	private static final VoxelShape FRAME_NORTH_LEFT_SHAPE =  Block.makeCuboidShape(15.0D, 0.0D, 14.0D, 16.0D, 16.0D, 15.0D);
	private static final VoxelShape FRAME_NORTH_TOP_SHAPE =  Block.makeCuboidShape(0.0D, 15.0D, 14.0D, 16.0D, 16.0D, 15.0D);
	private static final VoxelShape FRAME_NORTH_BOTTOM_SHAPE =  Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 1.0D, 15.0D);
	
	private static final VoxelShape FRAME_NORTH_TOP_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_TOP_SHAPE);
	private static final VoxelShape FRAME_NORTH_BOTTOM_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_BOTTOM_SHAPE);
	private static final VoxelShape FRAME_NORTH_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_RIGHT_SHAPE);
	private static final VoxelShape FRAME_NORTH_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_LEFT_SHAPE);
	private static final VoxelShape FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_TOP_SHAPE, FRAME_NORTH_RIGHT_SHAPE);
	private static final VoxelShape FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_TOP_SHAPE, FRAME_NORTH_LEFT_SHAPE);
	private static final VoxelShape FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_BOTTOM_SHAPE, FRAME_NORTH_RIGHT_SHAPE);
	private static final VoxelShape FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_BOTTOM_SHAPE, FRAME_NORTH_LEFT_SHAPE);
	private static final VoxelShape FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_RIGHT_SHAPE, FRAME_NORTH_LEFT_SHAPE);
	private static final VoxelShape FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_TOP_SHAPE, FRAME_NORTH_BOTTOM_SHAPE);
	private static final VoxelShape FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE, FRAME_NORTH_TOP_SHAPE, FRAME_NORTH_RIGHT_SHAPE, FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																								 FRAME_NORTH_BOTTOM_SHAPE,
																								 FRAME_NORTH_RIGHT_SHAPE,
																								 FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																							   FRAME_NORTH_TOP_SHAPE,
																							   FRAME_NORTH_BOTTOM_SHAPE,
																							   FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																								FRAME_NORTH_TOP_SHAPE,
																								FRAME_NORTH_BOTTOM_SHAPE,
																								FRAME_NORTH_RIGHT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_ALL_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																		   FRAME_NORTH_RIGHT_SHAPE,
																		   FRAME_NORTH_LEFT_SHAPE,
																		   FRAME_NORTH_TOP_SHAPE,
																		   FRAME_NORTH_BOTTOM_SHAPE);
	
	private static final VoxelShape FRAME_SOUTH_TOP_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_LEFT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_RIGHT_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_AND_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_SOUTH_ALL_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_ALL_SHAPE);
	private static final VoxelShape FRAME_SOUTH_BACK_SHAPE = VeCollisionUtil.rotate180(Axis.Y, FRAME_NORTH_BACK_SHAPE);
	
	private static final VoxelShape FRAME_WEST_TOP_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_LEFT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_RIGHT_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_AND_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_WEST_ALL_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_ALL_SHAPE);
	private static final VoxelShape FRAME_WEST_BACK_SHAPE = VeCollisionUtil.rotate270(Axis.Y, FRAME_NORTH_BACK_SHAPE);
	
	private static final VoxelShape FRAME_EAST_TOP_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_LEFT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_RIGHT_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_AND_BOTTOM_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_BOTTOM_AND_LEFT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE);
	private static final VoxelShape FRAME_EAST_ALL_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_ALL_SHAPE);
	private static final VoxelShape FRAME_EAST_BACK_SHAPE = VeCollisionUtil.rotate90(Axis.Y, FRAME_NORTH_BACK_SHAPE);
	
	public VeFrameBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)).with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false).with(UP, false).with(DOWN, false));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult rayTrace)
	{
		ItemStack heldItem = player.getHeldItem(handIn);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		Map<Item, Item> topPaintingMap = (new Builder<Item, Item>()).put(VeItems.wanderer_painting, VeItems.wanderer_painting_top)
																	.put(VeItems.graham_painting, VeItems.graham_painting_top).build();
		
		Map<Item, Item> bottomPaintingMap = (new Builder<Item, Item>()).put(VeItems.wanderer_painting, VeItems.wanderer_painting_bottom)
																	   .put(VeItems.graham_painting, VeItems.graham_painting_bottom).build();
		
		Map<Item, Item> rightPaintingMap = (new Builder<Item, Item>()).put(VeItems.courbet_painting, VeItems.courbet_painting_right)
																	  .put(VeItems.creebet_painting, VeItems.creebet_painting_right)
																	  .put(VeItems.pool_painting, VeItems.pool_painting_right)
																	  .put(VeItems.sea_painting, VeItems.sea_painting_right)
																	  .put(VeItems.sunset_painting, VeItems.sunset_painting_right).build();
		
		Map<Item, Item> leftPaintingMap = (new Builder<Item, Item>()).put(VeItems.courbet_painting, VeItems.courbet_painting_left)
																	 .put(VeItems.creebet_painting, VeItems.creebet_painting_left)
																	 .put(VeItems.pool_painting, VeItems.pool_painting_left)
																	 .put(VeItems.sea_painting, VeItems.sea_painting_left)
																	 .put(VeItems.sunset_painting, VeItems.sunset_painting_left).build();
		
		if(tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos);
			
			VeFrameTileEntity topFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up());
			VeFrameTileEntity bottomFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down());
			
			VeFrameTileEntity eastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.east());
			VeFrameTileEntity westFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.west());
			VeFrameTileEntity northFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.north());
			VeFrameTileEntity southFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.south());
			
			if(!worldIn.isRemote)
			{
				if(topPaintingMap.containsKey(heldItem.getItem()) || bottomPaintingMap.containsKey(heldItem.getItem()))
				{
					if(worldIn.getBlockState(pos.up()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(topFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, topFrameTileEntity, bottomPaintingMap, topPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(worldIn.getBlockState(pos.down()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(bottomFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, bottomFrameTileEntity, topPaintingMap, bottomPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
				}
				else if(rightPaintingMap.containsKey(heldItem.getItem()) || leftPaintingMap.containsKey(heldItem.getItem()))
				{
					if(worldIn.getBlockState(pos.east()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(eastFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, eastFrameTileEntity, rightPaintingMap, leftPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(worldIn.getBlockState(pos.west()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(westFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, westFrameTileEntity, leftPaintingMap, rightPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(worldIn.getBlockState(pos.south()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(southFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, southFrameTileEntity, leftPaintingMap, rightPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(worldIn.getBlockState(pos.north()).getBlock() == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(northFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, northFrameTileEntity, rightPaintingMap, leftPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
				}
				//If the inventory slot is empty and the paintings tag contains the block add the item and consume it
				else if(VeItemTags.PAINTINGS.contains(heldItem.getItem()) && isEmpty(frameTileEntity))
				{
					frameTileEntity.addItem(heldItem);
					worldIn.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
					heldItem.shrink(1);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}
	
	/*
	 * A helper method that checks if the frame is empty.
	 */
	private static boolean isEmpty(VeFrameTileEntity frameTileEntity)
	{
		return frameTileEntity.getInventory().get(0) == ItemStack.EMPTY;
	}
	
	/*
	 * A helper method that fills the frames with each painting piece for 2 block paintings.
	 */
	private static void fill2BlockPainting(World world, BlockPos pos, VeFrameTileEntity clickedFrameTileEntity, VeFrameTileEntity secondFrameTileEntity, Map<Item, Item> clickedPaintingMap, Map<Item, Item> secondPaintingMap, ItemStack heldItem)
	{
		clickedFrameTileEntity.addItem(new ItemStack(clickedPaintingMap.get(heldItem.getItem())));
		secondFrameTileEntity.addItem(new ItemStack(secondPaintingMap.get(heldItem.getItem())));
		world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
		heldItem.shrink(1);
	}
	
	/*
	 * A helper method to check if the frame in the specified area are empty.
	 */
	private boolean isMultiBlockPaintingEmpty(BlockPos currentPos, World world, double rowNum, double colNum)
	{
		for(int col = 0; col < rowNum; col++)
		{
			for(int row = 0; row < colNum; row++)
			{
				VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) world.getTileEntity(currentPos.down(col).east(row));
				
				if(world.getBlockState(currentPos.down(col).east(row)).getBlock() == this.getBlock() && !isEmpty(frameTileEntity))
				{
					return false;
				}
				row += 1;
			}
			col += 1;
		}
		return true;
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
		boolean waterLoggedFlag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
		
		boolean secondaryUseFlag = context.func_225518_g_();
		
		
		
		return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(waterLoggedFlag)).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		boolean attachFlag = this.getBlock() == facingState.getBlock() && facingState.get(FACING) == stateIn.get(FACING);
		
		boolean northFlag = facing == Direction.NORTH ? attachFlag : stateIn.get(NORTH);
        boolean eastFlag = facing == Direction.EAST ? attachFlag : stateIn.get(EAST);
        boolean southFlag = facing == Direction.SOUTH ? attachFlag : stateIn.get(SOUTH);
        boolean westFlag = facing == Direction.WEST ? attachFlag : stateIn.get(WEST);
        boolean upFlag = facing == Direction.UP ? attachFlag : stateIn.get(UP);
        boolean downFlag = facing == Direction.DOWN ? attachFlag : stateIn.get(DOWN);
        
        if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		else if(facing.getOpposite() == stateIn.get(FACING) && !stateIn.isValidPosition(worldIn, currentPos))
		{
			this.onBlockHarvested(worldIn.getWorld(), currentPos, stateIn, null);
			return Blocks.AIR.getDefaultState();
		}
		else
		{
			return stateIn.with(UP, Boolean.valueOf(upFlag))
						  .with(DOWN, Boolean.valueOf(downFlag))
						  .with(NORTH, Boolean.valueOf(northFlag))
						  .with(EAST, Boolean.valueOf(eastFlag))
						  .with(SOUTH, Boolean.valueOf(southFlag))
						  .with(WEST, Boolean.valueOf(westFlag));
		}
		return facingState;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos);
		VeFrameTileEntity frameTileEntityUp = (VeFrameTileEntity) worldIn.getTileEntity(pos.up());
		VeFrameTileEntity frameTileEntityDown = (VeFrameTileEntity) worldIn.getTileEntity(pos.down());
		
		VeFrameTileEntity frameTileEntityEast = (VeFrameTileEntity) worldIn.getTileEntity(pos.east());
		VeFrameTileEntity frameTileEntityWest = (VeFrameTileEntity) worldIn.getTileEntity(pos.west());
		VeFrameTileEntity frameTileEntityNorth = (VeFrameTileEntity) worldIn.getTileEntity(pos.north());
		VeFrameTileEntity frameTileEntitySouth = (VeFrameTileEntity) worldIn.getTileEntity(pos.south());
		
		Map<Item, Item> tallPaintingMap = (new Builder<Item, Item>()).put(VeItems.wanderer_painting_bottom, VeItems.wanderer_painting)
																 	 .put(VeItems.wanderer_painting_top, VeItems.wanderer_painting)
																 	 .put(VeItems.graham_painting_bottom, VeItems.graham_painting)
																 	 .put(VeItems.graham_painting_top, VeItems.graham_painting).build();
		
		Map<Item, Item> sidePaintingMap = (new Builder<Item, Item>()).put(VeItems.courbet_painting_right, VeItems.courbet_painting)
			 	 												   	 .put(VeItems.courbet_painting_left, VeItems.courbet_painting)
			 	 												   	 .put(VeItems.creebet_painting_right, VeItems.creebet_painting)
			 	 												   	 .put(VeItems.creebet_painting_left, VeItems.creebet_painting)
			 	 												   	 .put(VeItems.pool_painting_right, VeItems.pool_painting)
			 	 												   	 .put(VeItems.pool_painting_left, VeItems.pool_painting)
			 	 												   	 .put(VeItems.sea_painting_right, VeItems.sea_painting)
			 	 												   	 .put(VeItems.sea_painting_left, VeItems.sea_painting)
			 	 												   	 .put(VeItems.sunset_painting_right, VeItems.sunset_painting)
			 	 												   	 .put(VeItems.sunset_painting_left, VeItems.sunset_painting).build();
		
		Item inventoryItem = frameTileEntity.getInventory().get(0).getItem();
		
		NonNullList<ItemStack> tallPaintingItemDrops = NonNullList.withSize(1, new ItemStack(tallPaintingMap.get(inventoryItem)));
		NonNullList<ItemStack> sidePaintingItemDrops = NonNullList.withSize(1, new ItemStack(sidePaintingMap.get(inventoryItem)));
		
		if(tallPaintingMap.containsKey(inventoryItem))
		{
			if(worldIn.getBlockState(pos.up()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntityUp))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntityUp, tallPaintingItemDrops);
			}
			else if(worldIn.getBlockState(pos.down()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntityDown))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntityDown, tallPaintingItemDrops);
			}
		}
		else if(sidePaintingMap.containsKey(inventoryItem))
		{
			if(worldIn.getBlockState(pos.east()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntityEast))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntityEast, sidePaintingItemDrops);
			}
			else if(worldIn.getBlockState(pos.west()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntityWest))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntityWest, sidePaintingItemDrops);
			}
			else if(worldIn.getBlockState(pos.north()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntityNorth))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntityNorth, sidePaintingItemDrops);
			}
			else if(worldIn.getBlockState(pos.south()).getBlock() == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(frameTileEntitySouth))
			{
				harvest2BlockPainting(worldIn, pos, frameTileEntitySouth, sidePaintingItemDrops);
			}
		}
		else
		{
			InventoryHelper.dropItems(worldIn, pos, frameTileEntity.getInventory());
		}
		
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	/*
	 * A helper method to harvest 2 block paintings.
	 */
	private static void harvest2BlockPainting(World world, BlockPos pos, VeFrameTileEntity secondFrameTileEntity, NonNullList<ItemStack> drops)
	{
		secondFrameTileEntity.getInventory().clear();
		InventoryHelper.dropItems(world, pos, drops);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch((Direction)state.get(FACING))
		{
			case NORTH:
				return defineShapes(state, state.get(WEST), state.get(EAST), Direction.NORTH, FRAME_NORTH_TOP_RIM_SHAPE,
																					 		  FRAME_NORTH_BOTTOM_RIM_SHAPE,
																					 		  FRAME_NORTH_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_LEFT_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE,
																					 		  FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE,
																					 		  FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE,
																					 		  FRAME_NORTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE,
																					 		  FRAME_NORTH_BACK_SHAPE,
																					 		  FRAME_NORTH_ALL_SHAPE);
			case SOUTH:
				return defineShapes(state, state.get(WEST), state.get(EAST), Direction.NORTH, FRAME_SOUTH_TOP_RIM_SHAPE,
																							  FRAME_SOUTH_BOTTOM_RIM_SHAPE,
																							  FRAME_SOUTH_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_LEFT_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_AND_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_AND_LEFT_RIM_SHAPE,
																							  FRAME_SOUTH_BOTTOM_AND_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_BOTTOM_AND_LEFT_RIM_SHAPE,
																							  FRAME_SOUTH_RIGHT_AND_LEFT_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_AND_BOTTOM_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_BOTTOM_AND_LEFT_RIM_SHAPE,
																							  FRAME_SOUTH_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE,
																							  FRAME_SOUTH_BACK_SHAPE,
																							  FRAME_SOUTH_ALL_SHAPE);
			case WEST:
				return defineShapes(state, state.get(SOUTH), state.get(NORTH), Direction.WEST, FRAME_WEST_TOP_RIM_SHAPE,
													  					 					   FRAME_WEST_BOTTOM_RIM_SHAPE,
													  					 					   FRAME_WEST_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_LEFT_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_AND_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_AND_LEFT_RIM_SHAPE,
													  					 					   FRAME_WEST_BOTTOM_AND_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_BOTTOM_AND_LEFT_RIM_SHAPE,
													  					 					   FRAME_WEST_RIGHT_AND_LEFT_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_AND_BOTTOM_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_LEFT_AND_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_BOTTOM_AND_LEFT_RIM_SHAPE,
													  					 					   FRAME_WEST_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE,
													  					 					   FRAME_WEST_BACK_SHAPE,
													  					 					   FRAME_WEST_ALL_SHAPE);
			default:
				return defineShapes(state, state.get(SOUTH), state.get(NORTH), Direction.WEST, FRAME_EAST_TOP_RIM_SHAPE,
																							   FRAME_EAST_BOTTOM_RIM_SHAPE,
																							   FRAME_EAST_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_LEFT_RIM_SHAPE,
																							   FRAME_EAST_TOP_AND_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_TOP_AND_LEFT_RIM_SHAPE,
																							   FRAME_EAST_BOTTOM_AND_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_BOTTOM_AND_LEFT_RIM_SHAPE,
																							   FRAME_EAST_RIGHT_AND_LEFT_RIM_SHAPE,
																							   FRAME_EAST_TOP_AND_BOTTOM_RIM_SHAPE,
																							   FRAME_EAST_TOP_LEFT_AND_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_BOTTOM_LEFT_AND_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_TOP_BOTTOM_AND_LEFT_RIM_SHAPE,
																							   FRAME_EAST_TOP_BOTTOM_AND_RIGHT_RIM_SHAPE,
																							   FRAME_EAST_BACK_SHAPE,
																							   FRAME_EAST_ALL_SHAPE);
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
		builder.add(FACING, WATERLOGGED, NORTH, SOUTH, WEST, EAST, UP, DOWN);
	}
	
	public static VoxelShape defineShapes(BlockState state, Boolean firstSide, Boolean secondSide, Direction secondDirection, VoxelShape frameTopRim, VoxelShape frameBottomRim, VoxelShape frameRightRim, VoxelShape frameLeftRim, VoxelShape frameTopAndRightRim, VoxelShape frameTopAndLeftRim, VoxelShape frameBottomAndRightRim, VoxelShape frameBottomAndLeftRim, VoxelShape frameRightAndLeftRim, VoxelShape frameTopAndBottomRim, VoxelShape frameTopLeftAndRightRim, VoxelShape frameBottomLeftAndRightRim, VoxelShape frameTopBottomAndLeftRim, VoxelShape frameTopBottomAndRightRim, VoxelShape frameNone, VoxelShape frameAll)
	{
		if(state.get(DOWN) && state.get(UP) && secondSide && firstSide)
		{
			return frameNone;
		}
		else if(state.get(UP) && state.get(DOWN))
		{
			if(secondSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameRightRim;
				}
				else
				{
					return frameLeftRim;
				}
			}
			else if(firstSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameLeftRim;
				}
				else
				{
					return frameRightRim;
				}
			}
			else
			{
				return frameRightAndLeftRim;
			}
		}
		else if(secondSide && firstSide)
		{
			if(state.get(UP))
			{
				return frameBottomRim;
			}
			else if(state.get(DOWN))
			{
				return frameTopRim;
			}
			else
			{
				return frameTopAndBottomRim;
			}
		}
		else if(state.get(UP))
		{
			if(secondSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameBottomAndRightRim;
				}
				else
				{
					return frameBottomAndLeftRim;
				}
			}
			else if(firstSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameBottomAndLeftRim;
				}
				else
				{
					return frameBottomAndRightRim;
				}
			}
			else
			{
				return frameBottomLeftAndRightRim;
			}
		}
		else if(state.get(DOWN))
		{
			if(secondSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameTopAndRightRim;
				}
				else
				{
					return frameTopAndLeftRim;
				}
			}
			else if(firstSide)
			{
				if(state.get(FACING) == secondDirection)
				{
					return frameTopAndLeftRim;
				}
				else
				{
					return frameTopAndRightRim;
				}
			}
			else
			{
				return frameTopLeftAndRightRim;
			}
		}
		else if(secondSide)
		{
			if(state.get(FACING) == secondDirection)
			{
				return frameTopBottomAndRightRim;
			}
			else
			{
				return frameTopBottomAndLeftRim;
			}
		}
		else if(firstSide)
		{
			if(state.get(FACING) == secondDirection)
			{
				return frameTopBottomAndLeftRim;
			}
			else
			{
				return frameTopBottomAndRightRim;
			}
		}
		else
		{
			return frameAll;
		}
	}
}
