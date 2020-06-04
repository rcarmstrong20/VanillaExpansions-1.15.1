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
	
	private static final VoxelShape FRAME_NORTH_TOP_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																			   FRAME_NORTH_TOP_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_BOTTOM_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																				  FRAME_NORTH_BOTTOM_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																				 FRAME_NORTH_RIGHT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																				FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																						 FRAME_NORTH_TOP_SHAPE,
																						 FRAME_NORTH_RIGHT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																						FRAME_NORTH_TOP_SHAPE,
																						FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_BOTTOM_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																							FRAME_NORTH_BOTTOM_SHAPE,
																							FRAME_NORTH_RIGHT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_BOTTOM_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																						   FRAME_NORTH_BOTTOM_SHAPE,
																						   FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_RIGHT_AND_LEFT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																						  FRAME_NORTH_RIGHT_SHAPE,
																						  FRAME_NORTH_LEFT_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_AND_BOTTOM_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																						  FRAME_NORTH_TOP_SHAPE,
																						  FRAME_NORTH_BOTTOM_SHAPE);
	
	private static final VoxelShape FRAME_NORTH_TOP_LEFT_AND_RIGHT_RIM_SHAPE = VoxelShapes.or(FRAME_NORTH_BACK_SHAPE,
																							  FRAME_NORTH_TOP_SHAPE,
																							  FRAME_NORTH_RIGHT_SHAPE,
																							  FRAME_NORTH_LEFT_SHAPE);
	
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
		
		Map<Item, Item> bottomRightPaintingMap = (new Builder<Item, Item>()).put(VeItems.wither_painting, VeItems.wither_painting_bottom_right)
																			.put(VeItems.bust_painting, VeItems.bust_painting_bottom_right)
																			.put(VeItems.match_painting, VeItems.match_painting_bottom_right)
																			.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_bottom_right)
																			.put(VeItems.stage_painting, VeItems.stage_painting_bottom_right)
																			.put(VeItems.void_painting, VeItems.void_painting_bottom_right).build();
		
		Map<Item, Item> bottomLeftPaintingMap = (new Builder<Item, Item>()).put(VeItems.wither_painting, VeItems.wither_painting_bottom_left)
																		   .put(VeItems.bust_painting, VeItems.bust_painting_bottom_left)
																		   .put(VeItems.match_painting, VeItems.match_painting_bottom_left)
																		   .put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_bottom_left)
																		   .put(VeItems.stage_painting, VeItems.stage_painting_bottom_left)
																		   .put(VeItems.void_painting, VeItems.void_painting_bottom_left).build();
		
		Map<Item, Item> topRightPaintingMap = (new Builder<Item, Item>()).put(VeItems.wither_painting, VeItems.wither_painting_top_right)
																		 .put(VeItems.bust_painting, VeItems.bust_painting_top_right)
																		 .put(VeItems.match_painting, VeItems.match_painting_top_right)
																		 .put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_top_right)
																		 .put(VeItems.stage_painting, VeItems.stage_painting_top_right)
																		 .put(VeItems.void_painting, VeItems.void_painting_top_right).build();
		
		Map<Item, Item> topLeftPaintingMap = (new Builder<Item, Item>()).put(VeItems.wither_painting, VeItems.wither_painting_top_left)
																		.put(VeItems.bust_painting, VeItems.bust_painting_top_left)
																		.put(VeItems.match_painting, VeItems.match_painting_top_left)
																		.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_top_left)
																		.put(VeItems.stage_painting, VeItems.stage_painting_top_left)
																		.put(VeItems.void_painting, VeItems.void_painting_top_left).build();
		
		if(tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity frameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos);
			
			VeFrameTileEntity topFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up());
			VeFrameTileEntity bottomFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down());
			
			VeFrameTileEntity eastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.east());
			VeFrameTileEntity westFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.west());
			VeFrameTileEntity northFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.north());
			VeFrameTileEntity southFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.south());
			
			VeFrameTileEntity topEastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().east());
			VeFrameTileEntity topWestFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().west());
			VeFrameTileEntity topNorthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().north());
			VeFrameTileEntity topSouthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().south());
			
			VeFrameTileEntity bottomEastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().east());
			VeFrameTileEntity bottomWestFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().west());
			VeFrameTileEntity bottomNorthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().north());
			VeFrameTileEntity bottomSouthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().south());
			
			Block topBlock = worldIn.getBlockState(pos.up()).getBlock();
			Block bottomBlock = worldIn.getBlockState(pos.down()).getBlock();
			
			Block eastBlock = worldIn.getBlockState(pos.east()).getBlock();
			Block westBlock = worldIn.getBlockState(pos.west()).getBlock();
			Block southBlock = worldIn.getBlockState(pos.south()).getBlock();
			Block northBlock = worldIn.getBlockState(pos.north()).getBlock();
			
			Block topEastBlock = worldIn.getBlockState(pos.up().east()).getBlock();
			Block topWestBlock = worldIn.getBlockState(pos.up().west()).getBlock();
			Block topSouthBlock = worldIn.getBlockState(pos.up().south()).getBlock();
			Block topNorthBlock = worldIn.getBlockState(pos.up().north()).getBlock();
			
			Block bottomEastBlock = worldIn.getBlockState(pos.down().east()).getBlock();
			Block bottomWestBlock = worldIn.getBlockState(pos.down().west()).getBlock();
			Block bottomSouthBlock = worldIn.getBlockState(pos.down().south()).getBlock();
			Block bottomNorthBlock = worldIn.getBlockState(pos.down().north()).getBlock();
			
			if(!worldIn.isRemote)
			{
				if(topPaintingMap.containsKey(heldItem.getItem()) || bottomPaintingMap.containsKey(heldItem.getItem()))
				{
					if(topBlock == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(topFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, topFrameTileEntity, bottomPaintingMap, topPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(bottomBlock == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(bottomFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity, bottomFrameTileEntity, topPaintingMap, bottomPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
				}
				else if(rightPaintingMap.containsKey(heldItem.getItem()) || leftPaintingMap.containsKey(heldItem.getItem()))
				{
					if(eastBlock == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(eastFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity,
														 eastFrameTileEntity,
														 rightPaintingMap, leftPaintingMap, heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(westBlock == this.getBlock() && isEmpty(frameTileEntity) && isEmpty(westFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity,
														 westFrameTileEntity,
														 leftPaintingMap,
														 rightPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(southBlock == this.getBlock() &&
							isEmpty(frameTileEntity)      &&
							isEmpty(southFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity,
														 southFrameTileEntity,
														 leftPaintingMap,
														 rightPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(northBlock == this.getBlock() &&
							isEmpty(frameTileEntity)      &&
							isEmpty(northFrameTileEntity))
					{
						fill2BlockPainting(worldIn, pos, frameTileEntity,
														 northFrameTileEntity,
														 rightPaintingMap,
														 leftPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
				}
				else if(bottomRightPaintingMap.containsKey(heldItem.getItem()) || bottomLeftPaintingMap.containsKey(heldItem.getItem()) || topRightPaintingMap.containsKey(heldItem.getItem()) || topLeftPaintingMap.containsKey(heldItem.getItem()))
				{
					if(topBlock == this.getBlock() 	   &&
					   eastBlock == this.getBlock()    &&
					   topEastBlock == this.getBlock() &&
					   isEmpty(frameTileEntity)		   &&
					   isEmpty(topFrameTileEntity)     &&
					   isEmpty(eastFrameTileEntity)    &&
					   isEmpty(topEastFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 topFrameTileEntity,
														 eastFrameTileEntity,
														 topEastFrameTileEntity,
														 bottomRightPaintingMap,
														 topRightPaintingMap,
														 bottomLeftPaintingMap,
														 topLeftPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(bottomBlock == this.getBlock()     &&
							eastBlock == this.getBlock()       &&
							bottomEastBlock == this.getBlock() &&
							isEmpty(frameTileEntity)           &&
							isEmpty(eastFrameTileEntity)       &&
							isEmpty(bottomFrameTileEntity)	   &&
							isEmpty(bottomEastFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 eastFrameTileEntity,
														 bottomFrameTileEntity,
														 bottomEastFrameTileEntity,
														 topRightPaintingMap,
														 topLeftPaintingMap,
														 bottomRightPaintingMap,
														 bottomLeftPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(topBlock == this.getBlock()     &&
							westBlock == this.getBlock()    &&
							topWestBlock == this.getBlock() &&
							isEmpty(frameTileEntity)        &&
							isEmpty(westFrameTileEntity)    &&
							isEmpty(topFrameTileEntity)		&&
							isEmpty(topWestFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 westFrameTileEntity,
														 topFrameTileEntity,
														 topWestFrameTileEntity,
														 bottomLeftPaintingMap,
														 bottomRightPaintingMap,
														 topLeftPaintingMap,
														 topRightPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(bottomBlock == this.getBlock()     &&
							westBlock == this.getBlock()       &&
							bottomWestBlock == this.getBlock() &&
							isEmpty(frameTileEntity)           &&
							isEmpty(westFrameTileEntity)       &&
							isEmpty(bottomFrameTileEntity)	   &&
							isEmpty(bottomWestFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 westFrameTileEntity,
														 bottomFrameTileEntity,
														 bottomWestFrameTileEntity,
														 topLeftPaintingMap,
														 topRightPaintingMap,
														 bottomLeftPaintingMap,
														 bottomRightPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(topBlock == this.getBlock() 	 &&
							northBlock == this.getBlock()    &&
							topNorthBlock == this.getBlock() &&
							isEmpty(frameTileEntity)	     &&
							isEmpty(topFrameTileEntity)      &&
							isEmpty(northFrameTileEntity)    &&
							isEmpty(topNorthFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 topFrameTileEntity,
														 northFrameTileEntity,
														 topNorthFrameTileEntity,
														 bottomRightPaintingMap,
														 topRightPaintingMap,
														 bottomLeftPaintingMap,
														 topLeftPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(bottomBlock == this.getBlock()      &&
							northBlock == this.getBlock()       &&
							bottomNorthBlock == this.getBlock() &&
							isEmpty(frameTileEntity)            &&
							isEmpty(northFrameTileEntity)       &&
							isEmpty(bottomFrameTileEntity)	    &&
							isEmpty(bottomNorthFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 northFrameTileEntity,
														 bottomFrameTileEntity,
														 bottomNorthFrameTileEntity,
														 topRightPaintingMap,
														 topLeftPaintingMap,
														 bottomRightPaintingMap,
														 bottomLeftPaintingMap,
														 heldItem);
						return ActionResultType.SUCCESS;
					}
					else if(topBlock == this.getBlock()      &&
							southBlock == this.getBlock()    &&
							topSouthBlock == this.getBlock() &&
							isEmpty(frameTileEntity)         &&
							isEmpty(southFrameTileEntity)    &&
							isEmpty(topFrameTileEntity)		 &&
							isEmpty(topSouthFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 southFrameTileEntity,
														 topFrameTileEntity,
														 topSouthFrameTileEntity,
														 bottomLeftPaintingMap,
														 bottomRightPaintingMap,
														 topLeftPaintingMap,
														 topRightPaintingMap,
														 heldItem);
					}
					else if(bottomBlock == this.getBlock()      &&
							southBlock == this.getBlock()       &&
							bottomSouthBlock == this.getBlock() &&
							isEmpty(frameTileEntity)            &&
							isEmpty(southFrameTileEntity)       &&
							isEmpty(bottomFrameTileEntity)	    &&
							isEmpty(bottomSouthFrameTileEntity))
					{
						fill4BlockPainting(worldIn, pos, frameTileEntity,
														 southFrameTileEntity,
														 bottomFrameTileEntity,
														 bottomSouthFrameTileEntity,
														 topLeftPaintingMap,
														 topRightPaintingMap,
														 bottomLeftPaintingMap,
														 bottomRightPaintingMap,
														 heldItem);
					}
				}
				//If the inventory slot is empty and the paintings tag contains the block add the item and consume it.
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
	 * A helper method that fills the frames with each painting piece for 4 block paintings.
	 */
	private static void fill4BlockPainting(World world, BlockPos pos, VeFrameTileEntity clickedFrame, VeFrameTileEntity secondFrame, VeFrameTileEntity thirdFrame, VeFrameTileEntity fourthFrame, Map<Item, Item> clickedPaintingMap, Map<Item, Item> secondPaintingMap, Map<Item, Item> thirdPaintingMap, Map<Item, Item> fourthPaintingMap, ItemStack heldItem)
	{
		clickedFrame.addItem(new ItemStack(clickedPaintingMap.get(heldItem.getItem())));
		secondFrame.addItem(new ItemStack(secondPaintingMap.get(heldItem.getItem())));
		thirdFrame.addItem(new ItemStack(thirdPaintingMap.get(heldItem.getItem())));
		fourthFrame.addItem(new ItemStack(fourthPaintingMap.get(heldItem.getItem())));
		world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
		heldItem.shrink(1);
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
		
		
		
		return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(waterLoggedFlag))
									 .with(FACING, context.getPlacementHorizontalFacing().getOpposite());
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
		
		VeFrameTileEntity topFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up());
		VeFrameTileEntity bottomFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down());
		
		VeFrameTileEntity eastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.east());
		VeFrameTileEntity westFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.west());
		VeFrameTileEntity northFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.north());
		VeFrameTileEntity southFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.south());
		
		VeFrameTileEntity topEastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().east());
		VeFrameTileEntity topWestFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().west());
		VeFrameTileEntity topNorthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().north());
		VeFrameTileEntity topSouthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.up().south());
		
		VeFrameTileEntity bottomEastFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().east());
		VeFrameTileEntity bottomWestFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().west());
		VeFrameTileEntity bottomNorthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().north());
		VeFrameTileEntity bottomSouthFrameTileEntity = (VeFrameTileEntity) worldIn.getTileEntity(pos.down().south());
		
		Block topBlock = worldIn.getBlockState(pos.up()).getBlock();
		Block bottomBlock = worldIn.getBlockState(pos.down()).getBlock();
		
		Block eastBlock = worldIn.getBlockState(pos.east()).getBlock();
		Block westBlock = worldIn.getBlockState(pos.west()).getBlock();
		Block southBlock = worldIn.getBlockState(pos.south()).getBlock();
		Block northBlock = worldIn.getBlockState(pos.north()).getBlock();
		
		Block topEastBlock = worldIn.getBlockState(pos.up().east()).getBlock();
		Block topWestBlock = worldIn.getBlockState(pos.up().west()).getBlock();
		Block topSouthBlock = worldIn.getBlockState(pos.up().south()).getBlock();
		Block topNorthBlock = worldIn.getBlockState(pos.up().north()).getBlock();
		
		Block bottomEastBlock = worldIn.getBlockState(pos.down().east()).getBlock();
		Block bottomWestBlock = worldIn.getBlockState(pos.down().west()).getBlock();
		Block bottomSouthBlock = worldIn.getBlockState(pos.down().south()).getBlock();
		Block bottomNorthBlock = worldIn.getBlockState(pos.down().north()).getBlock();
		
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
		
		Map<Item, Item> fourPaintingMap = (new Builder<Item, Item>()).put(VeItems.wither_painting_bottom_right, VeItems.wither_painting)
																	 .put(VeItems.wither_painting_bottom_left, VeItems.wither_painting)
																	 .put(VeItems.wither_painting_top_right, VeItems.wither_painting)
																	 .put(VeItems.wither_painting_top_left, VeItems.wither_painting)
																	 .put(VeItems.bust_painting_bottom_right, VeItems.bust_painting)
																	 .put(VeItems.bust_painting_bottom_left, VeItems.bust_painting)
																	 .put(VeItems.bust_painting_top_right, VeItems.bust_painting)
																	 .put(VeItems.bust_painting_top_left, VeItems.bust_painting)
																	 .put(VeItems.match_painting_bottom_right, VeItems.match_painting)
																	 .put(VeItems.match_painting_bottom_left, VeItems.match_painting)
																	 .put(VeItems.match_painting_top_right, VeItems.match_painting)
																	 .put(VeItems.match_painting_top_left, VeItems.match_painting)
																	 .put(VeItems.skull_and_roses_painting_bottom_right, VeItems.skull_and_roses_painting)
																	 .put(VeItems.skull_and_roses_painting_bottom_left, VeItems.skull_and_roses_painting)
																	 .put(VeItems.skull_and_roses_painting_top_right, VeItems.skull_and_roses_painting)
																	 .put(VeItems.skull_and_roses_painting_top_left, VeItems.skull_and_roses_painting)
																	 .put(VeItems.stage_painting_bottom_right, VeItems.stage_painting)
																	 .put(VeItems.stage_painting_bottom_left, VeItems.stage_painting)
																	 .put(VeItems.stage_painting_top_right, VeItems.stage_painting)
																	 .put(VeItems.stage_painting_top_left, VeItems.stage_painting)
																	 .put(VeItems.void_painting_bottom_right, VeItems.void_painting)
																	 .put(VeItems.void_painting_bottom_left, VeItems.void_painting)
																	 .put(VeItems.void_painting_top_right, VeItems.void_painting)
																	 .put(VeItems.void_painting_top_left, VeItems.void_painting).build();
		
		Item inventoryItem = frameTileEntity.getInventory().get(0).getItem();
		
		NonNullList<ItemStack> tallPaintingItemDrops = NonNullList.withSize(1, new ItemStack(tallPaintingMap.get(inventoryItem)));
		NonNullList<ItemStack> sidePaintingItemDrops = NonNullList.withSize(1, new ItemStack(sidePaintingMap.get(inventoryItem)));
		NonNullList<ItemStack> fourPaintingItemDrops = NonNullList.withSize(1, new ItemStack(fourPaintingMap.get(inventoryItem)));
		
		if(tallPaintingMap.containsKey(inventoryItem))
		{
			if(topBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(topFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, topFrameTileEntity, tallPaintingItemDrops);
			}
			else if(bottomBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(bottomFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, bottomFrameTileEntity, tallPaintingItemDrops);
			}
		}
		else if(sidePaintingMap.containsKey(inventoryItem))
		{
			if(eastBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(eastFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, eastFrameTileEntity, sidePaintingItemDrops);
			}
			else if(westBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(westFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, westFrameTileEntity, sidePaintingItemDrops);
			}
			else if(northBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(northFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, northFrameTileEntity, sidePaintingItemDrops);
			}
			else if(southBlock == this.getBlock() && !isEmpty(frameTileEntity) && !isEmpty(southFrameTileEntity))
			{
				harvest2BlockPainting(worldIn, pos, southFrameTileEntity, sidePaintingItemDrops);
			}
		}
		else if(fourPaintingMap.containsKey(inventoryItem))
		{
			if(topBlock == this.getBlock() 	   &&
			   eastBlock == this.getBlock()    &&
			   topEastBlock == this.getBlock() &&
			   !isEmpty(frameTileEntity)	   &&
			   !isEmpty(topFrameTileEntity)    &&
			   !isEmpty(eastFrameTileEntity)   &&
			   !isEmpty(topEastFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, topFrameTileEntity,
													eastFrameTileEntity,
													topEastFrameTileEntity,
													fourPaintingItemDrops);
			}
			else if(bottomBlock == this.getBlock()     &&
					eastBlock == this.getBlock()       &&
					bottomEastBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)          &&
					!isEmpty(eastFrameTileEntity)      &&
					!isEmpty(bottomFrameTileEntity)	   &&
					!isEmpty(bottomEastFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, eastFrameTileEntity,
												 	bottomFrameTileEntity,
												 	bottomEastFrameTileEntity,
												 	fourPaintingItemDrops);
			}
			else if(topBlock == this.getBlock()    	&&
					westBlock == this.getBlock()    &&
					topWestBlock == this.getBlock()	&&
					!isEmpty(frameTileEntity)       &&
					!isEmpty(westFrameTileEntity)   &&
					!isEmpty(topFrameTileEntity)	&&
					!isEmpty(topWestFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, westFrameTileEntity,
												 	topFrameTileEntity,
												 	topWestFrameTileEntity,
												 	fourPaintingItemDrops);
			}
			else if(bottomBlock == this.getBlock()     &&
					westBlock == this.getBlock()       &&
					bottomWestBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)          &&
					!isEmpty(westFrameTileEntity)      &&
					!isEmpty(bottomFrameTileEntity)	   &&
					!isEmpty(bottomWestFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, westFrameTileEntity,
												 	bottomFrameTileEntity,
												 	bottomWestFrameTileEntity,
												 	fourPaintingItemDrops);
			}
			else if(topBlock == this.getBlock() 	 &&
					northBlock == this.getBlock()    &&
					topNorthBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)	     &&
					!isEmpty(topFrameTileEntity)     &&
					!isEmpty(northFrameTileEntity)   &&
					!isEmpty(topNorthFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, topFrameTileEntity,
												    northFrameTileEntity,
												    topNorthFrameTileEntity,
												    fourPaintingItemDrops);
			}
			else if(bottomBlock == this.getBlock()      &&
					northBlock == this.getBlock()       &&
					bottomNorthBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)           &&
					!isEmpty(northFrameTileEntity)      &&
					!isEmpty(bottomFrameTileEntity)	    &&
					!isEmpty(bottomNorthFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, northFrameTileEntity,
												 	bottomFrameTileEntity,
												 	bottomNorthFrameTileEntity,
												 	fourPaintingItemDrops);
			}
			else if(topBlock == this.getBlock()      &&
					southBlock == this.getBlock()    &&
					topSouthBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)        &&
					!isEmpty(southFrameTileEntity)   &&
					!isEmpty(topFrameTileEntity)	 &&
					!isEmpty(topSouthFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, southFrameTileEntity,
												 	topFrameTileEntity,
												 	topSouthFrameTileEntity,
												 	fourPaintingItemDrops);
			}
			else if(bottomBlock == this.getBlock()      &&
					southBlock == this.getBlock()       &&
					bottomSouthBlock == this.getBlock() &&
					!isEmpty(frameTileEntity)           &&
					!isEmpty(southFrameTileEntity)      &&
					!isEmpty(bottomFrameTileEntity)	    &&
					!isEmpty(bottomSouthFrameTileEntity))
			{
				harvest4BlockPainting(worldIn, pos, southFrameTileEntity,
													bottomFrameTileEntity,
													bottomSouthFrameTileEntity,
													fourPaintingItemDrops);
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
	private static void harvest2BlockPainting(World world, BlockPos pos, VeFrameTileEntity secondFrame, NonNullList<ItemStack> drops)
	{
		secondFrame.getInventory().clear();
		InventoryHelper.dropItems(world, pos, drops);
	}
	
	/*
	 * A helper method to harvest 4 block paintings.
	 */
	private static void harvest4BlockPainting(World world, BlockPos pos, VeFrameTileEntity secondFrame, VeFrameTileEntity thirdFrame, VeFrameTileEntity fourthFrame, NonNullList<ItemStack> drops)
	{
		secondFrame.getInventory().clear();
		thirdFrame.getInventory().clear();
		fourthFrame.getInventory().clear();
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
