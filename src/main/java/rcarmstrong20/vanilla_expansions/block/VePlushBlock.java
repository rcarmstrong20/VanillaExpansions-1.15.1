package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VePlushBlock extends HorizontalBlock implements IWaterLoggable
{
	public static final VoxelShape NORMAL_CUBE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	//Blaze Bounding Boxes
	
	protected static final VoxelShape BLAZE_HEAD_SHAPE = Block.makeCuboidShape(5.5D, 8.0D, 5.5D, 10.5D, 13.0D, 10.5D);
	protected static final VoxelShape BLAZE_NORTH_WEST_BOTTOM_LEG_SHAPE = Block.makeCuboidShape(4.5D, 1.0D, 4.5D, 5.5D, 7.0D, 5.5D);
	protected static final VoxelShape BLAZE_NORTH_EAST_BOTTOM_LEG_SHAPE = Block.makeCuboidShape(10.5D, 1.0D, 4.5D, 11.5D, 7.0D, 5.5D);
	protected static final VoxelShape BLAZE_SOUTH_WEST_BOTTOM_LEG_SHAPE = Block.makeCuboidShape(4.5D, 1.0D, 10.5D, 5.5D, 7.0D, 11.5D);
	protected static final VoxelShape BLAZE_SOUTH_EAST_BOTTOM_LEG_SHAPE = Block.makeCuboidShape(10.5D, 1.0D, 10.5D, 11.5D, 7.0D, 11.5D);
	protected static final VoxelShape BLAZE_NORTH_BOTTOM_LEGS_SHAPE = VoxelShapes.or(BLAZE_NORTH_WEST_BOTTOM_LEG_SHAPE, BLAZE_NORTH_EAST_BOTTOM_LEG_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_BOTTOM_LEGS_SHAPE = VoxelShapes.or(BLAZE_SOUTH_WEST_BOTTOM_LEG_SHAPE, BLAZE_SOUTH_EAST_BOTTOM_LEG_SHAPE);
	protected static final VoxelShape BLAZE_BOTTOM_LEGS_SHAPE = VoxelShapes.or(BLAZE_NORTH_BOTTOM_LEGS_SHAPE, BLAZE_SOUTH_BOTTOM_LEGS_SHAPE);
	protected static final VoxelShape BLAZE_BOTTOM_LEGS_AND_HEAD_SHAPE = VoxelShapes.or(BLAZE_BOTTOM_LEGS_SHAPE, BLAZE_HEAD_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_TOP_LEG_SHAPE = Block.makeCuboidShape(7.5D, 7.0D, 11.5D, 8.5D, 13.0D, 12.5D);
	protected static final VoxelShape BLAZE_WEST_TOP_LEG_SHAPE = Block.makeCuboidShape(3.5D, 7.0D, 7.5D, 4.5D, 13.0D, 8.5D);
	protected static final VoxelShape BLAZE_EAST_TOP_LEG_SHAPE = Block.makeCuboidShape(11.5D, 7.0D, 7.5D, 12.5D, 13.0D, 8.5D);
	protected static final VoxelShape BLAZE_NORTH_TOP_LEG_SHAPE = Block.makeCuboidShape(7.5D, 7.0D, 3.5D, 8.5D, 13.0D, 4.5D);
	protected static final VoxelShape BLAZE_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 11.0D, 5.0D, 7.5D, 12.0D, 5.5D);
	protected static final VoxelShape BLAZE_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 11.0D, 5.0D, 10.5D, 12.0D, 5.5D);
	protected static final VoxelShape BLAZE_NORTH_EYES_SHAPE = VoxelShapes.or(BLAZE_NORTH_RIGHT_EYE_SHAPE, BLAZE_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape BLAZE_WEST_AND_EAST_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_WEST_TOP_LEG_SHAPE, BLAZE_EAST_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_NORTH_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_WEST_AND_EAST_TOP_LEGS_SHAPE, BLAZE_SOUTH_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_NORTH_LEGS_AND_HEAD_SHAPE = VoxelShapes.or(BLAZE_BOTTOM_LEGS_AND_HEAD_SHAPE, BLAZE_NORTH_TOP_LEGS_SHAPE);
	protected static final VoxelShape BLAZE_NORTH_SHAPE = VoxelShapes.or(BLAZE_NORTH_EYES_SHAPE, BLAZE_NORTH_LEGS_AND_HEAD_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 11.0D, 10.5D, 7.5D, 12.0D, 11.0D);
	protected static final VoxelShape BLAZE_SOUTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 11.0D, 10.5D, 10.5D, 12.0D, 11.0D);
	protected static final VoxelShape BLAZE_SOUTH_EYES_SHAPE = VoxelShapes.or(BLAZE_SOUTH_RIGHT_EYE_SHAPE, BLAZE_SOUTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_WEST_AND_EAST_TOP_LEGS_SHAPE, BLAZE_NORTH_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_LEGS_AND_HEAD_SHAPE = VoxelShapes.or(BLAZE_BOTTOM_LEGS_AND_HEAD_SHAPE, BLAZE_SOUTH_TOP_LEGS_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_SHAPE = VoxelShapes.or(BLAZE_SOUTH_EYES_SHAPE, BLAZE_SOUTH_LEGS_AND_HEAD_SHAPE);
	protected static final VoxelShape BLAZE_WEST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(5.0D, 11.0D, 5.5D, 5.5D, 12.0D, 7.5D);
	protected static final VoxelShape BLAZE_WEST_LEFT_EYE_SHAPE = Block.makeCuboidShape(5.0D, 11.0D, 8.5D, 5.5D, 12.0D, 10.5D);
	protected static final VoxelShape BLAZE_WEST_EYES_SHAPE = VoxelShapes.or(BLAZE_WEST_RIGHT_EYE_SHAPE, BLAZE_WEST_LEFT_EYE_SHAPE);
	protected static final VoxelShape BLAZE_SOUTH_AND_NORTH_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_SOUTH_TOP_LEG_SHAPE, BLAZE_NORTH_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_WEST_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_SOUTH_AND_NORTH_TOP_LEGS_SHAPE, BLAZE_EAST_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_WEST_LEGS_AND_HEAD_SHAPE = VoxelShapes.or(BLAZE_BOTTOM_LEGS_AND_HEAD_SHAPE, BLAZE_WEST_TOP_LEGS_SHAPE);
	protected static final VoxelShape BLAZE_WEST_SHAPE = VoxelShapes.or(BLAZE_WEST_EYES_SHAPE, BLAZE_WEST_LEGS_AND_HEAD_SHAPE);
	protected static final VoxelShape BLAZE_EAST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(10.5D, 11.0D, 5.5D, 11.0D, 12.0D, 7.5D);
	protected static final VoxelShape BLAZE_EAST_LEFT_EYE_SHAPE = Block.makeCuboidShape(10.5D, 11.0D, 8.5D, 11.0D, 12.0D, 10.5D);
	protected static final VoxelShape BLAZE_EAST_EYES_SHAPE = VoxelShapes.or(BLAZE_EAST_RIGHT_EYE_SHAPE, BLAZE_EAST_LEFT_EYE_SHAPE);
	protected static final VoxelShape BLAZE_EAST_TOP_LEGS_SHAPE = VoxelShapes.or(BLAZE_SOUTH_AND_NORTH_TOP_LEGS_SHAPE, BLAZE_WEST_TOP_LEG_SHAPE);
	protected static final VoxelShape BLAZE_EAST_LEGS_AND_HEAD_SHAPE = VoxelShapes.or(BLAZE_BOTTOM_LEGS_AND_HEAD_SHAPE, BLAZE_EAST_TOP_LEGS_SHAPE);
	protected static final VoxelShape BLAZE_EAST_SHAPE = VoxelShapes.or(BLAZE_EAST_EYES_SHAPE, BLAZE_EAST_LEGS_AND_HEAD_SHAPE);
	
	//Creeper Bounding Boxes
	
	protected static final VoxelShape CREEPER_HEAD_SHAPE = Block.makeCuboidShape(4.5D, 9.0D, 4.5D, 11.5D, 16.0D, 11.5D);
	protected static final VoxelShape CREEPER_TORSO_SHAPE = Block.makeCuboidShape(5.5D, 3.0D, 5.5D, 10.5D, 9.0D, 10.5D);
	protected static final VoxelShape CREEPER_HEAD_AND_TORSO_SHAPE = VoxelShapes.or(CREEPER_HEAD_SHAPE, CREEPER_TORSO_SHAPE);
	protected static final VoxelShape CREEPER_Z_FOOT1_SHAPE = Block.makeCuboidShape(5.5D, 0.0D, 2.5D, 10.5D, 3.0D, 5.5D);
	protected static final VoxelShape CREEPER_Z_FOOT2_SHAPE = Block.makeCuboidShape(5.5D, 0.0D, 10.5D, 10.5D, 3.0D, 13.5D);
	protected static final VoxelShape CREEPER_Z_FEET_SHAPE = VoxelShapes.or(CREEPER_Z_FOOT1_SHAPE, CREEPER_Z_FOOT2_SHAPE);
	protected static final VoxelShape CREEPER_Z_BODY_SHAPE = VoxelShapes.or(CREEPER_HEAD_AND_TORSO_SHAPE, CREEPER_Z_FEET_SHAPE);
	protected static final VoxelShape CREEPER_SOUTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 12.5D, 11.5D, 10.5D, 14.5D, 12.0D);
	protected static final VoxelShape CREEPER_SOUTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 12.5D, 11.5D, 7.5D, 14.5D, 12.0D);
	protected static final VoxelShape CREEPER_SOUTH_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(7.5D, 10.5D, 11.5D, 8.5D, 12.5D, 12.0D);
	protected static final VoxelShape CREEPER_SOUTH_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(8.5D, 9.5D, 11.5D, 9.5D, 11.5D, 12.0D);
	protected static final VoxelShape CREEPER_SOUTH_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(6.5D, 9.5D, 11.5D, 7.5D, 11.5D, 12.0D);
	protected static final VoxelShape CREEPER_SOUTH_EYES_SHAPE = VoxelShapes.or(CREEPER_SOUTH_RIGHT_EYE_SHAPE, CREEPER_SOUTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape CREEPER_SOUTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(CREEPER_SOUTH_MIDDLE_MOUTH_SHAPE, CREEPER_SOUTH_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_SOUTH_MOUTH_SHAPE = VoxelShapes.or(CREEPER_SOUTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE, CREEPER_SOUTH_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_SOUTH_FACE_SHAPE = VoxelShapes.or(CREEPER_SOUTH_MOUTH_SHAPE, CREEPER_SOUTH_EYES_SHAPE);
	protected static final VoxelShape CREEPER_SOUTH_SHAPE = VoxelShapes.or(CREEPER_SOUTH_FACE_SHAPE, CREEPER_Z_BODY_SHAPE);
	protected static final VoxelShape CREEPER_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 12.5D, 4.0D, 10.5D, 14.5D, 4.5D);
	protected static final VoxelShape CREEPER_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 12.5D, 4.0D, 7.5D, 14.5D, 4.5D);
	protected static final VoxelShape CREEPER_NORTH_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(7.5D, 10.5D, 4.0D, 8.5D, 12.5D, 4.5D);
	protected static final VoxelShape CREEPER_NORTH_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(8.5D, 9.5D, 4.0D, 9.5D, 11.5D, 4.5D);
	protected static final VoxelShape CREEPER_NORTH_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(6.5D, 9.5D, 4.0D, 7.5D, 11.5D, 4.5D);
	protected static final VoxelShape CREEPER_NORTH_EYES_SHAPE = VoxelShapes.or(CREEPER_NORTH_RIGHT_EYE_SHAPE, CREEPER_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape CREEPER_NORTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(CREEPER_NORTH_MIDDLE_MOUTH_SHAPE, CREEPER_NORTH_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_NORTH_MOUTH_SHAPE = VoxelShapes.or(CREEPER_NORTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE, CREEPER_NORTH_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_NORTH_FACE_SHAPE = VoxelShapes.or(CREEPER_NORTH_MOUTH_SHAPE, CREEPER_NORTH_EYES_SHAPE);
	protected static final VoxelShape CREEPER_NORTH_SHAPE = VoxelShapes.or(CREEPER_NORTH_FACE_SHAPE, CREEPER_Z_BODY_SHAPE);
	protected static final VoxelShape CREEPER_X_FOOT1_SHAPE = Block.makeCuboidShape(2.5D, 0.0D, 5.5D, 5.5D, 3.0D, 10.5D);
	protected static final VoxelShape CREEPER_X_FOOT2_SHAPE = Block.makeCuboidShape(10.5D, 0.0D, 5.5D, 13.5D, 3.0D, 10.5D);
	protected static final VoxelShape CREEPER_X_FEET_SHAPE = VoxelShapes.or(CREEPER_X_FOOT1_SHAPE, CREEPER_X_FOOT2_SHAPE);
	protected static final VoxelShape CREEPER_X_BODY_SHAPE = VoxelShapes.or(CREEPER_HEAD_AND_TORSO_SHAPE, CREEPER_X_FEET_SHAPE);
	protected static final VoxelShape CREEPER_WEST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(4.0D, 12.5D, 8.5D, 4.5D, 14.5D, 10.5D);
	protected static final VoxelShape CREEPER_WEST_LEFT_EYE_SHAPE = Block.makeCuboidShape(4.0D, 12.5D, 5.5D, 4.5D, 14.5D, 7.5D);
	protected static final VoxelShape CREEPER_WEST_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(4.0D, 10.5D, 7.5D, 4.5D, 12.5D, 8.5D);
	protected static final VoxelShape CREEPER_WEST_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(4.0D, 9.5D, 8.5D, 4.5D, 11.5D, 9.5D);
	protected static final VoxelShape CREEPER_WEST_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(4.0D, 9.5D, 6.5D, 4.5D, 11.5D, 7.5D);
	protected static final VoxelShape CREEPER_WEST_EYES_SHAPE = VoxelShapes.or(CREEPER_WEST_RIGHT_EYE_SHAPE, CREEPER_WEST_LEFT_EYE_SHAPE);
	protected static final VoxelShape CREEPER_WEST_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(CREEPER_WEST_MIDDLE_MOUTH_SHAPE, CREEPER_WEST_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_WEST_MOUTH_SHAPE = VoxelShapes.or(CREEPER_WEST_MIDDLE_AND_RIGHT_MOUTH_SHAPE, CREEPER_WEST_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_WEST_FACE_SHAPE = VoxelShapes.or(CREEPER_WEST_MOUTH_SHAPE, CREEPER_WEST_EYES_SHAPE);
	protected static final VoxelShape CREEPER_WEST_SHAPE = VoxelShapes.or(CREEPER_WEST_FACE_SHAPE, CREEPER_X_BODY_SHAPE);
	protected static final VoxelShape CREEPER_EAST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(11.5D, 12.5D, 8.5D, 12.0D, 14.5D, 10.5D);
	protected static final VoxelShape CREEPER_EAST_LEFT_EYE_SHAPE = Block.makeCuboidShape(11.5D, 12.5D, 5.5D, 12.0D, 14.5D, 7.5D);
	protected static final VoxelShape CREEPER_EAST_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(11.5D, 10.5D, 7.5D, 12.0D, 12.5D, 8.5D);
	protected static final VoxelShape CREEPER_EAST_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(11.5D, 9.5D, 8.5D, 12.0D, 11.5D, 9.5D);
	protected static final VoxelShape CREEPER_EAST_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(11.5D, 9.5D, 6.5D, 12.0D, 11.5D, 7.5D);
	protected static final VoxelShape CREEPER_EAST_EYES_SHAPE = VoxelShapes.or(CREEPER_EAST_RIGHT_EYE_SHAPE, CREEPER_EAST_LEFT_EYE_SHAPE);
	protected static final VoxelShape CREEPER_EAST_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(CREEPER_EAST_MIDDLE_MOUTH_SHAPE, CREEPER_EAST_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_EAST_MOUTH_SHAPE = VoxelShapes.or(CREEPER_EAST_MIDDLE_AND_RIGHT_MOUTH_SHAPE, CREEPER_EAST_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape CREEPER_EAST_FACE_SHAPE = VoxelShapes.or(CREEPER_EAST_MOUTH_SHAPE, CREEPER_EAST_EYES_SHAPE);
	protected static final VoxelShape CREEPER_EAST_SHAPE = VoxelShapes.or(CREEPER_EAST_FACE_SHAPE, CREEPER_X_BODY_SHAPE);
	
	//Zombie Bounding Boxes
	
	protected static final VoxelShape ZOMBIE_SOUTH_HEAD_SHAPE = Block.makeCuboidShape(5.0D, 11.0D, 4.0D, 11.0D, 16.0D, 8.5D);
	protected static final VoxelShape ZOMBIE_SOUTH_TORSO_SHAPE = Block.makeCuboidShape(5.5D, 0.0D, 4.5D, 10.5D, 11.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_SOUTH_RIGHT_ARM_SHAPE = Block.makeCuboidShape(10.5D, 9.0D, 5.0D, 12.5D, 11.0D, 12.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_LEFT_ARM_SHAPE = Block.makeCuboidShape(3.5D, 9.0D, 5.0D, 5.5D, 11.0D, 12.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(9.0D, 14.0D, 8.5D, 10.5D, 15.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 14.0D, 8.5D, 7.0D, 15.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 13.0D, 8.5D, 9.0D, 14.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(9.0D, 11.0D, 8.5D, 10.0D, 13.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(6.0D, 12.0D, 8.5D, 7.0D, 13.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_SOUTH_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_RIGHT_ARM_SHAPE, ZOMBIE_SOUTH_LEFT_ARM_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_EYES_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_RIGHT_EYE_SHAPE, ZOMBIE_SOUTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_MIDDLE_MOUTH_SHAPE, ZOMBIE_SOUTH_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE, ZOMBIE_SOUTH_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_FACE_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_EYES_SHAPE, ZOMBIE_SOUTH_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_FACE_AND_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_FACE_SHAPE, ZOMBIE_SOUTH_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_BODY_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_HEAD_SHAPE, ZOMBIE_SOUTH_TORSO_SHAPE);
	protected static final VoxelShape ZOMBIE_SOUTH_SHAPE = VoxelShapes.or(ZOMBIE_SOUTH_BODY_SHAPE, ZOMBIE_SOUTH_FACE_AND_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_HEAD_SHAPE = Block.makeCuboidShape(5.0D, 11.0D, 7.5D, 11.0D, 16.0D, 12.0D);
	protected static final VoxelShape ZOMBIE_NORTH_TORSO_SHAPE = Block.makeCuboidShape(5.5D, 0.0D, 8.5D, 10.5D, 11.0D, 11.5D);
	protected static final VoxelShape ZOMBIE_NORTH_RIGHT_ARM_SHAPE = Block.makeCuboidShape(10.5D, 9.0D, 4.0D, 12.5D, 11.0D, 11.0D);
	protected static final VoxelShape ZOMBIE_NORTH_LEFT_ARM_SHAPE = Block.makeCuboidShape(3.5D, 9.0D, 4.0D, 5.5D, 11.0D, 11.0D);
	protected static final VoxelShape ZOMBIE_NORTH_RIGHT_EYE_SHAPE = Block.makeCuboidShape(9.0D, 14.0D, 7.0D, 10.5D, 15.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_NORTH_LEFT_EYE_SHAPE = Block.makeCuboidShape(5.5D, 14.0D, 7.0D, 7.0D, 15.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_NORTH_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 9.0D, 14.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_NORTH_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(9.0D, 12.0D, 7.0D, 10.0D, 13.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_NORTH_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(6.0D, 11.0D, 7.0D, 7.0D, 13.0D, 7.5D);
	protected static final VoxelShape ZOMBIE_NORTH_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_RIGHT_ARM_SHAPE, ZOMBIE_NORTH_LEFT_ARM_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_EYES_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_RIGHT_EYE_SHAPE, ZOMBIE_NORTH_LEFT_EYE_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_MIDDLE_MOUTH_SHAPE, ZOMBIE_NORTH_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_MIDDLE_AND_RIGHT_MOUTH_SHAPE, ZOMBIE_NORTH_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_FACE_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_EYES_SHAPE, ZOMBIE_NORTH_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_FACE_AND_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_FACE_SHAPE, ZOMBIE_NORTH_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_BODY_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_HEAD_SHAPE, ZOMBIE_NORTH_TORSO_SHAPE);
	protected static final VoxelShape ZOMBIE_NORTH_SHAPE = VoxelShapes.or(ZOMBIE_NORTH_BODY_SHAPE, ZOMBIE_NORTH_FACE_AND_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_HEAD_SHAPE = Block.makeCuboidShape(7.5D, 11.0D, 5.0D, 12.0D, 16.0D, 11.0D);
	protected static final VoxelShape ZOMBIE_WEST_TORSO_SHAPE = Block.makeCuboidShape(8.5D, 0.0D, 5.5D, 11.5D, 11.0D, 10.5D);
	protected static final VoxelShape ZOMBIE_WEST_RIGHT_ARM_SHAPE = Block.makeCuboidShape(4.0D, 9.0D, 10.5D, 11.0D, 11.0D, 12.5D);
	protected static final VoxelShape ZOMBIE_WEST_LEFT_ARM_SHAPE = Block.makeCuboidShape(4.0D, 9.0D, 3.5D, 11.0D, 11.0D, 5.5D);
	protected static final VoxelShape ZOMBIE_WEST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(7.0D, 14.0D, 9.0D, 7.5D, 15.0D, 10.5D);
	protected static final VoxelShape ZOMBIE_WEST_LEFT_EYE_SHAPE = Block.makeCuboidShape(7.0D, 14.0D, 5.5D, 7.5D, 15.0D, 7.0D);
	protected static final VoxelShape ZOMBIE_WEST_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 7.5D, 14.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_WEST_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 11.0D, 9.0D, 7.5D, 13.0D, 10.0D);
	protected static final VoxelShape ZOMBIE_WEST_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(7.0D, 12.0D, 6.0D, 7.5D, 13.0D, 7.0D);
	protected static final VoxelShape ZOMBIE_WEST_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_WEST_RIGHT_ARM_SHAPE, ZOMBIE_WEST_LEFT_ARM_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_EYES_SHAPE = VoxelShapes.or(ZOMBIE_WEST_RIGHT_EYE_SHAPE, ZOMBIE_WEST_LEFT_EYE_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_WEST_MIDDLE_MOUTH_SHAPE, ZOMBIE_WEST_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_WEST_MIDDLE_AND_RIGHT_MOUTH_SHAPE, ZOMBIE_WEST_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_FACE_SHAPE = VoxelShapes.or(ZOMBIE_WEST_EYES_SHAPE, ZOMBIE_WEST_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_FACE_AND_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_WEST_FACE_SHAPE, ZOMBIE_WEST_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_BODY_SHAPE = VoxelShapes.or(ZOMBIE_WEST_HEAD_SHAPE, ZOMBIE_WEST_TORSO_SHAPE);
	protected static final VoxelShape ZOMBIE_WEST_SHAPE = VoxelShapes.or(ZOMBIE_WEST_BODY_SHAPE, ZOMBIE_WEST_FACE_AND_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_HEAD_SHAPE = Block.makeCuboidShape(4.0D, 11.0D, 5.0D, 8.5D, 16.0D, 11.0D);
	protected static final VoxelShape ZOMBIE_EAST_TORSO_SHAPE = Block.makeCuboidShape(4.5D, 0.0D, 5.5D, 7.5D, 11.0D, 10.5D);
	protected static final VoxelShape ZOMBIE_EAST_RIGHT_ARM_SHAPE = Block.makeCuboidShape(5.0D, 9.0D, 10.5D, 12.0D, 11.0D, 12.5D);
	protected static final VoxelShape ZOMBIE_EAST_LEFT_ARM_SHAPE = Block.makeCuboidShape(5.0D, 9.0D, 3.5D, 12.0D, 11.0D, 5.5D);
	protected static final VoxelShape ZOMBIE_EAST_RIGHT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 14.0D, 9.0D, 9.0D, 15.0D, 10.5D);
	protected static final VoxelShape ZOMBIE_EAST_LEFT_EYE_SHAPE = Block.makeCuboidShape(8.5D, 14.0D, 5.5D, 9.0D, 15.0D, 7.0D);
	protected static final VoxelShape ZOMBIE_EAST_MIDDLE_MOUTH_SHAPE = Block.makeCuboidShape(8.5D, 13.0D, 7.0D, 9.0D, 14.0D, 9.0D);
	protected static final VoxelShape ZOMBIE_EAST_RIGHT_MOUTH_SHAPE = Block.makeCuboidShape(8.5D, 12.0D, 9.0D, 9.0D, 13.0D, 10.0D);
	protected static final VoxelShape ZOMBIE_EAST_LEFT_MOUTH_SHAPE = Block.makeCuboidShape(8.5D, 11.0D, 6.0D, 9.0D, 13.0D, 7.0D);
	protected static final VoxelShape ZOMBIE_EAST_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_EAST_RIGHT_ARM_SHAPE, ZOMBIE_EAST_LEFT_ARM_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_EYES_SHAPE = VoxelShapes.or(ZOMBIE_EAST_RIGHT_EYE_SHAPE, ZOMBIE_EAST_LEFT_EYE_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_MIDDLE_AND_RIGHT_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_EAST_MIDDLE_MOUTH_SHAPE, ZOMBIE_EAST_RIGHT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_MOUTH_SHAPE = VoxelShapes.or(ZOMBIE_EAST_MIDDLE_AND_RIGHT_MOUTH_SHAPE, ZOMBIE_EAST_LEFT_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_FACE_SHAPE = VoxelShapes.or(ZOMBIE_EAST_EYES_SHAPE, ZOMBIE_EAST_MOUTH_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_FACE_AND_ARMS_SHAPE = VoxelShapes.or(ZOMBIE_EAST_FACE_SHAPE, ZOMBIE_EAST_ARMS_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_BODY_SHAPE = VoxelShapes.or(ZOMBIE_EAST_HEAD_SHAPE, ZOMBIE_EAST_TORSO_SHAPE);
	protected static final VoxelShape ZOMBIE_EAST_SHAPE = VoxelShapes.or(ZOMBIE_EAST_BODY_SHAPE, ZOMBIE_EAST_FACE_AND_ARMS_SHAPE);
	
	//Sheep Bounding Boxes
	
	protected static final VoxelShape SHEEP_SOUTH_HEAD_SHAPE = Block.makeCuboidShape(5.5D, 9.0D, 10.0D, 10.5D, 14.0D, 15.0D);
	protected static final VoxelShape SHEEP_SOUTH_BODY_SHAPE = Block.makeCuboidShape(4.5D, 4.0D, 4.0D, 11.5D, 11.0D, 13.0D);
	protected static final VoxelShape SHEEP_SOUTH_LEG1_SHAPE = Block.makeCuboidShape(2.5D, 0.0D, 4.0D, 9.5D, 4.0D, 6.0D);
	
	//
	
	protected static final VoxelShape BAT_NORTH_SHAPE = Block.makeCuboidShape(0.1D, 0.0D, 6.0D, 15.0D, 12.0D, 11.0D);
	protected static final VoxelShape BAT_SOUTH_SHAPE = Block.makeCuboidShape(0.1D, 0.0D, 5.0D, 16.0D, 12.0D, 10.0D);
	protected static final VoxelShape BAT_EAST_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 0.1D, 10.0D, 12.0D, 15.0D);
	protected static final VoxelShape BAT_WEST_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 0.1D, 11.0D, 12.0D, 16.0D);
	
	protected static final VoxelShape CHICKEN_NORTH_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 16.0D);
	protected static final VoxelShape CHICKEN_SOUTH_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape CHICKEN_WEST_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
	protected static final VoxelShape CHICKEN_EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
    protected static final VoxelShape SKELETON_NORTH_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 14.0D, 16.0D, 15.0D);
    protected static final VoxelShape SKELETON_SOUTH_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 1.0D, 14.0D, 16.0D, 13.0D);
    protected static final VoxelShape SKELETON_EAST_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 13.0D, 16.0D, 14.0D);
    protected static final VoxelShape SKELETON_WEST_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 15.0D, 16.0D, 14.0D);
	
	protected static final VoxelShape ENDERMAN_NORTH_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 14.0D);
	protected static final VoxelShape ENDERMAN_SOUTH_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 2.0D, 12.0D, 16.0D, 12.0D);
	protected static final VoxelShape ENDERMAN_EAST_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	protected static final VoxelShape ENDERMAN_WEST_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 14.0D, 16.0D, 12.0D);
    
	protected static final VoxelShape HORSE_Z_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
	protected static final VoxelShape HORSE_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
	
	protected static final VoxelShape COW_Z_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 14.0D, 16.0D);
	protected static final VoxelShape COW_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 14.0D, 12.0D);
    
	protected static final VoxelShape ENDERMITE_Z_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 2.0D, 12.0D, 6.0D, 14.0D);
	protected static final VoxelShape ENDERMITE_X_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 4.0D, 14.0D, 6.0D, 12.0D);
	
	protected static final VoxelShape SILVERFISH_Z_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 16.0D);
	protected static final VoxelShape SILVERFISH_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 8.0D, 14.0D);
	
	protected static final VoxelShape OCELOT_Z_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 8.0D, 16.0D);
	protected static final VoxelShape OCELOT_X_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 16.0D, 8.0D, 11.0D);
	
	protected static final VoxelShape SPIDER_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
	protected static final VoxelShape CAVE_SPIDER_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
	protected static final VoxelShape GHAST_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape VILLAGER_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
	
	public VePlushBlock(Block.Properties properties)
	{
		super(properties);
	}
	/*
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	*/
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return facing.getAxis().isHorizontal() ? stateIn.with(HORIZONTAL_FACING, stateIn.get(HORIZONTAL_FACING)) : stateIn;
	}
	
	@Override
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, WATERLOGGED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if(this == VeBlocks.blaze_plush)
		{
			return VePlushBlock.defineShapes(state, BLAZE_SOUTH_SHAPE, BLAZE_NORTH_SHAPE, BLAZE_WEST_SHAPE, BLAZE_EAST_SHAPE);
		}
		else if(this == VeBlocks.creeper_plush)
		{
			return VePlushBlock.defineShapes(state, CREEPER_SOUTH_SHAPE, CREEPER_NORTH_SHAPE, CREEPER_WEST_SHAPE, CREEPER_EAST_SHAPE);
		}
		else if(this == VeBlocks.zombie_plush || this == VeBlocks.zombie_demon_plush)
		{
			return VePlushBlock.defineShapes(state, ZOMBIE_SOUTH_SHAPE, ZOMBIE_NORTH_SHAPE, ZOMBIE_WEST_SHAPE, ZOMBIE_EAST_SHAPE);
		}
		else if(this == VeBlocks.white_sheep_plush || this == VeBlocks.orange_sheep_plush || this == VeBlocks.magenta_sheep_plush || this == VeBlocks.light_blue_sheep_plush || this == VeBlocks.yellow_sheep_plush || this == VeBlocks.lime_sheep_plush || this == VeBlocks.pink_sheep_plush || this == VeBlocks.gray_sheep_plush || this == VeBlocks.light_gray_sheep_plush || this == VeBlocks.cyan_sheep_plush || this == VeBlocks.purple_sheep_plush || this == VeBlocks.blue_sheep_plush || this == VeBlocks.brown_sheep_plush || this == VeBlocks.green_sheep_plush || this == VeBlocks.red_sheep_plush || this == VeBlocks.black_sheep_plush)
		{
			return VePlushBlock.defineShapes(state, HORSE_Z_SHAPE, HORSE_Z_SHAPE, HORSE_X_SHAPE, HORSE_X_SHAPE);
		}
		
		else if(this == VeBlocks.bat_plush)
		{
			return VePlushBlock.defineShapes(state, BAT_SOUTH_SHAPE, BAT_NORTH_SHAPE, BAT_WEST_SHAPE, BAT_EAST_SHAPE);
		}
		else if(this == VeBlocks.zombie_pigman_plush)
		{
			return VePlushBlock.defineShapes(state, ZOMBIE_SOUTH_SHAPE, ZOMBIE_NORTH_SHAPE, ZOMBIE_WEST_SHAPE, ZOMBIE_EAST_SHAPE);
		}
		else if(this == VeBlocks.chicken_plush || this == VeBlocks.rabbit_plush)
		{
			return VePlushBlock.defineShapes(state, CHICKEN_SOUTH_SHAPE, CHICKEN_NORTH_SHAPE, CHICKEN_WEST_SHAPE, CHICKEN_EAST_SHAPE);
		}
		else if(this == VeBlocks.skeleton_plush)
		{
			 return VePlushBlock.defineShapes(state, SKELETON_SOUTH_SHAPE, SKELETON_NORTH_SHAPE, SKELETON_WEST_SHAPE, SKELETON_EAST_SHAPE);
		}
		else if(this == VeBlocks.white_horse_plush || this == VeBlocks.gray_horse_plush || this == VeBlocks.light_gray_horse_plush || this == VeBlocks.brown_horse_plush || this == VeBlocks.black_horse_plush || this == VeBlocks.purple_horse_plush)
		{
			return VePlushBlock.defineShapes(state, HORSE_Z_SHAPE, HORSE_Z_SHAPE, HORSE_X_SHAPE, HORSE_X_SHAPE);
		}
		else if(this == VeBlocks.enderman_plush)
		{
			return VePlushBlock.defineShapes(state, ENDERMAN_SOUTH_SHAPE, ENDERMAN_NORTH_SHAPE, ENDERMAN_WEST_SHAPE, ENDERMAN_EAST_SHAPE);
		}
		else if(this == VeBlocks.cow_plush || this == VeBlocks.red_mooshroom_plush || this == VeBlocks.brown_mooshroom_plush || this == VeBlocks.pig_plush || this == VeBlocks.guardian_plush)
		{
			return VePlushBlock.defineShapes(state, COW_Z_SHAPE, COW_Z_SHAPE, COW_X_SHAPE, COW_X_SHAPE);
		}
		else if(this == VeBlocks.endermite_plush)
		{
			return VePlushBlock.defineShapes(state, ENDERMITE_Z_SHAPE, ENDERMITE_Z_SHAPE, ENDERMITE_X_SHAPE, ENDERMITE_X_SHAPE);
		}
		else if(this == VeBlocks.silverfish_plush)
		{
			return VePlushBlock.defineShapes(state, SILVERFISH_Z_SHAPE, SILVERFISH_Z_SHAPE, SILVERFISH_X_SHAPE, SILVERFISH_X_SHAPE);
		}
		else if(this == VeBlocks.ocelot_plush || this == VeBlocks.wolf_plush)
		{
			return VePlushBlock.defineShapes(state, OCELOT_Z_SHAPE, OCELOT_Z_SHAPE, OCELOT_X_SHAPE, OCELOT_X_SHAPE);
		}
		else if(this == VeBlocks.plains_villager_plush || this == VeBlocks.desert_villager_plush || this == VeBlocks.jungle_villager_plush || this == VeBlocks.witch_plush || this == VeBlocks.squid_plush)
		{
			return VILLAGER_SHAPE;
		}
		else if(this == VeBlocks.cave_spider_plush)
		{
			return CAVE_SPIDER_SHAPE;
		}
		else if(this == VeBlocks.spider_plush)
		{
			return SPIDER_SHAPE;
		}
		else if(this == VeBlocks.ghast_plush)
		{
			return GHAST_SHAPE;
		}
		return NORMAL_CUBE;
	}
	
	/**
	 * Decide what shape the block should have depending on its direction property
	 */
	private static VoxelShape defineShapes(BlockState state, VoxelShape southShape, VoxelShape northShape, VoxelShape westShape, VoxelShape eastShape)
	{
		switch((Direction)state.get(HORIZONTAL_FACING))
		{
			case SOUTH:
				return southShape;
			case NORTH:
			default:
				return northShape;
			case WEST:
				return westShape;
			case EAST:
				return eastShape;
		}
	}
}
