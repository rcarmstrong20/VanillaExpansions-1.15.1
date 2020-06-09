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
			
			if(getTopPaintingHashMap().containsKey(heldItem.getItem()) || getBottomPaintingHashMap().containsKey(heldItem.getItem()))
			{
				if(topTileEntity instanceof VeFrameTileEntity || bottomTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					
					if(frameFits2BlockPainting(state, topState, clickedFrame, topFrame))
					{
						if(clickedFrame.addItem(new ItemStack(getBottomPaintingHashMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(getTopPaintingHashMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, bottomState, clickedFrame, bottomFrame))
					{
						if(clickedFrame.addItem(new ItemStack(getTopPaintingHashMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(getBottomPaintingHashMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(getRightPaintingMap().containsKey(heldItem.getItem()) || getLeftPaintingMap().containsKey(heldItem.getItem()))
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
						if(clickedFrame.addItem(new ItemStack(getRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(getLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, westState, clickedFrame, westFrame))
					{
						if(clickedFrame.addItem(new ItemStack(getLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(getRightPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, southState, clickedFrame, southFrame))
					{
						if(clickedFrame.addItem(new ItemStack(getLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(getRightPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
					else if(frameFits2BlockPainting(state, northState, clickedFrame, northFrame))
					{
						if(clickedFrame.addItem(new ItemStack(getRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(getLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
			}
			else if(getBottomRightPaintingMap().containsKey(heldItem.getItem()) ||
					getBottomLeftPaintingMap().containsKey(heldItem.getItem())  ||
					getTopRightPaintingMap().containsKey(heldItem.getItem())    ||
					getLeftPaintingMap().containsKey(heldItem.getItem()))
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
						if(clickedFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topEastFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomWestFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topWestFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem())))   &&
						   bottomFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) 	  &&
						   bottomNorthFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   northFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topNorthFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomSouthFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))))
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
						if(clickedFrame.addItem(new ItemStack(getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   southFrame.addItem(new ItemStack(getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topSouthFrame.addItem(new ItemStack(getTopRightPaintingMap().get(heldItem.getItem()))))
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
	
	public static Map<Item, Item> getTopPaintingHashMap()
	{
		Map<Item, Item> topPaintingMap = new HashMap<>();
		
		topPaintingMap.put(VeItems.wanderer_painting, VeItems.wanderer_painting_top);
		topPaintingMap.put(VeItems.graham_painting, VeItems.graham_painting_top);
		
		return topPaintingMap;
	}
	
	public static Map<Item, Item> getBottomPaintingHashMap()
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
					
					if(is2BlockPaintingPart(state, topState, clickedFrame, topFrame, getBottomPaintingHashMap(), getTopPaintingHashMap()))
					{
						topFrame.getInventory().clear();
					}
				}
				//Harvest from top to bottom.
				if(bottomTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					
					if(is2BlockPaintingPart(state, bottomState, clickedFrame, bottomFrame, getTopPaintingHashMap(), getBottomPaintingHashMap()))
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
					
					if(is2BlockPaintingPart(state, eastState, clickedFrame, eastFrame, getRightPaintingMap(), getLeftPaintingMap()))
					{
						eastFrame.getInventory().clear(); //Empty the east frame
					}
				}
				//Harvest from east to west
				if(westTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					
					if(is2BlockPaintingPart(state, westState, clickedFrame, westFrame, getLeftPaintingMap(), getRightPaintingMap()))
					{
						westFrame.getInventory().clear(); //Empty the west frame
					}
				}
				//Harvest from south to north
				if(northTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity northFrame = (VeFrameTileEntity) northTileEntity;
					
					if(is2BlockPaintingPart(state, northState, clickedFrame, northFrame, getRightPaintingMap(), getLeftPaintingMap()))
					{
						northFrame.getInventory().clear(); //Empty the north frame
					}
				}
				//Harvest from north to south
				if(southTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity southFrame = (VeFrameTileEntity) southTileEntity;
					
					if(is2BlockPaintingPart(state, southState, clickedFrame, southFrame, getLeftPaintingMap(), getRightPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, bottomState, eastState, bottomEastState, clickedFrame, bottomFrame, eastFrame, bottomEastFrame, getTopRightPaintingMap(), getBottomRightPaintingMap(), getTopLeftPaintingMap(), getBottomLeftPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, topState, eastState, topEastState, clickedFrame, topFrame, eastFrame, topEastFrame, getBottomRightPaintingMap(), getTopRightPaintingMap(), getBottomLeftPaintingMap(), getTopLeftPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, topState, westState, topWestState, clickedFrame, topFrame, westFrame, topWestFrame, getBottomLeftPaintingMap(), getTopLeftPaintingMap(), getBottomRightPaintingMap(), getTopRightPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, bottomState, westState, bottomWestState, clickedFrame, bottomFrame, westFrame, bottomWestFrame, getTopLeftPaintingMap(), getBottomLeftPaintingMap(), getTopRightPaintingMap(), getBottomRightPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, bottomState, southState, bottomSouthState, clickedFrame, bottomFrame, southFrame, bottomSouthFrame, getTopLeftPaintingMap(), getBottomLeftPaintingMap(), getTopRightPaintingMap(), getBottomRightPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, topState, southState, topSouthState, clickedFrame, topFrame, southFrame, topSouthFrame, getBottomLeftPaintingMap(), getTopLeftPaintingMap(), getBottomRightPaintingMap(), getTopRightPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, topState, northState, topNorthState, clickedFrame, topFrame, northFrame, topNorthFrame, getBottomRightPaintingMap(), getTopRightPaintingMap(), getBottomLeftPaintingMap(), getTopLeftPaintingMap()))
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
					
					if(is4BlockPaintingPart(state, bottomState, northState, bottomNorthState, clickedFrame, bottomFrame, northFrame, bottomNorthFrame, getTopRightPaintingMap(), getBottomRightPaintingMap(), getTopLeftPaintingMap(), getBottomLeftPaintingMap()))
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
				spawnAsEntity(world, pos, new ItemStack(fourPaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
		}
		super.onBlockHarvested(world, pos, state, player);
	}
	
	/**
	 * A helper method that returns true if both blocks make up the painting harvested.
	 */
	public static boolean is2BlockPaintingPart(BlockState clickedState, BlockState state2, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, Map<Item, Item> map, Map<Item, Item> map2)
	{
		return state2.getBlock() == clickedState.getBlock()	  			 	   &&
			   matchesFacing(clickedState, state2) 		 				 	   &&
			   !isEmpty(clickedFrame)			  	  					 	   &&
			   !isEmpty(frame2)					  	  					 	   &&
			   map.containsValue(clickedFrame.getInventory().get(0).getItem()) &&
			   map2.containsValue(frame2.getInventory().get(0).getItem());
	}
	
	/**
	 * A helper method that returns true if all 4 blocks make up the painting harvested.
	 */
	public static boolean is4BlockPaintingPart(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4, Map<Item, Item> map, Map<Item, Item> map2, Map<Item, Item> map3, Map<Item, Item> map4)
	{
		return is2BlockPaintingPart(clickedState, state2, clickedFrame, frame2, map, map2) &&
			   state3.getBlock() == clickedState.getBlock()								   &&
			   state4.getBlock() == clickedState.getBlock()								   &&
			   !isEmpty(frame3)														   	   &&
			   !isEmpty(frame4)															   &&
			   map3.containsValue(frame3.getInventory().get(0).getItem())				   &&
			   map4.containsValue(frame4.getInventory().get(0).getItem());
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
