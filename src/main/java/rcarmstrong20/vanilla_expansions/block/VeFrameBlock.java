package rcarmstrong20.vanilla_expansions.block;

import java.util.Map;

import com.electronwill.nightconfig.core.EnumGetMethod;
import com.google.common.collect.ImmutableMap.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
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
import net.minecraft.tags.BlockTags;
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
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult rayTrace)
	{
		ItemStack heldItem = player.getHeldItem(handIn);
		
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
		
		TileEntity tileEntity = world.getTileEntity(pos);
		
		TileEntity topTileEntity = world.getTileEntity(pos.up());
		TileEntity bottomTileEntity = world.getTileEntity(pos.down());
		
		TileEntity eastTileEntity = world.getTileEntity(pos.east());
		TileEntity westTileEntity = world.getTileEntity(pos.west());
		TileEntity northTileEntity = world.getTileEntity(pos.north());
		TileEntity southTileEntity = world.getTileEntity(pos.south());
		
		TileEntity topEastTileEntity = world.getTileEntity(pos.up().east());
		TileEntity topWestTileEntity = world.getTileEntity(pos.up().west());
		TileEntity topNorthTileEntity = world.getTileEntity(pos.up().north());
		TileEntity topSouthTileEntity = world.getTileEntity(pos.up().south());
		
		TileEntity bottomEastTileEntity = world.getTileEntity(pos.down().east());
		TileEntity bottomWestTileEntity = world.getTileEntity(pos.down().west());
		TileEntity bottomNorthTileEntity = world.getTileEntity(pos.down().north());
		TileEntity bottomSouthTileEntity = world.getTileEntity(pos.down().south());
		
		BlockState topState = world.getBlockState(pos.up());
		BlockState bottomState = world.getBlockState(pos.down());
		
		BlockState eastState = world.getBlockState(pos.east());
		BlockState westState = world.getBlockState(pos.west());
		BlockState southState = world.getBlockState(pos.south());
		BlockState northState = world.getBlockState(pos.north());
		
		BlockState topEastState = world.getBlockState(pos.up().east());
		BlockState topWestState = world.getBlockState(pos.up().west());
		BlockState topSouthState = world.getBlockState(pos.up().south());
		BlockState topNorthState = world.getBlockState(pos.up().north());
		
		BlockState bottomEastState = world.getBlockState(pos.down().east());
		BlockState bottomWestState = world.getBlockState(pos.down().west());
		BlockState bottomSouthState = world.getBlockState(pos.down().south());
		BlockState bottomNorthState = world.getBlockState(pos.down().north());
		
		if(!world.isRemote && tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity clickedFrame = (VeFrameTileEntity) tileEntity;
			
			if(topPaintingMap.containsKey(heldItem.getItem()) || bottomPaintingMap.containsKey(heldItem.getItem()))
			{
				if(topTileEntity instanceof VeFrameTileEntity || bottomTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					
					if(frameFits2BlockPainting(state, topState, clickedFrame, topFrame))
					{
						if(clickedFrame.addItem(new ItemStack(bottomPaintingMap.get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(topPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					} 
					else if(frameFits2BlockPainting(state, bottomState, clickedFrame, bottomFrame))
					{
						if(clickedFrame.addItem(new ItemStack(topPaintingMap.get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(bottomPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(rightPaintingMap.containsKey(heldItem.getItem()) || leftPaintingMap.containsKey(heldItem.getItem()))
			{
				if(eastTileEntity instanceof VeFrameTileEntity  ||
				   westTileEntity instanceof VeFrameTileEntity  ||
				   southTileEntity instanceof VeFrameTileEntity ||
				   northTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					
					if(frameFits2BlockPainting(state, eastState, clickedFrame, eastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(rightPaintingMap.get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(leftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, westState, clickedFrame, westFrame))
					{
						if(clickedFrame.addItem(new ItemStack(leftPaintingMap.get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(rightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, southState, clickedFrame, southFrame))
					{
						if(clickedFrame.addItem(new ItemStack(rightPaintingMap.get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(leftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, northState, clickedFrame, northFrame))
					{
						if(clickedFrame.addItem(new ItemStack(leftPaintingMap.get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(rightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(bottomRightPaintingMap.containsKey(heldItem.getItem()) ||
					bottomLeftPaintingMap.containsKey(heldItem.getItem())  ||
					topRightPaintingMap.containsKey(heldItem.getItem())    ||
					topLeftPaintingMap.containsKey(heldItem.getItem()))
			{
				//Place the painting from top west to bottom east.
				if(bottomTileEntity instanceof VeFrameTileEntity  ||
						eastTileEntity instanceof VeFrameTileEntity    ||
						bottomEastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   bottomState,
							   				   eastState,
							   				   bottomEastState,
							   				   clickedFrame,
							   				   bottomFrame,
							   				   eastFrame,
							   				   bottomEastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from bottom west to top east.
				if(topTileEntity instanceof VeFrameTileEntity  ||
				   eastTileEntity instanceof VeFrameTileEntity ||
				   topEastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity topEastFrame = (VeFrameTileEntity) topEastTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   topState,
							   				   eastState,
							   				   topEastState,
							   				   clickedFrame,
							   				   topFrame,
							   				   eastFrame,
							   				   topEastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   topEastFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from top east to bottom west.
				if(bottomTileEntity instanceof VeFrameTileEntity  ||
				   westTileEntity instanceof VeFrameTileEntity    ||
				   bottomWestTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity bottomWestFrame = (VeFrameTileEntity) bottomWestTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   bottomState,
							   				   westState,
							   				   bottomWestState,
							   				   clickedFrame,
							   				   bottomFrame,
							   				   westFrame,
							   				   bottomWestFrame))
					{
						if(clickedFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) &&
						   bottomWestFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from bottom east to top west.
				if(topTileEntity instanceof VeFrameTileEntity  ||
				   westTileEntity instanceof VeFrameTileEntity ||
				   topWestTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity topWestFrame = (VeFrameTileEntity) topWestTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   topState,
							   				   westState,
							   				   topWestState,
							   				   clickedFrame,
							   				   topFrame,
							   				   westFrame,
							   				   topWestFrame))
					{
						if(clickedFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   topWestFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from top south to bottom north.
				if(bottomTileEntity instanceof VeFrameTileEntity  ||
				   northTileEntity instanceof VeFrameTileEntity    ||
				   bottomNorthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					VeFrameTileEntity bottomNorthFrame = (VeFrameTileEntity) bottomNorthTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   bottomState,
							   				   northState,
							   				   bottomNorthState,
							   				   clickedFrame,
							   				   bottomFrame,
							   				   northFrame,
							   				   bottomNorthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem())))   &&
						   bottomFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) 	  &&
						   bottomNorthFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from bottom south to top north.
				if(topTileEntity instanceof VeFrameTileEntity  ||
				   northTileEntity instanceof VeFrameTileEntity ||
				   topNorthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					VeFrameTileEntity topNorthFrame = (VeFrameTileEntity) topNorthTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   topState,
							   				   northState,
							   				   topNorthState,
							   				   clickedFrame,
							   				   topFrame,
							   				   northFrame,
							   				   topNorthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   topNorthFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from top north to bottom south.
				if(bottomTileEntity instanceof VeFrameTileEntity ||
				   southTileEntity instanceof VeFrameTileEntity  ||
				   bottomSouthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					VeFrameTileEntity bottomSouthFrame = (VeFrameTileEntity) bottomSouthTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   bottomState,
							   				   southState,
							   				   bottomSouthState,
							   				   clickedFrame,
							   				   bottomFrame,
							   				   southFrame,
							   				   bottomSouthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))) &&
						   bottomSouthFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from bottom north to top south.
				if(topTileEntity instanceof VeFrameTileEntity  ||
				   southTileEntity instanceof VeFrameTileEntity ||
				   topSouthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					VeFrameTileEntity topSouthFrame = (VeFrameTileEntity) topSouthTileEntity;
					
					if(frameFits4BlockPainting(state,
							   				   topState,
							   				   southState,
							   				   topSouthState,
							   				   clickedFrame,
							   				   topFrame,
							   				   southFrame,
							   				   topSouthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(bottomRightPaintingMap.get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(topRightPaintingMap.get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(bottomLeftPaintingMap.get(heldItem.getItem()))) &&
						   topSouthFrame.addItem(new ItemStack(topLeftPaintingMap.get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(VeItemTags.PAINTINGS.contains(heldItem.getItem()) && isEmpty(clickedFrame))
			{
				clickedFrame.addItem(heldItem);
				world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.FAIL;
	}
	
	/*
	 * A helper method that checks that the frame can support the painting.
	 */
	private static boolean frameFits2BlockPainting(BlockState clickedState, BlockState secondState, VeFrameTileEntity clickedFrame, VeFrameTileEntity secondFrame)
	{
		return secondState.getBlock() == clickedState.getBlock() &&
			   matchesFacing(clickedState, secondState)  		 &&
			   isEmpty(clickedFrame)              		  		 &&
			   isEmpty(secondFrame);
	}
	
	/*
	 * A helper method that fills the frames with each painting piece for 4 block paintings.
	 */
	private static boolean frameFits4BlockPainting(BlockState clickedState, BlockState secondState, BlockState thirdState, BlockState fourthState, VeFrameTileEntity clickedFrame, VeFrameTileEntity secondFrame, VeFrameTileEntity thirdFrame, VeFrameTileEntity fourthFrame)
	{
		return clickedState.getBlock() == secondState.getBlock() &&
			   clickedState.getBlock() == thirdState.getBlock()  &&
			   clickedState.getBlock() == fourthState.getBlock() &&
			   matchesFacing(clickedState, secondState) 		 &&
			   matchesFacing(clickedState, thirdState) 			 &&
			   matchesFacing(clickedState, fourthState) 	 	 &&
			   isEmpty(clickedFrame)            		 	  	 &&
			   isEmpty(secondFrame)    			 	 			 &&
			   isEmpty(thirdFrame)	 							 &&
			   isEmpty(fourthFrame);
	}
	
	/*
	 * A helper method that checks if the frame is empty.
	 */
	private static boolean isEmpty(VeFrameTileEntity frameTileEntity)
	{
		return frameTileEntity.getInventory().get(0) == ItemStack.EMPTY;
	}
	
	private static boolean matchesFacing(BlockState state, BlockState worldState)
	{
		return state.get(FACING) == worldState.get(FACING);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState northState = worldIn.getBlockState(pos.offset(Direction.NORTH.getOpposite()));
		BlockState southState = worldIn.getBlockState(pos.offset(Direction.SOUTH.getOpposite()));
		BlockState westState = worldIn.getBlockState(pos.offset(Direction.WEST.getOpposite()));
		BlockState eastState = worldIn.getBlockState(pos.offset(Direction.EAST.getOpposite()));
		
		if(northState.isSolid() && state.get(FACING) == Direction.NORTH							   ||
		   southState.isSolid() && state.get(FACING) == Direction.SOUTH 						   ||
		   westState.isSolid() && state.get(FACING) == Direction.WEST  							   ||
		   eastState.isSolid() && state.get(FACING) == Direction.EAST   						   ||
		   BlockTags.SIGNS.contains(northState.getBlock()) && state.get(FACING) == Direction.NORTH ||
		   BlockTags.SIGNS.contains(southState.getBlock()) && state.get(FACING) == Direction.SOUTH ||
		   BlockTags.SIGNS.contains(westState.getBlock())  && state.get(FACING) == Direction.WEST  ||
		   BlockTags.SIGNS.contains(eastState.getBlock()) && state.get(FACING) == Direction.EAST)
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
			super.onBlockHarvested(worldIn.getWorld(), currentPos, stateIn, null);
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
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		
		TileEntity topTileEntity = world.getTileEntity(pos.up());
		TileEntity bottomTileEntity = world.getTileEntity(pos.down());
		
		TileEntity eastTileEntity = world.getTileEntity(pos.east());
		TileEntity westTileEntity = world.getTileEntity(pos.west());
		TileEntity northTileEntity = world.getTileEntity(pos.north());
		TileEntity southTileEntity = world.getTileEntity(pos.south());
		
		TileEntity topEastTileEntity = world.getTileEntity(pos.up().east());
		TileEntity topWestTileEntity = world.getTileEntity(pos.up().west());
		TileEntity topNorthTileEntity = world.getTileEntity(pos.up().north());
		TileEntity topSouthTileEntity = world.getTileEntity(pos.up().south());
		
		TileEntity bottomEastTileEntity = world.getTileEntity(pos.down().east());
		TileEntity bottomWestTileEntity = world.getTileEntity(pos.down().west());
		TileEntity bottomNorthTileEntity = world.getTileEntity(pos.down().north());
		TileEntity bottomSouthTileEntity = world.getTileEntity(pos.down().south());
		
		BlockState topState = world.getBlockState(pos.up());
		BlockState bottomState = world.getBlockState(pos.down());
		
		BlockState eastState = world.getBlockState(pos.east());
		BlockState westState = world.getBlockState(pos.west());
		BlockState southState = world.getBlockState(pos.south());
		BlockState northState = world.getBlockState(pos.north());
		
		BlockState topEastState = world.getBlockState(pos.up().east());
		BlockState topWestState = world.getBlockState(pos.up().west());
		BlockState topSouthState = world.getBlockState(pos.up().south());
		BlockState topNorthState = world.getBlockState(pos.up().north());
		
		BlockState bottomEastState = world.getBlockState(pos.down().east());
		BlockState bottomWestState = world.getBlockState(pos.down().west());
		BlockState bottomSouthState = world.getBlockState(pos.down().south());
		BlockState bottomNorthState = world.getBlockState(pos.down().north());
		
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
			
		harvest2BlockPainting(world,
		  			  		  pos,
		  			  		  state,
		  			  		  topState,
		  			  		  tileEntity,
		  			  		  topTileEntity,
		  			  		  tallPaintingMap);
			
		harvest2BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  tileEntity,
							  bottomTileEntity,
							  tallPaintingMap);
			
		harvest2BlockPainting(world,
				  			  pos,
				  			  state,
				  			  eastState,
				  			  tileEntity,
				  			  eastTileEntity,
				  			  sidePaintingMap);

		harvest2BlockPainting(world,
							  pos,
							  state,
							  westState,
							  tileEntity,
							  westTileEntity,
							  sidePaintingMap);

		harvest2BlockPainting(world,
							  pos,
							  state,
							  northState,
							  tileEntity,
							  northTileEntity,
							  sidePaintingMap);
		
		harvest2BlockPainting(world,
							  pos,
							  state,
							  southState,
							  tileEntity,
							  southTileEntity,
							  sidePaintingMap);
		
		harvest4BlockPainting(world,
				  			  pos,
				  			  state,
				  			  topState,
				  			  eastState,
				  			  topEastState,
				  			  tileEntity,
				  			  topTileEntity,
				  			  eastTileEntity,
				  			  topEastTileEntity,
				  			  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  eastState,
							  bottomEastState,
							  tileEntity,
							  bottomTileEntity,
							  eastTileEntity,
							  bottomEastTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  westState,
							  topWestState,
							  tileEntity,
							  topTileEntity,
							  westTileEntity,
							  topWestTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  westState,
							  bottomWestState,
							  tileEntity,
							  bottomTileEntity,
							  westTileEntity,
							  bottomWestTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  northState,
							  topNorthState,
							  tileEntity,
							  topTileEntity,
							  northTileEntity,
							  topNorthTileEntity,
							  fourPaintingMap);
		
		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  northState,
							  bottomNorthState,
							  tileEntity,
							  bottomTileEntity,
							  northTileEntity,
							  bottomNorthTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  southState,
							  topSouthState,
							  tileEntity,
							  topTileEntity,
							  southTileEntity,
							  topSouthTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  southState,
							  bottomSouthState,
							  tileEntity,
							  bottomTileEntity,
							  southTileEntity,
							  bottomSouthTileEntity,
							  fourPaintingMap);
		
		super.onBlockHarvested(world, pos, state, player);
	}
	
	/*
	@Override
	public void spawnAdditionalDrops(BlockState state, World world, BlockPos pos, ItemStack stack)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		
		TileEntity topTileEntity = world.getTileEntity(pos.up());
		TileEntity bottomTileEntity = world.getTileEntity(pos.down());
		
		TileEntity eastTileEntity = world.getTileEntity(pos.east());
		TileEntity westTileEntity = world.getTileEntity(pos.west());
		TileEntity northTileEntity = world.getTileEntity(pos.north());
		TileEntity southTileEntity = world.getTileEntity(pos.south());
		
		TileEntity topEastTileEntity = world.getTileEntity(pos.up().east());
		TileEntity topWestTileEntity = world.getTileEntity(pos.up().west());
		TileEntity topNorthTileEntity = world.getTileEntity(pos.up().north());
		TileEntity topSouthTileEntity = world.getTileEntity(pos.up().south());
		
		TileEntity bottomEastTileEntity = world.getTileEntity(pos.down().east());
		TileEntity bottomWestTileEntity = world.getTileEntity(pos.down().west());
		TileEntity bottomNorthTileEntity = world.getTileEntity(pos.down().north());
		TileEntity bottomSouthTileEntity = world.getTileEntity(pos.down().south());
		
		BlockState topState = world.getBlockState(pos.up());
		BlockState bottomState = world.getBlockState(pos.down());
		
		BlockState eastState = world.getBlockState(pos.east());
		BlockState westState = world.getBlockState(pos.west());
		BlockState southState = world.getBlockState(pos.south());
		BlockState northState = world.getBlockState(pos.north());
		
		BlockState topEastState = world.getBlockState(pos.up().east());
		BlockState topWestState = world.getBlockState(pos.up().west());
		BlockState topSouthState = world.getBlockState(pos.up().south());
		BlockState topNorthState = world.getBlockState(pos.up().north());
		
		BlockState bottomEastState = world.getBlockState(pos.down().east());
		BlockState bottomWestState = world.getBlockState(pos.down().west());
		BlockState bottomSouthState = world.getBlockState(pos.down().south());
		BlockState bottomNorthState = world.getBlockState(pos.down().north());
		
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
		
		harvest2BlockPainting(world,
		  			  		  pos,
		  			  		  state,
		  			  		  topState,
		  			  		  tileEntity,
		  			  		  topTileEntity,
		  			  		  tallPaintingMap);
			
		harvest2BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  tileEntity,
							  bottomTileEntity,
							  tallPaintingMap);
			
		harvest2BlockPainting(world,
				  			  pos,
				  			  state,
				  			  eastState,
				  			  tileEntity,
				  			  eastTileEntity,
				  			  sidePaintingMap);

		harvest2BlockPainting(world,
							  pos,
							  state,
							  westState,
							  tileEntity,
							  westTileEntity,
							  sidePaintingMap);

		harvest2BlockPainting(world,
							  pos,
							  state,
							  northState,
							  tileEntity,
							  northTileEntity,
							  sidePaintingMap);
		
		harvest2BlockPainting(world,
							  pos,
							  state,
							  southState,
							  tileEntity,
							  southTileEntity,
							  sidePaintingMap);
		
		harvest4BlockPainting(world,
				  			  pos,
				  			  state,
				  			  topState,
				  			  eastState,
				  			  topEastState,
				  			  tileEntity,
				  			  topTileEntity,
				  			  eastTileEntity,
				  			  topEastTileEntity,
				  			  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  eastState,
							  bottomEastState,
							  tileEntity,
							  bottomTileEntity,
							  eastTileEntity,
							  bottomEastTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  westState,
							  topWestState,
							  tileEntity,
							  topTileEntity,
							  westTileEntity,
							  topWestTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  westState,
							  bottomWestState,
							  tileEntity,
							  bottomTileEntity,
							  westTileEntity,
							  bottomWestTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  northState,
							  topNorthState,
							  tileEntity,
							  topTileEntity,
							  northTileEntity,
							  topNorthTileEntity,
							  fourPaintingMap);
		
		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  northState,
							  bottomNorthState,
							  tileEntity,
							  bottomTileEntity,
							  northTileEntity,
							  bottomNorthTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  topState,
							  southState,
							  topSouthState,
							  tileEntity,
							  topTileEntity,
							  southTileEntity,
							  topSouthTileEntity,
							  fourPaintingMap);

		harvest4BlockPainting(world,
							  pos,
							  state,
							  bottomState,
							  southState,
							  bottomSouthState,
							  tileEntity,
							  bottomTileEntity,
							  southTileEntity,
							  bottomSouthTileEntity,
							  fourPaintingMap);
	}
	*/
	
	/*
	 * A helper method that harvests 2 block paintings.
	 */
	private static void harvest2BlockPainting(World world, BlockPos pos, BlockState state, BlockState secondState, TileEntity clickedTileEntity, TileEntity secondTileEntity, Map<Item, Item> map)
	{
		if(clickedTileEntity instanceof VeFrameTileEntity &&
		   secondTileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity clickedFrame = (VeFrameTileEntity) clickedTileEntity;
			VeFrameTileEntity secondFrame = (VeFrameTileEntity) secondTileEntity;
			Item inventoryItem = clickedFrame.getInventory().get(0).getItem();
			
			if(map.containsKey(inventoryItem) 			  		 	 &&
			   secondState.getBlock() == state.getBlock()			 &&
			   matchesFacing(state, secondState) 		 			 &&
			   !isEmpty(clickedFrame)			 				     &&
			   !isEmpty(secondFrame)					    		 &&
			   map.get(clickedFrame.getInventory().get(0).getItem()) ==
			   map.get(secondFrame.getInventory().get(0).getItem()))
			{
				System.out.print("Drop Items");
				
				spawnAsEntity(world, pos, new ItemStack(map.get(inventoryItem)));
				
				secondFrame.getInventory().clear();
			}
		}
	}
	
	/*
	 * A helper method that harvests 4 block paintings.
	 */
	private static void harvest4BlockPainting(World world, BlockPos pos, BlockState state, BlockState state2, BlockState state3, BlockState state4, TileEntity clickedTileEntity, TileEntity secondTileEntity, TileEntity thirdTileEntity, TileEntity fourthTileEntity, Map<Item, Item> map)
	{
		if(clickedTileEntity instanceof VeFrameTileEntity &&
		   secondTileEntity instanceof VeFrameTileEntity  &&
		   thirdTileEntity instanceof VeFrameTileEntity   &&
		   fourthTileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity clickedFrame = (VeFrameTileEntity) clickedTileEntity;
			VeFrameTileEntity secondFrame = (VeFrameTileEntity) secondTileEntity;
			VeFrameTileEntity thirdFrame = (VeFrameTileEntity) thirdTileEntity;
			VeFrameTileEntity fourthFrame = (VeFrameTileEntity) fourthTileEntity;
			Item inventoryItem = clickedFrame.getInventory().get(0).getItem();
			
			if(map.containsKey(inventoryItem) 		 &&
			   state.getBlock() == state2.getBlock() &&
			   state.getBlock() == state3.getBlock() &&
			   state.getBlock() == state4.getBlock() &&
			   matchesFacing(state, state2)			 &&
			   matchesFacing(state, state3)			 &&
			   matchesFacing(state, state4)		     &&
			   !isEmpty(clickedFrame)	   		 	 &&
			   !isEmpty(secondFrame)   				 &&
			   !isEmpty(thirdFrame)					 &&
			   !isEmpty(fourthFrame))
			{
				Item frameItem = clickedFrame.getInventory().get(0).getItem();
				Item secondFrameItem = secondFrame.getInventory().get(0).getItem();
				Item thirdFrameItem = thirdFrame.getInventory().get(0).getItem();
				Item fourthFrameItem = fourthFrame.getInventory().get(0).getItem();
				
				if(map.get(frameItem) 		==
				   map.get(secondFrameItem) &&
				   map.get(secondFrameItem) ==
				   map.get(thirdFrameItem)  &&
				   map.get(thirdFrameItem)  ==
				   map.get(fourthFrameItem))
				{
					spawnAsEntity(world, pos, new ItemStack(map.get(inventoryItem)));
					
					secondFrame.getInventory().clear();
					thirdFrame.getInventory().clear();
					fourthFrame.getInventory().clear();
				}
			}
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch((Direction)state.get(FACING))
		{
			case NORTH:
				return defineShapes(state,
									state.get(WEST),
									state.get(EAST),
									Direction.NORTH,
									FRAME_NORTH_TOP_RIM_SHAPE,
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
				return defineShapes(state,
									state.get(WEST),
									state.get(EAST),
									Direction.NORTH,
									FRAME_SOUTH_TOP_RIM_SHAPE,
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
				return defineShapes(state,
									state.get(SOUTH),
									state.get(NORTH),
									Direction.WEST,
									FRAME_WEST_TOP_RIM_SHAPE,
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
				return defineShapes(state,
									state.get(SOUTH),
									state.get(NORTH),
									Direction.WEST,
									FRAME_EAST_TOP_RIM_SHAPE,
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
