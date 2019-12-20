package rcarmstrong20.vanilla_expansions.client.renderer.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VeUndervoidParticle extends SpriteTexturedParticle
{
	private VeUndervoidParticle(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.particleRed = 14.0F;
		this.particleGreen = 14.0F;
		this.particleBlue = 14.0F;
		this.setSize(0.01F, 0.01F);
		this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
		this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
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
		if (this.maxAge-- <= 0)
		{
			this.setExpired();
		}
		else
		{
			this.move(this.motionX, this.motionY, this.motionZ);
			if (!this.world.getFluidState(new BlockPos(this.posX, this.posY, this.posZ)).isTagged(FluidTags.WATER))
			{
				this.setExpired();
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType>
	{
		private final IAnimatedSprite spriteSet;
		
		public Factory(IAnimatedSprite spriteSet)
		{
			this.spriteSet = spriteSet;
		}
		
		public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
		{
			VeUndervoidParticle undervoidparticle = new VeUndervoidParticle(worldIn, x, y, z);
			undervoidparticle.selectSpriteRandomly(this.spriteSet);
			return undervoidparticle;
		}
	}
}
