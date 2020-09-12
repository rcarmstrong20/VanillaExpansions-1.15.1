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

/**
 *
 * @author Ryan
 *
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeParticleTypes
{
    private static final List<BasicParticleType> PARTICLES = new ArrayList<>();

    public static BasicParticleType dripping_void = register("dripping_void", false);
    public static BasicParticleType falling_void = register("falling_void", false);
    public static BasicParticleType landing_void = register("landing_void", false);
    public static BasicParticleType undervoid = register("undervoid", true);
    public static BasicParticleType white_spark = register("white_spark", false);
    public static BasicParticleType orange_spark = register("orange_spark", false);
    public static BasicParticleType magenta_spark = register("magenta_spark", false);
    public static BasicParticleType light_blue_spark = register("light_blue_spark", false);
    public static BasicParticleType yellow_spark = register("yellow_spark", false);
    public static BasicParticleType lime_spark = register("lime_spark", false);
    public static BasicParticleType pink_spark = register("pink_spark", false);
    public static BasicParticleType gray_spark = register("gray_spark", false);
    public static BasicParticleType light_gray_spark = register("light_gray_spark", false);
    public static BasicParticleType cyan_spark = register("cyan_spark", false);
    public static BasicParticleType purple_spark = register("purple_spark", false);
    public static BasicParticleType blue_spark = register("blue_spark", false);
    public static BasicParticleType brown_spark = register("brown_spark", false);
    public static BasicParticleType green_spark = register("green_spark", false);
    public static BasicParticleType red_spark = register("red_spark", false);
    public static BasicParticleType black_spark = register("black_spark", false);

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