package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import rcarmstrong20.vanilla_expansions.core.VeSoundEvents;

/**
 *
 * @author Ryan
 *
 */
public class VePurpleHugeMushroomBlock extends HugeMushroomBlock
{
    public VePurpleHugeMushroomBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void onLanded(IBlockReader worldIn, Entity entityIn)
    {}

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        float strength = 2.0F;
        float height = entityIn.fallDistance * strength;
        if (height > 0 && !entityIn.isCrouching())
        {
            if (height >= 100)
            {
                height = 100;
            }
            entityIn.setMotion(entityIn.getMotion().mul(1.0, 0.0, 1.0));
            entityIn.addVelocity(0, Math.sqrt(0.22 * (height + 0.25F)), 0);
            worldIn.playSound(null, pos, VeSoundEvents.BLOCK_MUSHROOM_BOUNCE, SoundCategory.BLOCKS, 1.0F,
                    0.8F + worldIn.rand.nextFloat() * 0.4F);
        }
        entityIn.fallDistance = 0;
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerWorld worldserver, BlockPos pos, BlockState state2,
            LivingEntity entity, int numberOfParticles)
    {
        return true;
    }
}
