package rcarmstrong20.vanilla_expansions.fluid;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeFluidTags;
import rcarmstrong20.vanilla_expansions.core.VeFluids;
import rcarmstrong20.vanilla_expansions.core.VeItems;
import rcarmstrong20.vanilla_expansions.core.VeParticleTypes;
import rcarmstrong20.vanilla_expansions.core.VeSoundEvents;

/**
 *
 * @author Ryan
 *
 */
public abstract class VeVoidFluid extends WaterFluid
{
    @Override
    public Fluid getFlowingFluid()
    {
        return VeFluids.FLOWING_VOID;
    }

    @Override
    public Fluid getStillFluid()
    {
        return VeFluids.VOID;
    }

    @Override
    public Item getFilledBucket()
    {
        return VeItems.void_bucket;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World worldIn, BlockPos pos, IFluidState state, Random random)
    {
        if (random.nextInt(100) == 0)
        {
            worldIn.addParticle(VeParticleTypes.undervoid, (double) pos.getX() + (double) random.nextFloat(),
                    (double) pos.getY() + (double) random.nextFloat(),
                    (double) pos.getZ() + (double) random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
        else if (random.nextInt(600) == 0)
        {
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), VeSoundEvents.BLOCK_VOID_AMBIENT,
                    SoundCategory.BLOCKS, random.nextFloat() * 0.2F + 0.2F, random.nextFloat() + 0.5F, false);
        }
    }

    @Override
    public boolean canDisplace(IFluidState state, IBlockReader reader, BlockPos pos, Fluid fluid, Direction direction)
    {
        return direction == Direction.DOWN && !fluid.isIn(VeFluidTags.VOID);
    }

    @Override
    protected FluidAttributes createAttributes()
    {
        return FluidAttributes
                .builder(new ResourceLocation(VanillaExpansions.MOD_ID, "block/void_still"),
                        new ResourceLocation(VanillaExpansions.MOD_ID, "block/void_flow"))
                .luminosity(20).density(1000).viscosity(3000).build(this);
    }

    @Override
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IParticleData getDripParticleData()
    {
        return VeParticleTypes.dripping_void;
    }

    @Override
    public BlockState getBlockState(IFluidState state)
    {
        return VeBlocks.void_liquid.getDefaultState().with(FlowingFluidBlock.LEVEL,
                Integer.valueOf(getLevelFromState(state)));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluidIn)
    {
        return fluidIn == VeFluids.VOID || fluidIn == VeFluids.FLOWING_VOID;
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_)
    {
        return 10;
    }

    public static class Flowing extends VeVoidFluid
    {
        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder)
        {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(IFluidState state)
        {
            return state.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state)
        {
            return false;
        }
    }

    public static class Source extends VeVoidFluid
    {
        @Override
        public int getLevel(IFluidState p_207192_1_)
        {
            return 8;
        }

        @Override
        public boolean isSource(IFluidState state)
        {
            return true;
        }
    }
}
