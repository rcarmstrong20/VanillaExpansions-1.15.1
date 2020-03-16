package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.inventory.container.VeWoodcutterContainer;

/*
 * Author: rcarmstrong20
 */

@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeContainerTypes
{
	public static final List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();
	
	public static final ContainerType<VeWoodcutterContainer> WOODCUTTER = register("woodcutter", VeWoodcutterContainer::new);
	
	private static <T extends Container> ContainerType<T> register(String name, ContainerType.IFactory<T> factory)
	{
		return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), factory);
	}
	
	private static <T extends Container> ContainerType<T> register(ResourceLocation name, ContainerType.IFactory<T> factory)
	{
		ContainerType<T> type = new ContainerType<>(factory);
		type.setRegistryName(name);
		CONTAINER_TYPES.add(type);
		return type;
	}
	
	@SubscribeEvent
    public static void registerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
		CONTAINER_TYPES.forEach(type -> event.getRegistry().register(type));
		CONTAINER_TYPES.clear();
		VanillaExpansions.LOGGER.info("Container types registered.");
    }
}
