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

@OnlyIn(Dist.CLIENT)
public class VeVoidDripParticle extends SpriteTexturedParticle
{	
	private final Fluid fluid;
	
	private VeVoidDripParticle(World world, double x, double y, double z, Fluid fluid)
	{
		super(world, x, y, z);
		this.setSize(0.01F, 0.01F);
		this.particleGravity = 0.06F;
		this.fluid = fluid;
	}
	
	public IParticleRenderType getRenderType()
	{
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	public void tick()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.func_217576_g();
		if (!this.isExpired) {
			this.motionY -= (double)this.particleGravity;
			this.move(this.motionX, this.motionY, this.motionZ);
			this.func_217577_h();
			if (!this.isExpired)
			{
				this.motionX *= (double)0.98F;
				this.motionY *= (double)0.98F;
				this.motionZ *= (double)0.98F;
				BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
				IFluidState ifluidstate = this.world.getFluidState(blockpos);
				if (ifluidstate.getFluid() == this.fluid && this.posY < (double)((float)blockpos.getY() + ifluidstate.func_215679_a(this.world, blockpos))) {
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
	
	protected void func_217577_h() {}
	
	@OnlyIn(Dist.CLIENT)
	static class Dripping extends VeVoidDripParticle
	{
		private final IParticleData particleData;
		
		private Dripping(World world, double x, double y, double z, Fluid fluid, IParticleData particleData)
		{
			super(world, x, y, z, fluid);
			this.particleData = particleData;
			this.particleGravity *= 0.02F;
			this.maxAge = 40;
		}
		
		protected void func_217576_g()
		{
			if (this.maxAge-- <= 0)
			{
				this.setExpired();
				this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
			}
		}
		
		protected void func_217577_h()
		{
			this.motionX *= 0.02D;
			this.motionY *= 0.02D;
			this.motionZ *= 0.02D;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	static class DrippingVoid extends VeVoidDripParticle.Dripping
	{
		private DrippingVoid(World world, double x, double y, double z, Fluid fluid, IParticleData data)
		{
			super(world, x, y, z, fluid, data);
		}
		
		protected void func_217576_g()
		{
			super.func_217576_g();
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class DrippingVoidFactory implements IParticleFactory<BasicParticleType>
	{
		protected final IAnimatedSprite spriteSet;
		
		public DrippingVoidFactory(IAnimatedSprite spriteSet)
		{
			this.spriteSet = spriteSet;
		}
		
		public Particle makeParticle(BasicParticleType basicParticleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
		{
			VeVoidDripParticle.DrippingVoid dripparticle$drippingvoid = new VeVoidDripParticle.DrippingVoid(world, x, y, z, VeFluids.VOID, VeParticleTypes.DRIPPING_VOID);
			dripparticle$drippingvoid.selectSpriteRandomly(this.spriteSet);
			return dripparticle$drippingvoid;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	static class Falling extends VeVoidDripParticle
	{
		private final IParticleData particleData;
		
		private Falling(World world, double x, double y, double z, Fluid fluid, IParticleData particleData)
		{
			super(world, x, y, z, fluid);
			this.particleData = particleData;
			this.maxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
		}
		
		protected void func_217577_h()
		{
			if (this.onGround)
			{
				this.setExpired();
				this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class FallingVoidFactory implements IParticleFactory<BasicParticleType>
	{
		protected final IAnimatedSprite spriteSet;
		
		public FallingVoidFactory(IAnimatedSprite spriteIn)
		{
			this.spriteSet = spriteIn;
		}
		
		public Particle makeParticle(BasicParticleType basicParticleType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
		{
			VeVoidDripParticle dripparticle = new VeVoidDripParticle.Falling(world, x, y, z, VeFluids.VOID, VeParticleTypes.FALLING_VOID);
			dripparticle.selectSpriteRandomly(this.spriteSet);
			return dripparticle;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	static class Landing extends VeVoidDripParticle
	{
		private Landing(World world, double x, double y, double z, Fluid fluid)
		{
			super(world, x, y, z, fluid);
			this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class LandingVoidFactory implements IParticleFactory<BasicParticleType>
	{
		protected final IAnimatedSprite spriteSet;
		
		public LandingVoidFactory(IAnimatedSprite spriteIn)
		{
			this.spriteSet = spriteIn;
		}
		
		public Particle makeParticle(BasicParticleType basicParticalType, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
		{
			VeVoidDripParticle dripparticle = new VeVoidDripParticle.Landing(world, x, y, z, VeFluids.VOID);
			dripparticle.selectSpriteRandomly(this.spriteSet);
			return dripparticle;
		}
	}
}
