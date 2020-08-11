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

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VeParticleTypes
{
	private static final List<BasicParticleType> PARTICLES = new ArrayList<>();
	
	public static BasicParticleType dripping_void = register("dripping_void", false);
	public static BasicParticleType falling_void = register("falling_void", false);
	public static BasicParticleType landing_void = register("landing_void", false);
	public static BasicParticleType undervoid = register("undervoid", true);
	
	private static BasicParticleType register(String name, boolean alwaysShow)
	{
		return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), new BasicParticleType(alwaysShow));
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