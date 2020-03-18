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
		VillagerTrades.VILLAGER_DEFAULT_TRADES.put(VeVillagerProfessions.LUMBERJACK, VeVillagerTrades.LUMBERJACK_TRADES);
	}
	
	/**
	 * Register the render layers for each block.
	 */
	private void registerRenders()
	{
		RenderTypeLookup.setRenderLayer(VeBlocks.enderman_plush, RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_block, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_ore, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass_pane, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.spruce_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.birch_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.jungle_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.acacia_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.dark_oak_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.iron_ladder, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.modern_door, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass_pane, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass_pane, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass_pane, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_mushroom, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.woodcutter, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.white_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.orange_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.magenta_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.light_blue_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.yellow_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.lime_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.pink_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.light_gray_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.gray_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.cyan_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.blue_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.brown_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.green_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.black_campfire, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.bok_choy, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.garlic, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.green_onions, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.quinoa, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.ginger, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.blueberry_bush, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.cranberry_bush, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.witchs_cradle, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.regice_pokedoll, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.regirock_pokedoll, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.registeel_pokedoll, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(VeBlocks.regigigas_pokedoll, RenderType.getCutout());
	}
}
