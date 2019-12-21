package rcarmstrong20.vanilla_expansions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import rcarmstrong20.vanilla_expansions.proxy.ClientProxy;
import rcarmstrong20.vanilla_expansions.proxy.CommonProxy;

@Mod("ve")
public class VanillaExpansions
{
	public static Object modInstance;
	public static final String MOD_ID = "ve";
	public static final String MINECRAFT_ID = "minecraft";
	public static final Logger LOGGER = LogManager.getLogger(VanillaExpansions.MOD_ID);
	public static final VeItemGroup VE_GROUP = new VeItemGroup(VanillaExpansions.MOD_ID);
	public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public VanillaExpansions()
	{
		modInstance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		
		LOGGER.info("setup method registered");
		PROXY.onSetupCommon();
	}
	
	private void clientRegistries(final FMLCommonSetupEvent event)
	{
		LOGGER.info("client method registered");
		PROXY.onSetupClient();
	}
	
	/**
	 * Get the resource location ve:name
	 */
	public static ResourceLocation location(String name)
	{
		return new ResourceLocation(VanillaExpansions.MOD_ID, name);
	}
	
	/**
	 * Get the resource location minecraft:name
	 */
	public static ResourceLocation vanillaLocation(String name)
	{
		return new ResourceLocation(VanillaExpansions.MINECRAFT_ID, name);
	}
}
