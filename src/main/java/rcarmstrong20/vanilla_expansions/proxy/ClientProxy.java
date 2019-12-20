package rcarmstrong20.vanilla_expansions.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeUndervoidParticle;
import rcarmstrong20.vanilla_expansions.client.renderer.particle.VeVoidDripParticle;
import rcarmstrong20.vanilla_expansions.client.renderer.screen.VeWoodcutterScreen;
import rcarmstrong20.vanilla_expansions.client.renderer.tile_entity.VeCampfireTileEntityRenderer;
import rcarmstrong20.vanilla_expansions.core.VeContainerTypes;
import rcarmstrong20.vanilla_expansions.core.VeParticleTypes;
import rcarmstrong20.vanilla_expansions.core.VeTileEntityType;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onSetupClient()
	{
		super.onSetupClient();
		//VeBlockAndItemColors.registerColorHandlers();
		this.registerParticleFactories();
		this.registerTileEntityRenders();
		this.registerScreenFactories();
	}
	
	private void registerParticleFactories()
	{
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.DRIPPING_VOID, VeVoidDripParticle.DrippingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.FALLING_VOID, VeVoidDripParticle.FallingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.LANDING_VOID, VeVoidDripParticle.LandingVoidFactory::new);
		Minecraft.getInstance().particles.registerFactory(VeParticleTypes.UNDERVOID, VeUndervoidParticle.Factory::new);
	}
	
	private void registerScreenFactories()
	{
		ScreenManager.registerFactory(VeContainerTypes.WOODCUTTER, VeWoodcutterScreen::new);
	}
	
	private void registerTileEntityRenders()
	{
		ClientRegistry.bindTileEntityRenderer(VeTileEntityType.campfire, new VeCampfireTileEntityRenderer());
	}
}
