package rcarmstrong20.vanilla_expansions.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import rcarmstrong20.vanilla_expansions.core.VeBlockTags;

/**
 *
 * @author Ryan
 *
 */
public class VeEnderGrassBlock extends BushBlock
{
    protected static final VoxelShape ENDER_GRASS = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);

    public VeEnderGrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return isValidBlock(worldIn, pos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return isValidBlock(worldIn, pos);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing,
            IPlantable plantable)
    {
        return isValidBlock(world, pos);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double x = (double) pos.getX() + rand.nextFloat();
        double y = (double) pos.getY() + rand.nextFloat();
        double z = (double) pos.getZ() + rand.nextFloat();

        worldIn.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    /**
     * A helper method used for checking that the plant placement is valid.
     */
    private boolean isValidBlock(IBlockReader world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();

        if (block instanceof VePlantingPotBlock)
        {
            return VeBlockTags.END_PLANTABLE.contains(block) && VeBlockTags.END_POTTABLE.contains(this.getBlock());
        }
        else
        {
            return VeBlockTags.END_PLANTABLE.contains(block);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return ENDER_GRASS;
    }
}
