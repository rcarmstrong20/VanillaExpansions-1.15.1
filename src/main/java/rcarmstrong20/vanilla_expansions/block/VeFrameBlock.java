package rcarmstrong20.vanilla_expansions.block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.exception.ExceptionUtils;

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
		
		BlockPos topPos = pos.up();
		BlockPos bottomPos = pos.down();
		
		BlockPos eastPos = pos.east();
		BlockPos westPos = pos.west();
		BlockPos northPos = pos.north();
		BlockPos southPos = pos.south();
		
		BlockPos topEastPos = topPos.east();
		BlockPos topWestPos = topPos.west();
		BlockPos topNorthPos = topPos.north();
		BlockPos topSouthPos = topPos.south();
		
		BlockPos bottomEastPos = bottomPos.east();
		BlockPos bottomWestPos = bottomPos.west();
		BlockPos bottomNorthPos = bottomPos.north();
		BlockPos bottomSouthPos = bottomPos.south();
		
		
		
		/*
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
		
		TileEntity east2TileEntity = world.getTileEntity(pos.east(2));
		TileEntity east3TileEntity = world.getTileEntity(pos.east(3));
		TileEntity west2TileEntity = world.getTileEntity(pos.west(2));
		TileEntity west3TileEntity = world.getTileEntity(pos.west(3));
		
		TileEntity bottomEast2TileEntity = world.getTileEntity(pos.down().east(2));
		TileEntity bottomEast3TileEntity = world.getTileEntity(pos.down().east(3));
		TileEntity bottomWest2TileEntity = world.getTileEntity(pos.down().west(2));
		TileEntity bottomWest3TileEntity = world.getTileEntity(pos.down().west(3));
		
		TileEntity topEast2TileEntity = world.getTileEntity(pos.up().east(2));
		TileEntity topEast3TileEntity = world.getTileEntity(pos.up().east(3));
		TileEntity topWest2TileEntity = world.getTileEntity(pos.up().west(2));
		TileEntity topWest3TileEntity = world.getTileEntity(pos.up().west(3));
		
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
		
		BlockState east2State = world.getBlockState(pos.east(2));
		BlockState east3State = world.getBlockState(pos.east(3));
		BlockState west2State = world.getBlockState(pos.west(2));
		BlockState west3State = world.getBlockState(pos.west(3));
		
		BlockState bottomEast2State = world.getBlockState(pos.down().east(2));
		BlockState bottomEast3State = world.getBlockState(pos.down().east(3));
		BlockState bottomWest2State = world.getBlockState(pos.down().west(2));
		BlockState bottomWest3State = world.getBlockState(pos.down().west(3));
		
		BlockState topEast2State = world.getBlockState(pos.up().east(2));
		BlockState topEast3State = world.getBlockState(pos.up().east(3));
		BlockState topWest2State = world.getBlockState(pos.up().west(2));
		BlockState topWest3State = world.getBlockState(pos.up().west(3));
		*/
		
		if(!world.isRemote)
		{
			//Two block tall paintings start.
			if(VeTwoBlockPainting.getTopPaintingMap().containsKey(heldItem.getItem()) ||
			   VeTwoBlockPainting.getBottomPaintingMap().containsKey(heldItem.getItem()))
			{
				//Place from bottom.
				if(VeTwoBlockPainting.addPaintingParts(world,
						   pos,
						   topPos,
						   VeTwoBlockPainting.getBottomPaintingMap(),
						   VeTwoBlockPainting.getTopPaintingMap(),
						   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from top.
				if(VeTwoBlockPainting.addPaintingParts(world,
						   pos,
						   bottomPos,
						   VeTwoBlockPainting.getTopPaintingMap(),
						   VeTwoBlockPainting.getBottomPaintingMap(),
						   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
			}
			//Two block long paintings start.
			else if(VeTwoBlockPainting.getRightPaintingMap().containsKey(heldItem.getItem()) ||
					VeTwoBlockPainting.getLeftPaintingMap().containsKey(heldItem.getItem()))
			{
				//Place from facing=south right or facing=north left.
				if(VeTwoBlockPainting.addPaintingParts(world,
													   pos,
													   westPos,
													   VeTwoBlockPainting.getLeftPaintingMap(),
													   VeTwoBlockPainting.getRightPaintingMap(),
													   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				
				//Place from facing=south left or facing=north right.
				if(VeTwoBlockPainting.addPaintingParts(world,
													   pos,
													   eastPos,
													   VeTwoBlockPainting.getRightPaintingMap(),
													   VeTwoBlockPainting.getLeftPaintingMap(),
													   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east left or facing=west right.
				if(VeTwoBlockPainting.addPaintingParts(world,
													   pos,
													   northPos,
													   VeTwoBlockPainting.getLeftPaintingMap(),
													   VeTwoBlockPainting.getRightPaintingMap(),
													   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east right or facing=west left.
				if(VeTwoBlockPainting.addPaintingParts(world,
													   pos,
													   southPos,
													   VeTwoBlockPainting.getRightPaintingMap(),
													   VeTwoBlockPainting.getLeftPaintingMap(),
													   heldItem))
				{
					return ActionResultType.SUCCESS;
				}
			}
			//Four block paintings start.
			else if(VeFourBlockPainting.getBottomRightPaintingMap().containsKey(heldItem.getItem()) ||
					VeFourBlockPainting.getBottomLeftPaintingMap().containsKey(heldItem.getItem())  ||
					VeFourBlockPainting.getTopRightPaintingMap().containsKey(heldItem.getItem())    ||
					VeFourBlockPainting.getTopLeftPaintingMap().containsKey(heldItem.getItem()))
			{
				//Place from facing=south top left or facing=north top right.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														bottomPos,
														eastPos,
														bottomEastPos,
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=south bottom left or facing=north bottom right.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														topPos,
														eastPos,
														topEastPos,
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=south top right or facing=north or facing=north top left.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														bottomPos,
														westPos,
														bottomWestPos,
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=south bottom right or facing=north bottom left.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														topPos,
														westPos,
														topWestPos,
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east top left or facing=west top right.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														bottomPos,
														northPos,
														bottomNorthPos,
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east bottom left or facing=west bottom right.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														topPos,
														northPos,
														topNorthPos,
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east top right or facing=west top left.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														bottomPos,
														southPos,
														bottomSouthPos,
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
				//Place from facing=east bottom right or facing=west bottom left.
				if(VeFourBlockPainting.addPaintingParts(world,
														pos,
														topPos,
														southPos,
														topSouthPos,
														VeFourBlockPainting.getBottomRightPaintingMap(),
														VeFourBlockPainting.getTopRightPaintingMap(),
														VeFourBlockPainting.getBottomLeftPaintingMap(),
														VeFourBlockPainting.getTopLeftPaintingMap(),
														heldItem))
				{
					return ActionResultType.SUCCESS;
				}
			}
			
			/*
			else if(VeItemTags.PAINTINGS.contains(heldItem.getItem()) && isEmpty(clickedFrame))
			{
				clickedFrame.addItem(heldItem);
				world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
				return ActionResultType.CONSUME;
			}
			*/
		}
		return ActionResultType.FAIL;
	}
		/*
		if(!world.isRemote && tileEntity instanceof VeFrameTileEntity)
		{
			VeFrameTileEntity clickedFrame = (VeFrameTileEntity) tileEntity;
			
			
			/*
			//Eight block paintings start.
			else if(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().containsKey(heldItem.getItem())  ||
					VeEightBlockPainting.getBottomLeftPaintingMap().containsKey(heldItem.getItem()) 	   ||
					VeEightBlockPainting.getBottomRightMiddlePaintingMap().containsKey(heldItem.getItem()) ||
					VeEightBlockPainting.getBottomRightPaintingMap().containsKey(heldItem.getItem()) 	   ||
					VeEightBlockPainting.getTopLeftMiddlePaintingMap().containsKey(heldItem.getItem()) 	   ||
					VeEightBlockPainting.getTopLeftPaintingMap().containsKey(heldItem.getItem())           ||
					VeEightBlockPainting.getTopRightMiddlePaintingMap().containsKey(heldItem.getItem())    ||
					VeEightBlockPainting.getTopRightPaintingMap().containsKey(heldItem.getItem()))
			{
				//Place the painting from top west to bottom east.
				if(bottomTileEntity instanceof VeFrameTileEntity      &&
				   eastTileEntity instanceof VeFrameTileEntity        &&
				   east2TileEntity instanceof VeFrameTileEntity       &&
				   east3TileEntity instanceof VeFrameTileEntity       &&
				   bottomEastTileEntity instanceof VeFrameTileEntity  &&
				   bottomEast2TileEntity instanceof VeFrameTileEntity &&
				   bottomEast3TileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity east2Frame = (VeFrameTileEntity) east2TileEntity;
					VeFrameTileEntity east3Frame = (VeFrameTileEntity) east3TileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					VeFrameTileEntity bottomEast2Frame = (VeFrameTileEntity) bottomEast2TileEntity;
					VeFrameTileEntity bottomEast3Frame = (VeFrameTileEntity) bottomEast3TileEntity;
					
					if(VeEightBlockPainting.frameFitsPainting(state,
							   				   				  bottomState,
							   				   				  eastState,
							   				   				  east2State,
							   				   				  east3State,
							   				   				  bottomEastState,
							   				   				  bottomEast2State,
							   				   				  bottomEast3State,
							   				   				  clickedFrame,
							   				   				  bottomFrame,
							   				   				  eastFrame,
							   				   				  east2Frame,
							   				   				  east3Frame,
							   				   				  bottomEastFrame,
							   				   				  bottomEast2Frame,
							   				   				  bottomEast3Frame))
					{
						if(clickedFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east2Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east3Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomEast2Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomEast3Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				//Place the painting from bottom west to top east.
				if(topTileEntity instanceof VeFrameTileEntity      &&
				   eastTileEntity instanceof VeFrameTileEntity     &&
				   east2TileEntity instanceof VeFrameTileEntity    &&
				   east3TileEntity instanceof VeFrameTileEntity    &&
				   topEastTileEntity instanceof VeFrameTileEntity  &&
				   topEast2TileEntity instanceof VeFrameTileEntity &&
				   topEast3TileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity east2Frame = (VeFrameTileEntity) east2TileEntity;
					VeFrameTileEntity east3Frame = (VeFrameTileEntity) east3TileEntity;
					VeFrameTileEntity topEastFrame = (VeFrameTileEntity) topEastTileEntity;
					VeFrameTileEntity topEast2Frame = (VeFrameTileEntity) topEast2TileEntity;
					VeFrameTileEntity topEast3Frame = (VeFrameTileEntity) topEast3TileEntity;
					
					if(VeEightBlockPainting.frameFitsPainting(state,
							   				   				  topState,
							   				   				  eastState,
							   				   				  east2State,
							   				   				  east3State,
							   				   				  topEastState,
							   				   				  topEast2State,
							   				   				  topEast3State,
							   				   				  clickedFrame,
							   				   				  topFrame,
							   				   				  eastFrame,
							   				   				  east2Frame,
							   				   				  east3Frame,
							   				   				  topEastFrame,
							   				   				  topEast2Frame,
							   				   				  topEast3Frame))
					{
						if(clickedFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east2Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east3Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topEastFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   topEast2Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   topEast3Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				//Place the painting from top west right middle.
				if(bottomTileEntity instanceof VeFrameTileEntity     &&
				   westTileEntity instanceof VeFrameTileEntity   	 &&
				   eastTileEntity instanceof VeFrameTileEntity       &&
				   east2TileEntity instanceof VeFrameTileEntity      &&
				   bottomWestTileEntity instanceof VeFrameTileEntity &&
				   bottomEastTileEntity instanceof VeFrameTileEntity &&
				   bottomEast2TileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity east2Frame = (VeFrameTileEntity) east2TileEntity;
					VeFrameTileEntity bottomWestFrame = (VeFrameTileEntity) bottomWestTileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					VeFrameTileEntity bottomEast2Frame = (VeFrameTileEntity) bottomEast2TileEntity;
					
					if(VeEightBlockPainting.frameFitsPainting(state,
							   				   				  bottomState,
							   				   				  westState,
							   				   				  eastState,
							   				   				  east2State,
							   				   				  bottomWestState,
							   				   				  bottomEastState,
							   				   				  bottomEast2State,
							   				   				  clickedFrame,
							   				   				  bottomFrame,
							   				   				  westFrame,
							   				   				  eastFrame,
							   				   				  east2Frame,
							   				   				  bottomWestFrame,
							   				   				  bottomEastFrame,
							   				   				  bottomEast2Frame))
					{
						if(clickedFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east2Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomWestFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomEast2Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from bottom west right middle. Fix
				if(topTileEntity instanceof VeFrameTileEntity     &&
				   westTileEntity instanceof VeFrameTileEntity    &&
				   eastTileEntity instanceof VeFrameTileEntity    &&
				   east2TileEntity instanceof VeFrameTileEntity   &&
				   topWestTileEntity instanceof VeFrameTileEntity &&
				   topEastTileEntity instanceof VeFrameTileEntity &&
				   topEast2TileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity topFrame = (VeFrameTileEntity) topTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity east2Frame = (VeFrameTileEntity) west2TileEntity;
					VeFrameTileEntity topWestFrame = (VeFrameTileEntity) topWestTileEntity;
					VeFrameTileEntity topEastFrame = (VeFrameTileEntity) topEastTileEntity;
					VeFrameTileEntity topEast2Frame = (VeFrameTileEntity) topEast2TileEntity;
					
					if(VeEightBlockPainting.frameFitsPainting(state,
							   				   				  topState,
							   				   				  westState,
							   				   				  eastState,
							   				   				  east2State,
							   				   				  topWestState,
							   				   				  topEastState,
							   				   				  topEast2State,
							   				   				  clickedFrame,
							   				   				  topFrame,
							   				   				  westFrame,
							   				   				  eastFrame,
							   				   				  east2Frame,
							   				   				  topWestFrame,
							   				   				  topEastFrame,
							   				   				  topEast2Frame))
					{
						if(clickedFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   topFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   east2Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   topWestFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   topEastFrame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   topEast2Frame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))))
						{
							world.playSound(null, pos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
							heldItem.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				
				//Place the painting from top west left middle.
				if(bottomTileEntity instanceof VeFrameTileEntity     &&
				   eastTileEntity instanceof VeFrameTileEntity       &&
				   westTileEntity instanceof VeFrameTileEntity       &&
				   west2TileEntity instanceof VeFrameTileEntity      &&
				   bottomEastTileEntity instanceof VeFrameTileEntity &&
				   bottomWestTileEntity instanceof VeFrameTileEntity &&
				   bottomWest2TileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity bottomFrame = (VeFrameTileEntity) bottomTileEntity;
					VeFrameTileEntity eastFrame = (VeFrameTileEntity) eastTileEntity;
					VeFrameTileEntity westFrame = (VeFrameTileEntity) westTileEntity;
					VeFrameTileEntity west2Frame = (VeFrameTileEntity) west2TileEntity;
					VeFrameTileEntity bottomEastFrame = (VeFrameTileEntity) bottomEastTileEntity;
					VeFrameTileEntity bottomWestFrame = (VeFrameTileEntity) bottomWestTileEntity;
					VeFrameTileEntity bottomWest2Frame = (VeFrameTileEntity) bottomWest2TileEntity;
					
					if(VeEightBlockPainting.frameFitsPainting(state,
							   				   				  bottomState,
							   				   				  eastState,
							   				   				  westState,
							   				   				  west2State,
							   				   				  bottomEastState,
							   				   				  bottomWestState,
							   				   				  bottomWest2State,
							   				   				  clickedFrame,
							   				   				  bottomFrame,
							   				   				  eastFrame,
							   				   				  westFrame,
							   				   				  west2Frame,
							   				   				  bottomEastFrame,
							   				   				  bottomWestFrame,
							   				   				  bottomWest2Frame))
					{
						if(clickedFrame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftMiddlePaintingMap().get(heldItem.getItem()))) &&
						   eastFrame.addItem(new ItemStack(VeEightBlockPainting.getTopLeftPaintingMap().get(heldItem.getItem()))) &&
						   westFrame.addItem(new ItemStack(VeEightBlockPainting.getTopRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   west2Frame.addItem(new ItemStack(VeEightBlockPainting.getTopRightPaintingMap().get(heldItem.getItem()))) &&
						   bottomEastFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomLeftPaintingMap().get(heldItem.getItem()))) &&
						   bottomWestFrame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightMiddlePaintingMap().get(heldItem.getItem()))) &&
						   bottomWest2Frame.addItem(new ItemStack(VeEightBlockPainting.getBottomRightPaintingMap().get(heldItem.getItem()))))
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
	*/
	
	/**
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
	
	private static void playPlacePaintingEvent(World world, BlockPos clickedPos, ItemStack itemStack)
	{
		world.playSound(null, clickedPos, SoundEvents.ENTITY_PAINTING_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
		itemStack.shrink(1);
	}
	
	/*
	 * Borrowed from LadderBlock
	 */
	private boolean canAttachTo(IBlockReader blockReader, BlockPos pos, Direction direction)
	{
		BlockState blockstate = blockReader.getBlockState(pos);
		return !blockstate.canProvidePower() && blockstate.isSolidSide(blockReader, pos, direction);
	}
	
	/*
	 * Borrowed from LadderBlock
	 */
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = state.get(FACING);
		return this.canAttachTo(worldIn, pos.offset(direction.getOpposite()), direction);
	}
	
	/*
	 * Borrowed from LadderBlock
	 */
	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		if (!context.replacingClickedOnBlock())
		{
			BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(context.getFace().getOpposite()));
			if (blockstate.getBlock() == this && blockstate.get(FACING) == context.getFace())
			{
				return null;
			}
		}
		
		BlockState blockstate1 = this.getDefaultState();
		IWorldReader iworldreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		
		for(Direction direction : context.getNearestLookingDirections())
		{
			if (direction.getAxis().isHorizontal())
			{
				blockstate1 = blockstate1.with(FACING, direction.getOpposite());
				if (blockstate1.isValidPosition(iworldreader, blockpos))
				{
					return blockstate1.with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
				}
			}
		}
		return null;
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
		BlockPos topPos = pos.up();
		BlockPos bottomPos = pos.down();
		
		BlockState topState = world.getBlockState(topPos);
		BlockState bottomState = world.getBlockState(bottomPos);
		
		BlockPos eastPos = pos.east();
		BlockPos westPos = pos.west();
		BlockPos northPos = pos.north();
		BlockPos southPos = pos.south();
		
		BlockPos topEastPos = topPos.east();
		BlockPos topWestPos = topPos.west();
		BlockPos topNorthPos = topPos.north();
		BlockPos topSouthPos = topPos.south();
		
		BlockPos bottomEastPos = bottomPos.east();
		BlockPos bottomWestPos = bottomPos.west();
		BlockPos bottomNorthPos = bottomPos.north();
		BlockPos bottomSouthPos = bottomPos.south();
		
		
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
		
		TileEntity east2TileEntity = world.getTileEntity(pos.east(2));
		TileEntity east3TileEntity = world.getTileEntity(pos.east(3));
		
		BlockState bottomEast2State = world.getBlockState(pos.down().east(2));
		BlockState bottomEast3State = world.getBlockState(pos.down().east(3));
		
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
				List<Map<Item, Item>> topMapList = Arrays.asList(VeTwoBlockPainting.getTopPaintingMap(), VeTwoBlockPainting.getBottomPaintingMap());
				List<Map<Item, Item>> bottomMapList = Arrays.asList(VeTwoBlockPainting.getBottomPaintingMap(), VeTwoBlockPainting.getTopPaintingMap());
				
				//Harvest from bottom.
				harvestPainting(world,
								Arrays.asList(pos,
											  topPos),
								bottomMapList);
				
				//Harvest from top.
				harvestPainting(world,
								Arrays.asList(pos,
											  bottomPos),
								topMapList);
				
				spawnAsEntity(world, pos, new ItemStack(tallPaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(sidePaintingMap.containsKey(inventoryItem))
			{
				//Harvest from x-axis right.
				harvestPainting(world,
								Arrays.asList(pos,
											  eastPos),
								Arrays.asList(VeTwoBlockPainting.getRightPaintingMap(),
											  VeTwoBlockPainting.getLeftPaintingMap()));
				
				//Harvest from x-axis left.
				harvestPainting(world,
								Arrays.asList(pos,
											  westPos),
								Arrays.asList(VeTwoBlockPainting.getLeftPaintingMap(),
											  VeTwoBlockPainting.getRightPaintingMap()));
				
				//Harvest from z-axis right.
				harvestPainting(world,
								Arrays.asList(pos,
											  southPos),
								Arrays.asList(VeTwoBlockPainting.getRightPaintingMap(),
											  VeTwoBlockPainting.getLeftPaintingMap()));
				
				//Harvest from z-axis left.
				harvestPainting(world,
								Arrays.asList(pos,
											  northPos),
								Arrays.asList(VeTwoBlockPainting.getLeftPaintingMap(),
											  VeTwoBlockPainting.getRightPaintingMap()));
				
				spawnAsEntity(world, pos, new ItemStack(sidePaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(fourPaintingMap.containsKey(inventoryItem))
			{
				//Harvest from x-axis top right.
				harvestPainting(world,
								Arrays.asList(pos,
											  bottomPos,
											  eastPos,
											  bottomEastPos),
								Arrays.asList(VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap()));
				
				//Harvest from x-axis bottom right.
				harvestPainting(world,
								Arrays.asList(pos,
											  topPos,
											  eastPos,
											  topEastPos),
								Arrays.asList(VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap()));
				
				//Harvest from x-axis bottom left.
				harvestPainting(world,
								Arrays.asList(pos,
											  topPos,
											  westPos,
											  topWestPos),
								Arrays.asList(VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap()));
				
				//Harvest from x-axis top left.
				harvestPainting(world,
								Arrays.asList(pos,
											  bottomPos,
											  westPos,
											  bottomWestPos),
								Arrays.asList(VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap()));
				
				//Harvest from z-axis top right.
				harvestPainting(world,
								Arrays.asList(pos,
											  bottomPos,
											  southPos,
											  bottomSouthPos),
								Arrays.asList(VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap()));
				
				//Harvest from z-axis bottom right.
				harvestPainting(world,
								Arrays.asList(pos,
											  topPos,
											  southPos,
											  topSouthPos),
								Arrays.asList(VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap()));
				
				//Harvest from z-axis top left.
				harvestPainting(world,
								Arrays.asList(pos,
											  bottomPos,
											  northPos,
											  bottomNorthPos),
								Arrays.asList(VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap()));
				
				//Harvest from z-axis bottom left.
				harvestPainting(world,
								Arrays.asList(pos,
											  topPos,
											  northPos,
											  topNorthPos),
								Arrays.asList(VeFourBlockPainting.getBottomLeftPaintingMap(),
											  VeFourBlockPainting.getTopLeftPaintingMap(),
											  VeFourBlockPainting.getBottomRightPaintingMap(),
											  VeFourBlockPainting.getTopRightPaintingMap()));
				
				spawnAsEntity(world, pos, new ItemStack(fourPaintingMap.get(inventoryItem))); //Spawn the drops in the world.
			}
			else if(VeItemTags.PAINTINGS.contains(inventoryItem) && !isEmpty(clickedFrame))
			{
				spawnAsEntity(world, pos, new ItemStack(inventoryItem)); //Spawn the drops in the world.
			}
		}
		super.onBlockHarvested(world, pos, state, player);
	}
	
	private void harvestPainting(World world, List<BlockPos> framePositions, List<Map<Item, Item>> paintingParts)
	{
		if(this.isPaintingPart(world, framePositions, paintingParts))
		{
			this.clearFrames(world, framePositions);
		}
	}
	
	/**
	 * A helper method that returns true if both blocks make up the painting harvested. It returns an exception if both lists are different sizes.
	 */
	private boolean isPaintingPart(World world, List<BlockPos> posList, List<Map<Item, Item>> paintingPartsMap)
	{
		if(posList.size() == paintingPartsMap.size())
		{
			BlockState state = world.getBlockState(posList.get(0));
			
			for(int i = 0; i < posList.size(); i++)
			{
				BlockState currentState = world.getBlockState(posList.get(i));
				TileEntity currentTileEntity = world.getTileEntity(posList.get(i));
				Map<Item, Item> currentMap = paintingPartsMap.get(i);
				
				if(currentTileEntity instanceof VeFrameTileEntity)
				{
					VeFrameTileEntity currentFrame = (VeFrameTileEntity) currentTileEntity;
					
					if(state.getBlock() == currentState.getBlock()	  			 		      &&
					   matchesFacing(state, currentState) 		 				 	  		  &&
					   !isEmpty(currentFrame)			  	  					 	  		  &&
					   currentMap.containsValue(currentFrame.getInventory().get(0).getItem()))
					{
						return true;
					}
				}
			}
			return false;
		}
		else
		{
			return ExceptionUtils.wrapAndThrow(new Throwable("The two lists are different sizes!!!"));
		}
	}
	
	private void clearFrames(World world, List<BlockPos> posList)
	{
		for(int i = 0; i < posList.size(); i++)
		{
			TileEntity currentTileEntity = world.getTileEntity(posList.get(i));
			
			if(currentTileEntity instanceof VeFrameTileEntity)
			{
				VeFrameTileEntity currentFrame = (VeFrameTileEntity) currentTileEntity;
				
				currentFrame.getInventory().clear();
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
		 * A helper method that returns true if both blocks can support the given painting.
		 */
		private static boolean framesFitPainting(BlockState clickedState, BlockState state2, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2)
		{
			return state2.getBlock() == clickedState.getBlock() &&
				   matchesFacing(clickedState, state2) 			&&
				   isEmpty(clickedFrame)              			&&
				   isEmpty(frame2);
		}
		
		/**
		 * A helper method that places the painting pieces in the frames if there are available frames in the two positions and returns true otherwise it will return false.
		 */
		private static boolean addPaintingParts(World world, BlockPos clickedPos, BlockPos pos2, Map<Item, Item> clickedMap, Map<Item, Item> map2, ItemStack item)
		{
			BlockState clickedState = world.getBlockState(clickedPos);
			BlockState state2 = world.getBlockState(pos2);
			
			TileEntity clickedTileEntity = world.getTileEntity(clickedPos);
			TileEntity tileEntity2 = world.getTileEntity(pos2);
			
			if(clickedTileEntity instanceof VeFrameTileEntity &&
			   tileEntity2 instanceof VeFrameTileEntity)
			{
				VeFrameTileEntity clickedFrame = (VeFrameTileEntity) clickedTileEntity;
				VeFrameTileEntity frame2 = (VeFrameTileEntity) tileEntity2;
				
				if(framesFitPainting(clickedState, state2, clickedFrame, frame2))
				{
					if(clickedFrame.addItem(new ItemStack(clickedMap.get(item.getItem()))) &&
					   frame2.addItem(new ItemStack(map2.get(item.getItem()))))
					{
						playPlacePaintingEvent(world, clickedPos, item);
						return true;
					}
				}
			}
			return false;
		}
		
		
					/*
					for(int i2 = 0; i2 > paintingPartsList.size(); i2++)
					{
						Map<Item, Item> currentMap = paintingPartsList.get(i2);
						TileEntity currentTileEntity2 = world.getTileEntity(posList.get(i2));
						
						if(currentTileEntity2 instanceof VeFrameTileEntity)
						{
							VeFrameTileEntity currentFrame2 = (VeFrameTileEntity) currentTileEntity2;
							
							if(!currentMap.containsValue(currentFrame2.getInventory().get(0).getItem()))
							{
								return false;
							}
						}
					}
					*/
			
			/*
			return state2.getBlock() == clickedState.getBlock()	  			 		      &&
				   matchesFacing(clickedState, state2) 		 				 	  		  &&
				   !isEmpty(clickedFrame)			  	  					 	  		  &&
				   !isEmpty(frame2)					  	  					 	   		  &&
				   clickedMap.containsValue(clickedFrame.getInventory().get(0).getItem()) &&
				   map2.containsValue(frame2.getInventory().get(0).getItem());
				   */
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
		/*
		private static boolean framesFitPainting(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4)
		{
			if(tileEntity3 instanceof VeFrameTileEntity &&
			   tileEntity4 instanceof VeFrameTileEntity)
			{
				
				
				return VeTwoBlockPainting.framesFitPainting(clickedState, state2, clickedFrame, frame2) &&
					   clickedState.getBlock() == state3.getBlock()  									&&
					   clickedState.getBlock() == state4.getBlock() 									&&
					   matchesFacing(clickedState, state3) 												&&
					   matchesFacing(clickedState, state4) 									 			&&
					   isEmpty(frame3)	 							 									&&
					   isEmpty(frame4);
			}
		}
		*/
		
		private static boolean addPaintingParts(World world, BlockPos clickedPos, BlockPos pos2, BlockPos pos3, BlockPos pos4, Map<Item, Item> clickedMap, Map<Item, Item> map2, Map<Item, Item> map3, Map<Item, Item> map4, ItemStack item)
		{
			BlockState clickedState = world.getBlockState(clickedPos);
			BlockState state2 = world.getBlockState(pos2);
			BlockState state3 = world.getBlockState(pos3);
			BlockState state4 = world.getBlockState(pos4);
			
			TileEntity clickedTileEntity = world.getTileEntity(clickedPos);
			TileEntity tileEntity2 = world.getTileEntity(pos2);
			TileEntity tileEntity3 = world.getTileEntity(pos3);
			TileEntity tileEntity4 = world.getTileEntity(pos4);
			
			//if()
			//{
				VeFrameTileEntity frame3 = (VeFrameTileEntity) tileEntity3;
				VeFrameTileEntity frame4 = (VeFrameTileEntity) tileEntity4;
				
				if(clickedState.getBlock() == state3.getBlock() &&
				   clickedState.getBlock() == state4.getBlock() &&
				   matchesFacing(clickedState, state3) 			&&
				   matchesFacing(clickedState, state4) 			&&
				   isEmpty(frame3)	 							&&
				   isEmpty(frame4))
				{
					if(frame3.addItem(new ItemStack(map3.get(item.getItem()))) &&
					   frame4.addItem(new ItemStack(map4.get(item.getItem()))) &&
					   VeTwoBlockPainting.addPaintingParts(world, clickedPos, pos2, clickedMap, map2, item))
					{
						return true;
					}
				//}
			}
			return false;
		}
		
		/**
		 * A helper method that returns true if all 4 blocks make up the painting harvested.
		 */
		public static boolean isPaintingPart(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4, Map<Item, Item> clickedMap, Map<Item, Item> map2, Map<Item, Item> map3, Map<Item, Item> map4)
		{
			//return VeTwoBlockPainting.isPaintingPart(clickedState, state2, clickedFrame, frame2, clickedMap, map2) &&
			return state3.getBlock() == clickedState.getBlock()								   					   &&
				   state4.getBlock() == clickedState.getBlock()								   					   &&
				   !isEmpty(frame3)														   	 					   &&
				   !isEmpty(frame4)																				   &&
				   map3.containsValue(frame3.getInventory().get(0).getItem())							 		   &&
				   map4.containsValue(frame4.getInventory().get(0).getItem());
		}
		
		private static void harvestPainting(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4, Map<Item, Item> clickedMap, Map<Item, Item> map2, Map<Item, Item> map3, Map<Item, Item> map4)
		{
			if(VeFourBlockPainting.isPaintingPart(clickedState,
												  state2,
												  state3,
												  state4,
												  clickedFrame,
												  frame2,
												  frame3,
												  frame4,
												  clickedMap,
												  map2,
												  map3,
												  map4))
			{
				frame2.getInventory().clear();
				frame3.getInventory().clear();
				frame4.getInventory().clear();
			}
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
			
			bottomRightMiddlePaintingMap.put(VeItems.fighters_painting, VeItems.fighters_painting_bottom_right_middle);
			
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
		
		/**
		 * A helper method that checks if there are 4 available frames that can be filled with the given painting.
		 */
		/*
		private static boolean frameFitsPainting(BlockState clickedState, BlockState state2, BlockState state3, BlockState state4, BlockState state5, BlockState state6, BlockState state7, BlockState state8, VeFrameTileEntity clickedFrame, VeFrameTileEntity frame2, VeFrameTileEntity frame3, VeFrameTileEntity frame4, VeFrameTileEntity frame5, VeFrameTileEntity frame6, VeFrameTileEntity frame7, VeFrameTileEntity frame8)
		{
			return VeFourBlockPainting.frameFitsPainting(clickedState,
														 state2,
														 state3,
														 state4,
														 clickedFrame,
														 frame2,
														 frame3,
														 frame4) &&
				   clickedState.getBlock() == state5.getBlock()  &&
				   clickedState.getBlock() == state6.getBlock()  &&
				   clickedState.getBlock() == state7.getBlock()  &&
				   clickedState.getBlock() == state8.getBlock()  &&
				   matchesFacing(clickedState, state5) 			 &&
				   matchesFacing(clickedState, state6) 			 &&
				   matchesFacing(clickedState, state7) 			 &&
				   matchesFacing(clickedState, state8) 			 &&
				   isEmpty(frame5)	 							 &&
				   isEmpty(frame6)								 &&
				   isEmpty(frame7)								 &&
				   isEmpty(frame8);
		}
		*/
	}
}
