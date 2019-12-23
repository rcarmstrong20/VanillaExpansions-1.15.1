package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.item.crafting.VeWoodcuttingRecipe;

public class VeRecipeTypes
{
	public static final IRecipeType<VeWoodcuttingRecipe> WOODCUTTING = register(VanillaExpansions.MOD_ID, "woodcutting");
	
	static <T extends IRecipe<?>> IRecipeType<T> register(final String id, final String key)
	{
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(id, key), new IRecipeType<T>()
		{
			public String toString()
			{
				return key;
			}
		});
	}
}