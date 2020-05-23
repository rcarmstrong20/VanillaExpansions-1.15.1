package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

/**
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeSoundEvents
{
	private static final List<SoundEvent> SOUNDS = new ArrayList<>();
	
	public static final SoundEvent BLOCK_MUSHROOM_BOUNCE = register("block.mushroom_bounce");
	public static final SoundEvent BLOCK_VOID_AMBIENT = register("block.void_ambient");
	public static final SoundEvent BLOCK_VOID_HARDENS = register("block.void_hardens");
	public static final SoundEvent BLOCK_CAMPFIRE_DYED = register("block.campfire_dyed");
	public static final SoundEvent UI_WOODCUTTER_TAKE_RESULT = register("ui.woodcutter.take_result");
	
	private static SoundEvent register(String name)
    {
		ResourceLocation location = new ResourceLocation(VanillaExpansions.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        SOUNDS.add(event);
        return event;
    }

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event)
    {
        SOUNDS.forEach(soundEvent -> event.getRegistry().register(soundEvent));
        SOUNDS.clear();
        
        VanillaExpansions.LOGGER.info("Sound events registered.");
    }
}
