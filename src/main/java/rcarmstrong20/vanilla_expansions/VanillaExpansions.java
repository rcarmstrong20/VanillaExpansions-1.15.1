package rcarmstrong20.vanilla_expansions;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap.Builder;

import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
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
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeDripParticle;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeUndervoidParticle;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeParticleTypes;
import rcarmstrong20.vanilla_expansions.core.VeSoundEvents;
import rcarmstrong20.vanilla_expansions.proxy.ClientProxy;
import rcarmstrong20.vanilla_expansions.proxy.CommonProxy;

@Mod("ve")
public class VanillaExpansions
{
	public static Object modInstance;
	public static final Logger LOGGER = LogManager.getLogger(VanillaExpansions.MOD_ID);
	public static final String MOD_ID = "ve";
	public static final VeItemGroup VE_GROUP = new VeItemGroup(VanillaExpansions.MOD_ID);
	public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public VanillaExpansions()
	{
		modInstance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegisterParticle);
		ModLoadingContext.get().registerConfig(Type.COMMON, VeConfig.COMMON_SPEC, "ve-common.toml");
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/*
	 * Called on the mod's setup.
	 */
	private void setup(final FMLCommonSetupEvent event)
	{
		VanillaExpansions.LOGGER.info("setup method registered");
		PROXY.onSetupCommon();
	}
	
