package rcarmstrong20.vanilla_expansions.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VePointOfInterestTypes
{
	private static final Constructor<PointOfInterestType> POINT_OF_INTEREST_CONSTRUCTOR = ObfuscationReflectionHelper.findConstructor(PointOfInterestType.class, String.class, Set.class, int.class, int.class);
	private static final List<PointOfInterestType> POINT_OF_INTEREST_TYPES = new ArrayList<>();
	
	
	public static final PointOfInterestType LUMBERJACK = register("lumberjack", getAllStates(VeBlocks.woodcutter), 1, 1);
	
	private static PointOfInterestType register(String name, Set<BlockState> blockState, int p_221051_2_, int p_221051_4_)
	{
		try
		{
			return register(name, POINT_OF_INTEREST_CONSTRUCTOR.newInstance(name, blockState, p_221051_2_, p_221051_4_));
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Set<BlockState> getAllStates(Block block)
	{
		return ImmutableSet.copyOf(block.getStateContainer().getValidStates());
	}
	
	private static PointOfInterestType register(String name, PointOfInterestType pointOfInterest)
	{
		pointOfInterest.setRegistryName(VanillaExpansions.MOD_ID, name);
		POINT_OF_INTEREST_TYPES.add(pointOfInterest);
		return pointOfInterest;
	}
	
	@SubscribeEvent
	public static void registerPointOfInterestTypes(final RegistryEvent.Register<PointOfInterestType> event)
	{
		//A work around for forge's broken point of interest system.
		try
		{
			Method func_221052_a = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
			func_221052_a.invoke(null, cyclePointOfInterestType(POINT_OF_INTEREST_TYPES));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		VanillaExpansions.LOGGER.info("Point of Interests registered.");
	}
	
	/*
	 * Cycle through all the point of interest types in the given list.
	 */
	private static PointOfInterestType cyclePointOfInterestType(List<PointOfInterestType> pointOfInterestTypeList)
	{
		for(PointOfInterestType pointOfInterestType : pointOfInterestTypeList)
		{
			return pointOfInterestType;
		}
		return null;
	}
}