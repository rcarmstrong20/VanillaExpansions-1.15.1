package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

/**
 *
 * @author Ryan
 *
 */
public class VeFluidTags
{
    public static final Tag<Fluid> VOID = new FluidTags.Wrapper(new ResourceLocation(VanillaExpansions.MOD_ID, "void"));
}
