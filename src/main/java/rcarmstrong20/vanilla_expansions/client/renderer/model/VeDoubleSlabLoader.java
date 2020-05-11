package rcarmstrong20.vanilla_expansions.client.renderer.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.IModelLoader;

@OnlyIn(Dist.CLIENT)
public class VeDoubleSlabLoader implements IModelLoader<VeDoubleSlabModel>
{
	@Override
	public VeDoubleSlabModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
	{
		return new VeDoubleSlabModel();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}
}
