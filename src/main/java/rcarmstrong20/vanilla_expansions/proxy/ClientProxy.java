package rcarmstrong20.vanilla_expansions.proxy;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import rcarmstrong20.vanilla_expansions.client.renderer.VeBlockAndItemColors;
import rcarmstrong20.vanilla_expansions.client.renderer.screen.VeWoodcutterScreen;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeContainerTypes;
import rcarmstrong20.vanilla_expansions.core.VeVillagerProfessions;
import rcarmstrong20.vanilla_expansions.core.VeVillagerTrades;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onSetupClient()
	{
		super.onSetupClient();
		VeBlockAndItemColors.registerColorHandlers();
		this.registerTrades();
		this.registerScreenFactories();
		this.registerRenders();
	}
	/*
	private void registerParticleFactories()
	{
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.DRIPPING_VOID, VeVoidDripParticle.DrippingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.FALLING_VOID, VeVoidDripParticle.FallingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.LANDING_VOID, VeVoidDripParticle.LandingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.UNDERVOID, VeUndervoidParticle.Factory::new);
	}
	*/
	
	/*
	 * Register the screen factories for the containers.
	 */
	private void registerScreenFactories()
	{
		ScreenManager.registerFactory(VeContainerTypes.WOODCUTTER, VeWoodcutterScreen::new);
	}
	
	/*
	 * Add the new trade lists to the vanilla ones along with the corresponding profession for registry.
	 */
	private void registerTrades()
	{
		VillagerTrades.field_221239_a.put(VeVillagerProfessions.LUMBERJACK, VeVillagerTrades.LUMBERJACK_TRADES);
	}
	
	/*
	private void registerTileEntityRenders()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(VeCampfireTileEntity.class, new VeCampfireTileEntityRenderer());
	}
	*/
	
	/**
	 * Register the render layers for each block.
	 */
	private void registerRenders()
	{
		RenderType cutout = RenderType.func_228643_e_();
		RenderType cutoutMipped = RenderType.func_228641_d_();
		RenderType translucent = RenderType.func_228645_f_();
		
		RenderTypeLookup.setRenderLayer(VeBlocks.enderman_plush, cutoutMipped);
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_block, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_ore, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass_pane, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.spruce_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.birch_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.jungle_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.acacia_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.dark_oak_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.iron_ladder, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.modern_door, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass_pane, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass_pane, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass_pane, translucent);
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_mushroom, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.woodcutter, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.white_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.orange_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.magenta_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.light_blue_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.yellow_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.lime_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.pink_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.light_gray_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.gray_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.cyan_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.blue_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.brown_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.green_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.red_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.black_campfire, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.bok_choy, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.garlic, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.green_onions, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.quinoa, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.ginger, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.blueberry_bush, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.cranberry_bush, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.witchs_cradle, cutout);
		RenderTypeLookup.setRenderLayer(VeBlocks.regigigas_pokedoll, cutout);
	}
}
