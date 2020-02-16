package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VeVillagerProfessions
{
	private static final List<VillagerProfession> VILLAGER_PROFESSIONS = new ArrayList<>();
	
	public static final VillagerProfession LUMBERJACK = register(VanillaExpansions.MOD_ID, "lumberjack", VePointOfInterestTypes.LUMBERJACK);
	
	private static VillagerProfession register(String id, String name, PointOfInterestType pointOfInterest)
	{
		VillagerProfession profession = new VillagerProfession(id + ":" + name, pointOfInterest, ImmutableSet.of(), ImmutableSet.of());
		profession.setRegistryName(VanillaExpansions.location(name));
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