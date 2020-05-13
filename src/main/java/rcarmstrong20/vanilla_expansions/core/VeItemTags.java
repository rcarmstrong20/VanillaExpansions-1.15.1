package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

public class VeItemTags
{
	public static final Tag<Item> PAINTINGS = new ItemTags.Wrapper(new ResourceLocation(VanillaExpansions.MOD_ID, "paintings"));
	public static final Tag<Item> FRAMES = new ItemTags.Wrapper(new ResourceLocation(VanillaExpansions.MOD_ID, "frames"));
}
