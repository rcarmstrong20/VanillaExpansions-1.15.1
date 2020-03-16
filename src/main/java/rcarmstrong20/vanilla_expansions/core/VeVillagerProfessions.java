package rcarmstrong20.vanilla_expansions.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VeVillagerProfessions
{
	private static final Constructor<VillagerProfession> VILLAGER_POFESSION_CONSTRUCTOR = ObfuscationReflectionHelper.findConstructor(VillagerProfession.class, String.class, PointOfInterestType.class, ImmutableSet.class, ImmutableSet.class, SoundEvent.class);
	private static final List<VillagerProfession> VILLAGER_PROFESSIONS = new ArrayList<>();
	
	
	public static final VillagerProfession LUMBERJACK = register(VanillaExpansions.MOD_ID, "lumberjack", VePointOfInterestTypes.LUMBERJACK, SoundEvents.ENTITY_VILLAGER_WORK_MASON);
	
	private static VillagerProfession register(String id, String name, PointOfInterestType pointOfInterest, SoundEvent sound)
	{
		VillagerProfession profession = null;
		try
		{
			profession = VILLAGER_POFESSION_CONSTRUCTOR.newInstance(id + ":" + name, pointOfInterest, ImmutableSet.of(), ImmutableSet.of(), sound);
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		profession.setRegistryName(VanillaExpansions.MOD_ID, name);
		VILLAGER_PROFESSIONS.add(profession);
		return profession;
	}
	
	@SubscribeEvent
	public static void registerVillagerProffesions(final RegistryEvent.Register<VillagerProfession> event)
	{
		VILLAGER_PROFESSIONS.forEach(villager_profession -> event.getRegistry().register(villager_profession));
		VILLAGER_PROFESSIONS.clear();
		VanillaExpansions.LOGGER.info("Villager professions registered.");
	}
}