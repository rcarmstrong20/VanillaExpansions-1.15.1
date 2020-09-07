package rcarmstrong20.vanilla_expansions.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VePottedSnapdragonBlock extends FlowerPotBlock
{
    @SuppressWarnings("deprecation")
    public VePottedSnapdragonBlock(Block flower, Block.Properties properties)
    {
        super(flower, properties);
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
}
