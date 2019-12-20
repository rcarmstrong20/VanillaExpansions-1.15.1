package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
/*
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VeParticleTypes
{
	private static final List<BasicParticleType> PARTICLES = new ArrayList<>();
	
	public static final BasicParticleType DRIPPING_VOID = register(VanillaExpansions.location("dripping_void"), false);
	public static final BasicParticleType FALLING_VOID = register(VanillaExpansions.location("falling_void"), false);
	public static final BasicParticleType LANDING_VOID = register(VanillaExpansions.location("landing_void"), false);
	public static final BasicParticleType UNDERVOID = register(VanillaExpansions.location("undervoid"), false);
	
	private static BasicParticleType register(ResourceLocation name, boolean alwaysShow)
	{
		return register(name, new BasicParticleType(alwaysShow));
	}
	
	private static BasicParticleType register(ResourceLocation name, BasicParticleType particle)
	{
		particle.setRegistryName(name);
		PARTICLES.add(particle);
		return particle;
	}
	
	@SubscribeEvent
	public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event)
	{
		PARTICLES.forEach(particle -> event.getRegistry().register(particle));
		PARTICLES.clear();
		VanillaExpansions.LOGGER.info("Particles registered.");
	}
}
*/