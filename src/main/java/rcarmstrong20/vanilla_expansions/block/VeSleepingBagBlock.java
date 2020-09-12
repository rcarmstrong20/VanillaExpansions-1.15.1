package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/**
 *
 * @author Ryan
 *
 */
public class VeSleepingBagBlock extends BedBlock
{
    protected static final VoxelShape SLEEPING_BAG_NORTH_BASE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 4.0D,
            16.0D);
    protected static final VoxelShape SLEEPING_BAG_NORTH_TOP = Block.makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 5.0D,
            16.0D);
    protected static final VoxelShape SLEEPING_BAG_NORTH_PILLOW = Block.makeCuboidShape(2.0D, 5.0D, 2.0D, 14.0D, 5.5D,
            8.5D);
    protected static final VoxelShape SLEEPING_BAG_NORTH_HEAD = VoxelShapes.or(SLEEPING_BAG_NORTH_BASE,
            SLEEPING_BAG_NORTH_TOP, SLEEPING_BAG_NORTH_PILLOW);
    protected static final VoxelShape SLEEPING_BAG_SOUTH_FOOT = VoxelShapes.or(SLEEPING_BAG_NORTH_BASE,
            SLEEPING_BAG_NORTH_TOP);
    protected static final VoxelShape SLEEPING_BAG_SOUTH_BASE = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 4.0D,
            15.0D);
    protected static final VoxelShape SLEEPING_BAG_SOUTH_TOP = Block.makeCuboidShape(2.0D, 4.0D, 0.0D, 14.0D, 5.0D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_SOUTH_PILLOW = Block.makeCuboidShape(2.0D, 5.0D, 7.5D, 14.0D, 5.5D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_SOUTH_HEAD = VoxelShapes.or(SLEEPING_BAG_SOUTH_BASE,
            SLEEPING_BAG_SOUTH_TOP, SLEEPING_BAG_SOUTH_PILLOW);
    protected static final VoxelShape SLEEPING_BAG_NORTH_FOOT = VoxelShapes.or(SLEEPING_BAG_SOUTH_BASE,
            SLEEPING_BAG_SOUTH_TOP);
    protected static final VoxelShape SLEEPING_BAG_WEST_BASE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 16.0D, 4.0D,
            15.0D);
    protected static final VoxelShape SLEEPING_BAG_WEST_TOP = Block.makeCuboidShape(2.0D, 4.0D, 2.0D, 16.0D, 5.0D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_WEST_PILLOW = Block.makeCuboidShape(2.0D, 5.0D, 2.0D, 8.5D, 5.5D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_WEST_HEAD = VoxelShapes.or(SLEEPING_BAG_WEST_BASE,
            SLEEPING_BAG_WEST_TOP, SLEEPING_BAG_WEST_PILLOW);
    protected static final VoxelShape SLEEPING_BAG_EAST_FOOT = VoxelShapes.or(SLEEPING_BAG_WEST_BASE,
            SLEEPING_BAG_WEST_TOP);
    protected static final VoxelShape SLEEPING_BAG_EAST_BASE = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 15.0D, 4.0D,
            15.0D);
    protected static final VoxelShape SLEEPING_BAG_EAST_TOP = Block.makeCuboidShape(0.0D, 4.0D, 2.0D, 14.0D, 5.0D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_EAST_PILLOW = Block.makeCuboidShape(7.5D, 5.0D, 2.0D, 14.0D, 5.5D,
            14.0D);
    protected static final VoxelShape SLEEPING_BAG_EAST_HEAD = VoxelShapes.or(SLEEPING_BAG_EAST_BASE,
            SLEEPING_BAG_EAST_TOP, SLEEPING_BAG_EAST_PILLOW);
    protected static final VoxelShape SLEEPING_BAG_WEST_FOOT = VoxelShapes.or(SLEEPING_BAG_EAST_BASE,
            SLEEPING_BAG_EAST_TOP);

    public VeSleepingBagBlock(DyeColor color, Properties properties)
    {
        super(color, properties);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return defineShapes(state, SLEEPING_BAG_SOUTH_HEAD, SLEEPING_BAG_SOUTH_FOOT, SLEEPING_BAG_NORTH_HEAD,
                SLEEPING_BAG_NORTH_FOOT, SLEEPING_BAG_WEST_HEAD, SLEEPING_BAG_WEST_FOOT, SLEEPING_BAG_EAST_HEAD,
                SLEEPING_BAG_EAST_FOOT);
    }

    private static VoxelShape defineShapes(BlockState state, VoxelShape southShapeHead, VoxelShape southShapeFoot,
            VoxelShape northShapeHead, VoxelShape northShapeFoot, VoxelShape westShapeHead, VoxelShape westShapeFoot,
            VoxelShape eastShapeHead, VoxelShape eastShapeFoot)
    {
        switch (state.get(HORIZONTAL_FACING))
        {
            case NORTH:
                return getPartShapes(state, northShapeHead, northShapeFoot);
            case SOUTH:
                return getPartShapes(state, southShapeHead, southShapeFoot);
            case WEST:
                return getPartShapes(state, westShapeHead, westShapeFoot);
            default:
                return getPartShapes(state, eastShapeHead, eastShapeFoot);
        }
    }

    private static VoxelShape getPartShapes(BlockState state, VoxelShape headShape, VoxelShape feetShape)
    {
        switch (state.get(PART))
        {
            case HEAD:
                return headShape;
            default:
                return feetShape;
        }
    }
}
