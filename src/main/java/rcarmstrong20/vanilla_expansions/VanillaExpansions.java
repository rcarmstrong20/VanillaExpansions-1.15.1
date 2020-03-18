package rcarmstrong20.vanilla_expansions;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeDripParticle;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeUndervoidParticle;
import rcarmstrong20.vanilla_expansions.core.VeParticleTypes;
import rcarmstrong20.vanilla_expansions.proxy.ClientProxy;
import rcarmstrong20.vanilla_expansions.proxy.CommonProxy;

@Mod("ve")
public class VanillaExpansions
{
	public static Object modInstance;
	public static final String MOD_ID = "ve";
	public static final Logger LOGGER = LogManager.getLogger(VanillaExpansions.MOD_ID);
	public static final VeItemGroup VE_GROUP = new VeItemGroup(VanillaExpansions.MOD_ID);
	public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public VanillaExpansions()
	{
		modInstance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegisterParticle);
		
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
	
	@OnlyIn(Dist.CLIENT)
	private void onRegisterParticle(ParticleFactoryRegisterEvent event)
	{
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.DRIPPING_VOID, VeDripParticle.VeDrippingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.FALLING_VOID, VeDripParticle.VeFallingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.LANDING_VOID, VeDripParticle.VeLandingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.UNDERVOID, VeUndervoidParticle.Factory::new);
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
	
	/*
	 * Controls the behavior of changing a white bunny to a killer bunny.
	 */
	
	@SubscribeEvent
	public void onNameBunnyEntity(final PlayerInteractEvent.EntityInteractSpecific event)
	{
		if(event.getTarget() instanceof RabbitEntity)
		{
			RabbitEntity rabbit = (RabbitEntity) event.getTarget();
			ItemStack itemStack = event.getItemStack();
			
			if(rabbit.getRabbitType() == 1 || rabbit.getRabbitType() == 99)
			{
				if(itemStack.getItem() == Items.NAME_TAG && itemStack.hasDisplayName())
				{
					if("The Killer Bunny".equals(itemStack.getDisplayName().getUnformattedComponentText()))
					{
						rabbit.setRabbitType(99);
						rabbit.getRabbitType();
					}
					else
					{
						rabbit.setRabbitType(1);
						rabbit.getRabbitType();
					}
				}
			}
		}
	}
}
