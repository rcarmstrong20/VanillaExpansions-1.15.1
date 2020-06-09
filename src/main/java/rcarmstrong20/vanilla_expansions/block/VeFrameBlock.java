package rcarmstrong20.vanilla_expansions.block;

import java.util.HashMap;
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
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult rayTrace)
	{
		ItemStack heldItem = player.getHeldItem(handIn);
		
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
			
			if(VeTwoBlockPainting.getTopPaintingMap().containsKey(heldItem.getItem()) ||
			   VeTwoBlockPainting.getBottomPaintingMap().containsKey(heldItem.getItem()))
			{
				if(topTileEntity instanceof VeFrameTileEntity || bottomTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					
					if(VeTwoBlockPainting.frameFitsPainting(state, topState, clickedFrame, topFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getBottomPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeTwoBlockPainting.getTopPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(VeTwoBlockPainting.frameFitsPainting(state, bottomState, clickedFrame, bottomFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getTopPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeTwoBlockPainting.getBottomPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(VeTwoBlockPainting.getRightPaintingMap().containsKey(heldItem.getItem()) || VeTwoBlockPainting.getLeftPaintingMap().containsKey(heldItem.getItem()))
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
					
					if(VeTwoBlockPainting.frameFitsPainting(state, eastState, clickedFrame, eastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeTwoBlockPainting.getLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(VeTwoBlockPainting.frameFitsPainting(state, westState, clickedFrame, westFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeTwoBlockPainting.getRightPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(VeTwoBlockPainting.frameFitsPainting(state, southState, clickedFrame, southFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(VeTwoBlockPainting.getRightPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(VeTwoBlockPainting.frameFitsPainting(state, northState, clickedFrame, northFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeTwoBlockPainting.getRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(VeTwoBlockPainting.getLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(VeFourBlockPainting.getBottomRightPaintingMap().containsKey(heldItem.getItem()) ||
					VeFourBlockPainting.getBottomLeftPaintingMap().containsKey(heldItem.getItem())  ||
					VeFourBlockPainting.getTopRightPaintingMap().containsKey(heldItem.getItem())    ||
					VeFourBlockPainting.getTopLeftPaintingMap().containsKey(heldItem.getItem()))
			{
				//Place the painting from top west to bottom east.
				if(bottomTileEntity instanceof VeFrameTileEntity  ||
						eastTileEntity instanceof VeFrameTileEntity    ||
						bottomEastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					
					if(VeFourBlockPainting.frameFitsPainting(state,
							   				   					   bottomState,
							   				   					   eastState,
							   				   					   bottomEastState,
							   				   					   clickedFrame,
							   				   					   bottomFrame,
							   				   					   eastFrame,
							   				   					   bottomEastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
																   topState,
																   eastState,
																   topEastState,
																   clickedFrame,
																   topFrame,
																   eastFrame,
																   topEastFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topEastFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
							   				   					   bottomState,
							   				   					   westState,
							   				   					   bottomWestState,
							   				   					   clickedFrame,
							   				   					   bottomFrame,
							   				   					   westFrame,
							   				   					   bottomWestFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomWestFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
																   topState,
																   westState,
																   topWestState,
																   clickedFrame,
																   topFrame,
																   westFrame,
																   topWestFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topWestFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
																   bottomState,
																   northState,
																   bottomNorthState,
																   clickedFrame,
																   bottomFrame,
																   northFrame,
																   bottomNorthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem())))   &&
						   bottomFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) 	  &&
						   bottomNorthFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
							   				   					   topState,
							   				   					   northState,
							   				   					   topNorthState,
							   				   					   clickedFrame,
							   				   					   topFrame,
							   				   					   northFrame,
							   				   					   topNorthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topNorthFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
																   bottomState,
																   southState,
																   bottomSouthState,
																   clickedFrame,
																   bottomFrame,
																   southFrame,
																   bottomSouthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomSouthFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))))
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
					
					if(VeFourBlockPainting.frameFitsPainting(state,
							   				   					   topState,
							   				   					   southState,
							   				   					   topSouthState,
							   				   					   clickedFrame,
							   				   					   topFrame,
							   				   					   southFrame,
							   				   					   topSouthFrame))
					{
						if(clickedFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeFourBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(VeFourBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topSouthFrame.addItem(new ItemStack(VeFourBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))))
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
		
		if(tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity clickedFrame = (VeFrameTileEntity) tileEntity;
			
			Item inventoryItem = clickedFrame.getInventory().get(0).getItem();
			
			if(tallPaintingMap.containsKey(inventoryItem))
			{
				//Harvest from bottom to top.
				if(topTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, topState, clickedFrame, topFrame, VeTwoBlockPainting.getBottomPaintingMap(), VeTwoBlockPainting.getTopPaintingMap()))
					{
						topFrame.getInventory().clear();
					}
				}
				//Harvest from top to bottom.
				if(bottomTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, bottomState, clickedFrame, bottomFrame, VeTwoBlockPainting.getTopPaintingMap(), VeTwoBlockPainting.getBottomPaintingMap()))
					{
						bottomFrame.getInventory().clear();
					}
				}
				spawnAsEntity(world, pos, new ItemStack(tallPaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(sidePaintingMap.containsKey(inventoryItem))
			{
				//Harvest from west to east
				if(eastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, eastState, clickedFrame, eastFrame, VeTwoBlockPainting.getRightPaintingMap(), VeTwoBlockPainting.getLeftPaintingMap()))
					{
						eastFrame.getInventory().clear(); //Empty the east frame
					}
				}
				//Harvest from east to west
				if(westTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, westState, clickedFrame, westFrame, VeTwoBlockPainting.getLeftPaintingMap(), VeTwoBlockPainting.getRightPaintingMap()))
					{
						westFrame.getInventory().clear(); //Empty the west frame
					}
				}
				//Harvest from south to north
				if(northTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, northState, clickedFrame, northFrame, VeTwoBlockPainting.getRightPaintingMap(), VeTwoBlockPainting.getLeftPaintingMap()))
					{
						northFrame.getInventory().clear(); //Empty the north frame
					}
				}
				//Harvest from north to south
				if(southTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					
					if(VeTwoBlockPainting.isPaintingPart(state, southState, clickedFrame, southFrame, VeTwoBlockPainting.getLeftPaintingMap(), VeTwoBlockPainting.getRightPaintingMap()))
					{
						southFrame.getInventory().clear(); //Empty the south frame
					}
				}
				spawnAsEntity(world, pos, new ItemStack(sidePaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(fourPaintingMap.containsKey(inventoryItem))
			{
				//Harvest from bottom west to top east.
				if(bottomTileEntity instanceof VeFrameTileEntity &&
				   eastTileEntity instanceof VeFrameTileEntity   &&
				   bottomEastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, bottomState, eastState, bottomEastState, clickedFrame, bottomFrame, eastFrame, bottomEastFrame, VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap()))
					{
						bottomFrame.getInventory().clear();
						eastFrame.getInventory().clear();
						bottomEastFrame.getInventory().clear();
					}
				}
				//Harvest from top west to bottom east.
				if(topTileEntity instanceof VeFrameTileEntity  &&
				   eastTileEntity instanceof VeFrameTileEntity &&
				   topEastTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity topEastFrame = (VeFrameTileEntity) topEastTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, topState, eastState, topEastState, clickedFrame, topFrame, eastFrame, topEastFrame, VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap()))
					{
						topFrame.getInventory().clear();
						eastFrame.getInventory().clear();
						topEastFrame.getInventory().clear();
					}
				}
				//Harvest from bottom east to top west.
				if(topTileEntity instanceof VeFrameTileEntity  &&
				   westTileEntity instanceof VeFrameTileEntity &&
				   topWestTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity topWestFrame = (VeFrameTileEntity) topWestTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, topState, westState, topWestState, clickedFrame, topFrame, westFrame, topWestFrame, VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap()))
					{
						topFrame.getInventory().clear();
						westFrame.getInventory().clear();
						topWestFrame.getInventory().clear();
					}
				}
				//Harvest from bottom east to top west.
				if(bottomTileEntity instanceof VeFrameTileEntity &&
				   westTileEntity instanceof VeFrameTileEntity 	 &&
				   bottomWestTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity bottomWestFrame = (VeFrameTileEntity) bottomWestTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, bottomState, westState, bottomWestState, clickedFrame, bottomFrame, westFrame, bottomWestFrame, VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap()))
					{
						bottomFrame.getInventory().clear();
						westFrame.getInventory().clear();
						bottomWestFrame.getInventory().clear();
					}
				}
				//Harvest from bottom north to top south.
				if(bottomTileEntity instanceof VeFrameTileEntity &&
				   southTileEntity instanceof VeFrameTileEntity  &&
				   bottomSouthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					VeFrameTileEntity bottomSouthFrame = (VeFrameTileEntity) bottomSouthTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, bottomState, southState, bottomSouthState, clickedFrame, bottomFrame, southFrame, bottomSouthFrame, VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap()))
					{
						bottomFrame.getInventory().clear();
						southFrame.getInventory().clear();
						bottomSouthFrame.getInventory().clear();
					}
				}
				//Harvest from bottom north to top south.
				if(topTileEntity instanceof VeFrameTileEntity   &&
				   southTileEntity instanceof VeFrameTileEntity &&
				   topSouthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					VeFrameTileEntity topSouthFrame = (VeFrameTileEntity) topSouthTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, topState, southState, topSouthState, clickedFrame, topFrame, southFrame, topSouthFrame, VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap()))
					{
						topFrame.getInventory().clear();
						southFrame.getInventory().clear();
						topSouthFrame.getInventory().clear();
					}
				}
				//Harvest from bottom south to top north.
				if(topTileEntity instanceof VeFrameTileEntity   &&
				   northTileEntity instanceof VeFrameTileEntity &&
				   topNorthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					VeFrameTileEntity topNorthFrame = (VeFrameTileEntity) topNorthTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, topState, northState, topNorthState, clickedFrame, topFrame, northFrame, topNorthFrame, VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap()))
					{
						topFrame.getInventory().clear();
						northFrame.getInventory().clear();
						topNorthFrame.getInventory().clear();
					}
				}
				//Harvest from top south to bottom north.
				if(bottomTileEntity instanceof VeFrameTileEntity &&
				   northTileEntity instanceof VeFrameTileEntity  &&
				   bottomNorthTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					VeFrameTileEntity bottomNorthFrame = (VeFrameTileEntity) bottomNorthTileEntity;
					
					if(VeFourBlockPainting.isPaintingPart(state, bottomState, northState, bottomNorthState, clickedFrame, bottomFrame, northFrame, bottomNorthFrame, VeFourBlockPainting.getTopRightPaintingMap(), VeFourBlockPainting.getBottomRightPaintingMap(), VeFourBlockPainting.getTopLeftPaintingMap(), VeFourBlockPainting.getBottomLeftPaintingMap()))
					{
						bottomFrame.getInventory().clear();
						northFrame.getInventory().clear();
						bottomNorthFrame.getInventory().clear();
					}
				}
				spawnAsEntity(world, pos, new ItemStack(fourPaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(VeItemTags.PAINTINGS.contains(inventoryItem) && !isEmpty(clickedFrame))
			{
				spawnAsEntity(world, pos, new ItemStack(inventoryItem)); //Spawn the drops in the world.
			}
		}
		super.onBlockHarvested(world, pos, state, player);
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
	
	/**
	 * A sub-class that holds all the data for the 2 block paintings.
	 */
	static class VeTwoBlockPainting
	{
		public static Map<Item, Item> getTopPaintingMap()
		{
			Map<Item, Item> topPaintingMap = new HashMap<>();
			
			topPaintingMap.put(VeItems.wanderer_painting, VeItems.wanderer_painting_top);
			topPaintingMap.put(VeItems.graham_painting, VeItems.graham_painting_top);
			
			return topPaintingMap;
		}
		
		public static Map<Item, Item> getBottomPaintingMap()
		{
			Map<Item, Item> bottomPaintingMap = new HashMap<>();
			
			bottomPaintingMap.put(VeItems.wanderer_painting, VeItems.wanderer_painting_bottom);
			bottomPaintingMap.put(VeItems.graham_painting, VeItems.graham_painting_bottom);
			
			return bottomPaintingMap;
		}
		
		public static Map<Item, Item> getRightPaintingMap()
		{
			Map<Item, Item> rightPaintingMap = new HashMap<>();
			
			rightPaintingMap.put(VeItems.courbet_painting, VeItems.courbet_painting_right);
			rightPaintingMap.put(VeItems.creebet_painting, VeItems.creebet_painting_right);
			rightPaintingMap.put(VeItems.pool_painting, VeItems.pool_painting_right);
			rightPaintingMap.put(VeItems.sea_painting, VeItems.sea_painting_right);
			rightPaintingMap.put(VeItems.sunset_painting, VeItems.sunset_painting_right);
			
			return rightPaintingMap;
		}
		
		public static Map<Item, Item> getLeftPaintingMap()
		{
			Map<Item, Item> leftPaintingMap = new HashMap<>();
			
			leftPaintingMap.put(VeItems.courbet_painting, VeItems.courbet_painting_left);
			leftPaintingMap.put(VeItems.creebet_painting, VeItems.creebet_painting_left);
			leftPaintingMap.put(VeItems.pool_painting, VeItems.pool_painting_left);
			leftPaintingMap.put(VeItems.sea_painting, VeItems.sea_painting_left);
			leftPaintingMap.put(VeItems.sunset_painting, VeItems.sunset_painting_left);
			
			return leftPaintingMap;
		}
		
		/**
		 * A helper method that checks if there are 2 available frames that can be filled with the given painting.
		 */
		private static boolean frameFitsPainting(BlockState clickedState, BlockState secondState, VeFrameTileEntity clickedFrame, VeFrameTileEntity secondFrame)
		{
			return secondState.getBlock() == clickedState.getBlock() &&
				   matchesFacing(clickedState, secondState)  		 &&
				   isEmpty(clickedFrame)              		  		 &&
				   isEmpty(secondFrame);
		}
		
		/**
		 * A helper method that returns true if both blocks make up the painting harvested.
		 */
		public static boolean isPaintingPart(BlockState clickedState, BlockState state2, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, Map<Item, Item> map, Map<Item, Item> map2)
		{
			return state2.getBlock() == clickedState.getBlock()	  			 	   &&
				   matchesFacing(clickedState, state2) 		 				 	   &&
				   !isEmpty(clickedFrame)			  	  					 	   &&
				   !isEmpty(frame2)					  	  					 	   &&
				   map.containsValue(clickedFrame.getInventory().get(0).getItem()) &&
				   map2.containsValue(frame2.getInventory().get(0).getItem());
		}
	}
	
	/**
	 * A sub-class that holds all the data for the 4 block paintings.
	 */
	static class VeFourBlockPainting
	{
		public static Map<Item, Item> getBottomRightPaintingMap()
		{
			Map<Item, Item> bottomRightPaintingMap = new HashMap<>();
			
			bottomRightPaintingMap.put(VeItems.wither_painting, VeItems.wither_painting_bottom_right);
			bottomRightPaintingMap.put(VeItems.bust_painting, VeItems.bust_painting_bottom_right);
			bottomRightPaintingMap.put( VeItems.match_painting, VeItems.match_painting_bottom_right);
			bottomRightPaintingMap.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_bottom_right);
			bottomRightPaintingMap.put(VeItems.stage_painting, VeItems.stage_painting_bottom_right);
			bottomRightPaintingMap.put(VeItems.void_painting, VeItems.void_painting_bottom_right);
			
			return bottomRightPaintingMap;
		}
		
		public static Map<Item, Item> getBottomLeftPaintingMap()
		{
			Map<Item, Item> bottomLeftPaintingMap = new HashMap<>();
			
			bottomLeftPaintingMap.put(VeItems.wither_painting, VeItems.wither_painting_bottom_left);
			bottomLeftPaintingMap.put(VeItems.bust_painting, VeItems.bust_painting_bottom_left);
			bottomLeftPaintingMap.put(VeItems.match_painting, VeItems.match_painting_bottom_left);
			bottomLeftPaintingMap.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_bottom_left);
			bottomLeftPaintingMap.put(VeItems.stage_painting, VeItems.stage_painting_bottom_left);
			bottomLeftPaintingMap.put(VeItems.void_painting, VeItems.void_painting_bottom_left);
			
			return bottomLeftPaintingMap;
		}
		
		public static Map<Item, Item> getTopRightPaintingMap()
		{
			Map<Item, Item> topRightPaintingMap = new HashMap<>();
			
			topRightPaintingMap.put(VeItems.wither_painting, VeItems.wither_painting_top_right);
			topRightPaintingMap.put(VeItems.bust_painting, VeItems.bust_painting_top_right);
			topRightPaintingMap.put(VeItems.match_painting, VeItems.match_painting_top_right);
			topRightPaintingMap.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_top_right);
			topRightPaintingMap.put(VeItems.stage_painting, VeItems.stage_painting_top_right);
			topRightPaintingMap.put(VeItems.void_painting, VeItems.void_painting_top_right);
			
			return topRightPaintingMap;
		}
		
		public static Map<Item, Item> getTopLeftPaintingMap()
		{
			Map<Item, Item> topLeftPaintingMap = new HashMap<>();
			
			topLeftPaintingMap.put(VeItems.wither_painting, VeItems.wither_painting_top_left);
			topLeftPaintingMap.put(VeItems.bust_painting, VeItems.bust_painting_top_left);
			topLeftPaintingMap.put(VeItems.match_painting, VeItems.match_painting_top_left);
			topLeftPaintingMap.put(VeItems.skull_and_roses_painting, VeItems.skull_and_roses_painting_top_left);
			topLeftPaintingMap.put(VeItems.stage_painting, VeItems.stage_painting_top_left);
			topLeftPaintingMap.put(VeItems.void_painting, VeItems.void_painting_top_left);
			
			return topLeftPaintingMap;
		}
		
		/**
		 * A helper method that checks if there are 4 available frames that can be filled with the given painting.
		 */
		private static boolean frameFitsPainting(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4)
		{
			return VeTwoBlockPainting.frameFitsPainting(clickedState, state2, clickedFrame, frame2) &&
				   clickedState.getBlock() == state3.getBlock()  									&&
				   clickedState.getBlock() == state4.getBlock() 									&&
				   matchesFacing(clickedState, state3) 												&&
				   matchesFacing(clickedState, state4) 									 			&&
				   isEmpty(frame3)	 							 									&&
				   isEmpty(frame4);
		}
		
		/**
		 * A helper method that returns true if all 4 blocks make up the painting harvested.
		 */
		public static boolean isPaintingPart(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4, Map<Item, Item> map, Map<Item, Item> map2, Map<Item, Item> map3, Map<Item, Item> map4)
		{
			return VeTwoBlockPainting.isPaintingPart(clickedState, state2, clickedFrame, frame2, map, map2) &&
				   state3.getBlock() == clickedState.getBlock()								   				&&
				   state4.getBlock() == clickedState.getBlock()								   				&&
				   !isEmpty(frame3)														   	 				&&
				   !isEmpty(frame4)																			&&
				   map3.containsValue(frame3.getInventory().get(0).getItem())							 	&&
				   map4.containsValue(frame4.getInventory().get(0).getItem());
		}
	}
	
	/**
	 * A sub-class that holds all the data for the 8 block paintings.
	 */
	static class VeEightBlockPainting
	{
		public static Map<Item, Item> getTopRightPaintingMap()
		{
			Map<Item, Item> topRightPaintingMap = new HashMap<>();
			
			topRightPaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_top_right);
			
			return topRightPaintingMap;
		}
		
		public static Map<Item, Item> getTopRightMiddlePaintingMap()
		{
			Map<Item, Item> topRightMiddlePaintingMap = new HashMap<>();
			
			topRightMiddlePaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_top_right_middle);
			
			return topRightMiddlePaintingMap;
		}
		
		public static Map<Item, Item> getTopLeftMiddlePaintingMap()
		{
			Map<Item, Item> topLeftMiddlePaintingMap = new HashMap<>();
			
			topLeftMiddlePaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_top_left_middle);
			
			return topLeftMiddlePaintingMap;
		}
		
		public static Map<Item, Item> getTopLeftPaintingMap()
		{
			Map<Item, Item> topLeftPaintingMap = new HashMap<>();
			
			topLeftPaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_top_left);
			
			return topLeftPaintingMap;
		}
		
		public static Map<Item, Item> getBottomRightPaintingMap()
		{
			Map<Item, Item> bottomRightPaintingMap = new HashMap<>();
			
			bottomRightPaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_bottom_right);
			
			return bottomRightPaintingMap;
		}
		
		public static Map<Item, Item> getBottomRightMiddlePaintingMap()
		{
			Map<Item, Item> bottomRightMiddlePaintingMap = new HashMap<>();
			
			bottomRightMiddlePaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_top_right_middle);
			
			return bottomRightMiddlePaintingMap;
		}
		
		public static Map<Item, Item> getBottomLeftMiddlePaintingMap()
		{
			Map<Item, Item> bottomLeftMiddlePaintingMap = new HashMap<>();
			
			bottomLeftMiddlePaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_bottom_left_middle);
			
			return bottomLeftMiddlePaintingMap;
		}
		
		public static Map<Item, Item> getBottomLeftPaintingMap()
		{
			Map<Item, Item> bottomLeftPaintingMap = new HashMap<>();
			
			bottomLeftPaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_bottom_left);
			
			return bottomLeftPaintingMap;
		}
		
		
	}
}
