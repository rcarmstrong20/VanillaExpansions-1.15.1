package rcarmstrong20.vanilla_expansions.client.renderer.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.core.VeFluids;
import rcarmstrong20.vanilla_expansions.core.VeParticleTypes;

/**
 *
 * @author Ryan
 *
 */
@OnlyIn(Dist.CLIENT)
public class VeDripParticle extends SpriteTexturedParticle
{
    private final Fluid fluid;

    protected VeDripParticle(World world, double x, double y, double z, Fluid fluid)
    {
        super(world, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.fluid = fluid;
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.func_217576_g();
        if (!this.isExpired)
        {
            this.motionY -= this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.func_217577_h();
            if (!this.isExpired)
            {
                this.motionX *= 0.98F;
                this.motionY *= 0.98F;
                this.motionZ *= 0.98F;
                BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
                IFluidState ifluidstate = this.world.getFluidState(blockpos);
                if (ifluidstate.getFluid() == this.fluid
                        && this.posY < blockpos.getY() + ifluidstate.getActualHeight(this.world, blockpos))
                {
                    this.setExpired();
                }
            }
        }
    }

    protected void func_217576_g()
    {
        if (this.maxAge-- <= 0)
        {
            this.setExpired();
        }
    }

    protected void func_217577_h()
    {}

    @OnlyIn(Dist.CLIENT)
    static class VeDripping extends VeDripParticle
    {
        private final IParticleData data;

        private VeDripping(World world, double x, double y, double z, Fluid fluid, IParticleData data)
        {
            super(world, x, y, z, fluid);
            this.data = data;
            this.particleGravity *= 0.02F;
            this.maxAge = 40;
        }

        @Override
        protected void func_217576_g()
        {
            if (this.maxAge-- <= 0)
            {
                this.setExpired();
                this.world.addParticle(this.data, this.posX, this.posY, this.posZ, this.motionX, this.motionY,
                        this.motionZ);
            }
        }

        @Override
        protected void func_217577_h()
        {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class VeDrippingVoidFactory implements IParticleFactory<BasicParticleType>
    {
        protected final IAnimatedSprite sprite;

        public VeDrippingVoidFactory(IAnimatedSprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z,
                double xSpeed, double ySpeed, double zSpeed)
        {
            VeDripping drip_particle = new VeDripParticle.VeDripping(worldIn, x, y, z, VeFluids.VOID,
                    VeParticleTypes.falling_void);
            drip_particle.particleGravity *= 0.01F;
            drip_particle.maxAge = 100;
            drip_particle.setColor(0.1F, 0.1F, 0.1F);
            drip_particle.selectSpriteRandomly(this.sprite);
            return drip_particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class VeFallingLiquidParticle extends VeDripParticle
    {
        protected final IParticleData data;

        private VeFallingLiquidParticle(World world, double x, double y, double z, Fluid fluid, IParticleData data)
        {
            super(world, x, y, z, fluid);
            this.data = data;
            this.maxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        }

        @Override
        protected void func_217577_h()
        {
            if (this.onGround)
            {
                this.setExpired();
                this.world.addParticle(this.data, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class VeFallingVoidFactory implements IParticleFactory<BasicParticleType>
    {
        protected final IAnimatedSprite sprite;

        public VeFallingVoidFactory(IAnimatedSprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z,
                double xSpeed, double ySpeed, double zSpeed)
        {
            VeFallingLiquidParticle falling_particle = new VeDripParticle.VeFallingLiquidParticle(worldIn, x, y, z,
                    VeFluids.VOID, VeParticleTypes.landing_void);
            falling_particle.setColor(0.1F, 0.1F, 0.1F);
            falling_particle.selectSpriteRandomly(this.sprite);
            return falling_particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class VeLanding extends VeDripParticle
    {
        private VeLanding(World world, double x, double y, double z, Fluid fluid)
        {
            super(world, x, y, z, fluid);
            this.maxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class VeLandingVoidFactory implements IParticleFactory<BasicParticleType>
    {
        protected final IAnimatedSprite sprite;

        public VeLandingVoidFactory(IAnimatedSprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z,
                double xSpeed, double ySpeed, double zSpeed)
        {
            VeLanding land_particle = new VeDripParticle.VeLanding(worldIn, x, y, z, VeFluids.VOID);
            land_particle.maxAge = (int) (128.0D / (Math.random() * 0.8D + 0.2D));
            land_particle.setColor(0.1F, 0.1F, 0.1F);
            land_particle.selectSpriteRandomly(this.sprite);
            return land_particle;
        }
    }
}