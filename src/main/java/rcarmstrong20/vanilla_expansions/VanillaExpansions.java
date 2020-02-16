package rcarmstrong20.vanilla_expansions;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
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
	
	@SubscribeEvent
	public void onRightClickBlock(final RightClickBlock event)
	{
		BlockState worldState = event.getWorld().getBlockState(event.getPos());
		Item item = event.getItemStack().getItem();
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		Random random = new Random();
		IntegerProperty cropsAge = CropsBlock.AGE;
		IntegerProperty netherWartAge = NetherWartBlock.AGE;
		IntegerProperty beetrootAge = BeetrootBlock.BEETROOT_AGE;
		
		if(!event.getWorld().isRemote)
		{
			if(worldState.getBlock() instanceof CropsBlock && item != Items.BONE_MEAL)
			{
				if(worldState.getBlock() instanceof BeetrootBlock)
				{
					if(worldState.get(beetrootAge) == 3)
					{
						resetCropAndHarvest(worldState, world, pos, random, beetrootAge);
						event.setResult(Result.ALLOW);
						event.setCanceled(true);
					}
				}
				else if(worldState.get(cropsAge) == 7)
				{
					resetCropAndHarvest(worldState, world, pos, random, cropsAge);
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
				}
			}
			else if(worldState.getBlock() instanceof NetherWartBlock)
			{
				if(worldState.get(netherWartAge) == 3)
				{
					resetCropAndHarvest(worldState, world, pos, random, netherWartAge);
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
				}
			}
			else
			{
				event.setResult(Result.DEFAULT);
			}
		}
	}
	
	private static void resetCropAndHarvest(BlockState state, World world, BlockPos pos, Random random, IntegerProperty age)
	{
		world.setBlockState(pos, state.with(age, 0), 2);
		Block.spawnDrops(state, world, pos);
		world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
	}
}
