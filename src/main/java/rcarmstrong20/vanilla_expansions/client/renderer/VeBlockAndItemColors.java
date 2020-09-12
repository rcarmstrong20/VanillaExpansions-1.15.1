package rcarmstrong20.vanilla_expansions.client.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

/**
 *
 * @author Ryan
 *
 */
@OnlyIn(Dist.CLIENT)
public class VeBlockAndItemColors
{
    /**
     * Register the colour handlers.
     */
    public static void registerColorHandlers()
    {
        final BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        final ItemColors itemColors = Minecraft.getInstance().getItemColors();

        registerBlockColorHandlers(blockColors);
        registerItemColorHandlers(blockColors, itemColors);
    }

    private static void registerBlockColorHandlers(final BlockColors blockColors)
    {
        // Use the grass color of the biome or the default grass color
        final IBlockColor grassColorHandler = (state, blockAccess, pos, tintIndex) ->
        {
            if (tintIndex == 1)
            {
                if (blockAccess != null && pos != null)
                {
                    return BiomeColors.getGrassColor(blockAccess, pos);
                }
                return GrassColors.get(0.5D, 1.0D);
            }
            return -1;
        };

        final IBlockColor leavesColorHandler = (state, blockAccess, pos, tintIndex) ->
        {
            if (tintIndex == 1)
            {
                if (blockAccess != null && pos != null)
                {
                    return BiomeColors.getFoliageColor(blockAccess, pos);
                }
                return FoliageColors.getDefault();
            }
            return -1;
        };

        final IBlockColor waterColorHandler = (state, blockAccess, pos, tintIndex) ->
        {
            if (blockAccess != null && pos != null && tintIndex == 1)
            {
                // Get the water color from the current biome
                return BiomeColors.getWaterColor(blockAccess, pos);
            }
            return -1;
        };

        blockColors.register(grassColorHandler, VeBlocks.enderman_plush);
        blockColors.register(leavesColorHandler, VeBlocks.regigigas_pokedoll);
        blockColors.register(waterColorHandler, VeBlocks.stone_brick_planting_pot, VeBlocks.oak_planting_pot,
                VeBlocks.spruce_planting_pot, VeBlocks.birch_planting_pot, VeBlocks.jungle_planting_pot,
                VeBlocks.dark_oak_planting_pot, VeBlocks.acacia_planting_pot);
    }

    public static void registerItemColorHandlers(final BlockColors blockColors, final ItemColors itemColors)
    {
        // Use the Block's color for the ItemBlock
        final IItemColor itemBlockColorHandler = (stack, tintIndex) ->
        {
            final BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();

            return blockColors.getColor(state, null, null, tintIndex);
        };

        itemColors.register(itemBlockColorHandler, VeBlocks.enderman_plush, VeBlocks.regigigas_pokedoll);
    }
}