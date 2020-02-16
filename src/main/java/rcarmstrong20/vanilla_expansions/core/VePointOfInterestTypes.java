package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VePointOfInterestTypes
{
	private static final List<PointOfInterestType> POINT_OF_INTEREST_TYPES = new ArrayList<>();
	
	public static final PointOfInterestType LUMBERJACK = register("lumberjack", getAllStates(VeBlocks.woodcutter), 1, SoundEvents.ENTITY_VILLAGER_WORK_MASON, 1);
	
	private static PointOfInterestType register(String name, Set<BlockState> blockState, int p_221051_2_, @Nullable SoundEvent soundEvent, int p_221051_4_)
	{
		return register(name, new PointOfInterestType(name, blockState, p_221051_4_, null, p_221051_4_));
	}
	
	private static PointOfInterestType register(String name, PointOfInterestType pointOfInterest)
	{
		pointOfInterest.setRegistryName(VanillaExpansions.location(name));
		POINT_OF_INTEREST_TYPES.add(pointOfInterest);
		return pointOfInterest;
	}
	
	@SubscribeEvent
	public static void registerPointOfInterestTypes(final RegistryEvent.Register<PointOfInterestType> event)
	{
		POINT_OF_INTEREST_TYPES.forEach(pointOfInterestTypes -> event.getRegistry().register(pointOfInterestTypes));
		POINT_OF_INTEREST_TYPES.clear();
		VanillaExpansions.LOGGER.info("Point of Interests registered.");
	}
}