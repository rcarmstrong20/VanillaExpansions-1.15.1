package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.item.crafting.VeWoodCuttingRecipe;

@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeRecipeSerializers
{
    private static final List<IRecipeSerializer<?>> RECIPES = new ArrayList<>();

    public static final IRecipeSerializer<VeWoodCuttingRecipe> WOODCUTTING = register(VanillaExpansions.location("woodcutting"), new VeWoodCuttingRecipe.Serializer<>(VeWoodCuttingRecipe::new));
    
	private static <S extends IRecipeSerializer<? extends IRecipe<?>>> S register(ResourceLocation name, S recipe)
    {
        recipe.setRegistryName(name);
        RECIPES.add(recipe);
        return recipe;
    }
    
    @SubscribeEvent
    public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        RECIPES.forEach(item -> event.getRegistry().register(item));
        RECIPES.clear();
        
        VanillaExpansions.LOGGER.info("Recipe serializers registered.");
    }
}