	/*
	 * Called exclusively on the client.
	 */
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		VanillaExpansions.LOGGER.info("client method registered");
		PROXY.onSetupClient();
	}
	
	/*
	 * This takes care of registering the particle factories if they are not registered under the particle factory event there will be a bug.
	 */
	@OnlyIn(Dist.CLIENT)
	private void onRegisterParticle(ParticleFactoryRegisterEvent event)
	{
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.DRIPPING_VOID, VeDripParticle.VeDrippingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.FALLING_VOID, VeDripParticle.VeFallingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.LANDING_VOID, VeDripParticle.VeLandingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.UNDERVOID, VeUndervoidParticle.Factory::new);
	}
	
	/**
	 * Controls right-click crop harvesting and campfire re-coloring behavior.
	 */
	@SubscribeEvent
	public void onRightClickBlock(final RightClickBlock event)
	{
		//General variables
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		
		//Block and items
		BlockState worldState = event.getWorld().getBlockState(pos);
		ItemStack itemStack = event.getItemStack();
		TileEntity tileEntity = event.getWorld().getTileEntity(pos);
		
		//Crop age properties
		IntegerProperty cropsAge = CropsBlock.AGE;
		IntegerProperty netherWartAge = NetherWartBlock.AGE;
		IntegerProperty beetrootAge = BeetrootBlock.BEETROOT_AGE;
		IntegerProperty cocoaAge = CocoaBlock.AGE;
		
		//Campfire properties
		BooleanProperty isLit = CampfireBlock.LIT;
		
		if(!event.getWorld().isRemote)
		{
			//If the block your clicking is a crop and your not using bone meal return true.
			if(!VeConfig.Common.rightClickHarvestBlackList.get().contains(worldState.getBlock().getRegistryName().toString()) && worldState.getBlock() instanceof CropsBlock && itemStack.getItem() != Items.BONE_MEAL)
			{
				for(int i = 0; event.getWorld().getBlockState(pos.down(i)).getBlock() instanceof CropsBlock; i++)
				{
					if(event.getWorld().getBlockState(pos.down()) == Blocks.FARMLAND.getDefaultState())
					{
						if(worldState.getBlock() instanceof BeetrootBlock)
						{
							//When the beet root crop is fully grown and clicked then harvest it.
							if(worldState.get(beetrootAge) == getMaxAge(beetrootAge))
							{
								resetCrop(worldState, world, pos, beetrootAge);
								event.setResult(Result.ALLOW);
								event.setCanceled(true);
							}
						}
						//If its not a beet root and a crop then it must be a normal 7 stage crop and if it's fully grown harvest it.
						else if(worldState.get(cropsAge) == getMaxAge(cropsAge))
						{
							resetCrop(worldState, world, pos, cropsAge);
							event.setResult(Result.ALLOW);
							event.setCanceled(true);
						}
					}
					else
					{
						Block.replaceBlock(worldState, Blocks.AIR.getDefaultState(), world, pos, 1);
					}
				}
			}
			//If its not a crop it might be nether wart and if so check if it's fully grown and if so harvest it.
			else if(worldState.getBlock() instanceof NetherWartBlock)
			{
				if(worldState.get(netherWartAge) == getMaxAge(netherWartAge))
				{
					resetCrop(worldState, world, pos, netherWartAge);
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
				}
			}
			else if(worldState.getBlock() instanceof CocoaBlock)
			{
				if(worldState.get(cocoaAge) == getMaxAge(cocoaAge))
				{
					resetCrop(worldState, world, pos, cocoaAge);
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
				}
			}
			else if(worldState.getBlock() == Blocks.CAMPFIRE && tileEntity instanceof CampfireTileEntity)
			{
				Map<Item, Block> dyeToCampfire = (new Builder<Item, Block>()).put(Items.WHITE_DYE, VeBlocks.white_campfire)
																		 	 .put(Items.ORANGE_DYE, VeBlocks.orange_campfire)
																		     .put(Items.MAGENTA_DYE, VeBlocks.magenta_campfire)
																		     .put(Items.LIGHT_BLUE_DYE, VeBlocks.light_blue_campfire)
																		     .put(Items.YELLOW_DYE, VeBlocks.yellow_campfire)
																		     .put(Items.LIME_DYE, VeBlocks.lime_campfire)
																		     .put(Items.PINK_DYE, VeBlocks.pink_campfire)
																		     .put(Items.GRAY_DYE, VeBlocks.gray_campfire)
																		     .put(Items.LIGHT_GRAY_DYE, VeBlocks.light_gray_campfire)
																		     .put(Items.CYAN_DYE, VeBlocks.cyan_campfire)
																		     .put(Items.PURPLE_DYE, VeBlocks.purple_campfire)
																		     .put(Items.BLUE_DYE, VeBlocks.blue_campfire)
																		     .put(Items.BROWN_DYE, VeBlocks.brown_campfire)
																		     .put(Items.GREEN_DYE, VeBlocks.green_campfire)
																		     .put(Items.RED_DYE, VeBlocks.red_campfire)
																		     .put(Items.BLACK_DYE, VeBlocks.black_campfire).build();
				
				CampfireTileEntity campfireTileEntity = (CampfireTileEntity) tileEntity;
				NonNullList<ItemStack> campfireInventory = campfireTileEntity.getInventory();
				Direction currentFacing = worldState.get(CampfireBlock.FACING);
				
				//Only convert if the campfire is lit, the player is holding one of the dyes, and the campfire's inventory is empty.
				if(worldState.get(isLit) && dyeToCampfire.containsKey(itemStack.getItem()) && campfireInventory.get(0) == ItemStack.EMPTY)
				{
					world.playSound((PlayerEntity)null, pos, VeSoundEvents.BLOCK_CAMPFIRE_DYED, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
					world.setBlockState(pos, dyeToCampfire.get(itemStack.getItem()).getDefaultState().with(CampfireBlock.FACING, currentFacing));
					itemStack.shrink(1);
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
	
	/**
	 * A helper method that harvests and the passed in crop.
	 */
	private void resetCrop(BlockState state, World world, BlockPos pos, IntegerProperty age)
	{
		Block.replaceBlock(state, Blocks.AIR.getDefaultState(), world, pos, 1); //If the replacement block is anything but air the blocks don't drop and play their appropriate sound.
		world.setBlockState(pos, state.with(age, 0));
	}
	
	/**
	 * A helper method that gets the max age for the age property passed.
	 */
	private int getMaxAge(IntegerProperty age)
	{
		return age.getAllowedValues().size() - 1;
	}
	
	/*
	@SubscribeEvent
	public void onRightClickSlab(final PlayerInteractEvent.RightClickBlock event)
	{
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		Direction direction = event.getFace();
		
		BlockState worldState = event.getWorld().getBlockState(pos);
		BlockState worldStateUp = event.getWorld().getBlockState(pos.up());
		Item item = event.getItemStack().getItem();
		Block itemBlock = Block.getBlockFromItem(item.getItem());
		
		if(worldState.getBlock() instanceof SlabBlock && itemBlock instanceof SlabBlock)
		{
			if(worldState.getMaterial() == itemBlock.getDefaultState().getMaterial())
			{
				SlabType slabtype = worldState.get(SlabBlock.TYPE);
				
				if(slabtype != SlabType.DOUBLE && worldState.getBlock() != itemBlock)
				{
					if (slabtype == SlabType.BOTTOM && direction == Direction.UP || direction.getAxis().isHorizontal())
					{
						System.out.print("Placed top on bottom");
						VeDoubleSlabBlock.fillInventory(itemBlock, worldState.getBlock());
					}
					else if (slabtype == SlabType.TOP && direction == Direction.DOWN || direction.getAxis().isHorizontal())
					{
						System.out.print("Placed bottom under top");
						VeDoubleSlabBlock.fillInventory(worldState.getBlock(), itemBlock);
					}
					world.setBlockState(pos, VeBlocks.double_slab.getDefaultState());
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
				}
			}
		}
		else if(worldStateUp.getBlock() instanceof SlabBlock && itemBlock instanceof SlabBlock)
		{
			if(worldStateUp.getMaterial() == itemBlock.getDefaultState().getMaterial())
			{
				SlabType slabtypeUp = worldStateUp.get(SlabBlock.TYPE);
				
				if(slabtypeUp != SlabType.DOUBLE && worldState.getBlock() != itemBlock)
				{
					if (slabtypeUp == SlabType.TOP && direction == Direction.UP)
					{
						System.out.print("Placed bottom on top from bottom");
						VeDoubleSlabBlock.fillInventory(itemBlock, worldStateUp.getBlock());
						world.setBlockState(pos.up(), VeBlocks.double_slab.getDefaultState());
						event.setResult(Result.ALLOW);
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	/**
	 * Individually tracks the naming behavior for each white or killer rabbit entity, then either sets the rabbit type to 99 or 1.
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
						rabbit.tick();
					}
					else
					{
						rabbit.setRabbitType(1);
						rabbit.getRabbitType();
						rabbit.tick();
					}
				}
			}
		}
	}
}
