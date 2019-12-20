package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.fluid.VoidFluid;

/*
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeFluids
{
	private static final List<Fluid> FLUIDS = new ArrayList<>();
	
	public static final Fluid VOID = register(VanillaExpansions.location("void"), new VoidFluid.Source());
	public static final FlowingFluid FLOWING_VOID = register(VanillaExpansions.location("flowing_void"), new VoidFluid.Flowing());
	
	private static FlowingFluid register(ResourceLocation location, FlowingFluid fluid)
	{
		fluid.setRegistryName(location);
		FLUIDS.add(fluid);
		return fluid;
	}
	
	@SubscribeEvent
	public static void registerFluids(final RegistryEvent.Register<Fluid> event)
	{
		FLUIDS.forEach(fluid -> event.getRegistry().register(fluid));
		FLUIDS.clear();
		
		VanillaExpansions.LOGGER.info("Fluids registered.");
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onFogColor(EntityViewRenderEvent.FogColors event)
	{
		ActiveRenderInfo info = event.getInfo();
		IFluidState state = info.getFluidState();
		if (state.getFluid() instanceof VoidFluid)
		{
			event.setRed(0.0F);
			event.setGreen(0.0F);
			event.setBlue(0.0F);
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onFogDensity(EntityViewRenderEvent.FogDensity event)
	{
		ActiveRenderInfo info = event.getInfo();
		IFluidState state = info.getFluidState();
		if (state.getFluid() instanceof VoidFluid)
		{
			//GlStateManager.fogMode(GlStateManager.FogMode.EXP);
			event.setDensity(1.0F);
			event.setCanceled(true);
		} 
	}
}
