package rcarmstrong20.vanilla_expansions.proxy;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import rcarmstrong20.vanilla_expansions.client.renderer.VeBlockAndItemColors;
import rcarmstrong20.vanilla_expansions.client.renderer.screen.VeWoodcutterScreen;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeContainerTypes;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onSetupClient()
	{
		super.onSetupClient();
		VeBlockAndItemColors.registerColorHandlers();
		//this.registerParticleFactories();
		//this.registerTileEntityRenders();
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
	private void registerScreenFactories()
	{
		ScreenManager.registerFactory(VeContainerTypes.WOODCUTTER, VeWoodcutterScreen::new);
	}
	/*
	private void registerTileEntityRenders()
	{
		ClientRegistry.bindTileEntityRenderer(VeTileEntityType.campfire, new VeCampfireTileEntityRenderer(TileEntityRendererDispatcher.instance));
	}
	*/
	private void registerRenders()
	{
		RenderTypeLookup.setRenderLayer(VeBlocks.enderman_plush, RenderType.func_228641_d_());
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_block, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.airite_ore, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_glass_pane, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.spruce_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.birch_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.jungle_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.acacia_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.dark_oak_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.iron_ladder, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.modern_door, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.spider_glass_pane, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_spider_glass_pane, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.fancy_spider_glass_pane, RenderType.func_228645_f_());
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_mushroom, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.woodcutter, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.white_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.orange_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.magenta_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.light_blue_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.yellow_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.lime_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.pink_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.light_gray_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.gray_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.cyan_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.purple_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.blue_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.brown_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.green_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.red_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.black_campfire, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.bok_choy, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.garlic, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.green_onions, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.quinoa, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.ginger, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.blueberry_bush, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.cranberry_bush, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.witchs_cradle, RenderType.func_228643_e_());
		RenderTypeLookup.setRenderLayer(VeBlocks.regigigas_pokedoll, RenderType.func_228643_e_());
	}
}
