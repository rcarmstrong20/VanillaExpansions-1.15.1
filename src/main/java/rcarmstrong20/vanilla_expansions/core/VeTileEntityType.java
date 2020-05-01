package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.tile_entity.VeColoredCampfireTileEntity;
import rcarmstrong20.vanilla_expansions.tile_entity.VeDoubleSlabTileEntity;
import rcarmstrong20.vanilla_expansions.tile_entity.VeFrameTileEntity;

/**
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeTileEntityType
{
	public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
	
	public static TileEntityType<VeColoredCampfireTileEntity> colored_campfire = register(VanillaExpansions.MOD_ID, "colored_campfire", TileEntityType.Builder.create(VeColoredCampfireTileEntity::new, VeBlocks.white_campfire, VeBlocks.orange_campfire, VeBlocks.magenta_campfire, VeBlocks.light_blue_campfire, VeBlocks.yellow_campfire, VeBlocks.lime_campfire, VeBlocks.pink_campfire, VeBlocks.gray_campfire, VeBlocks.light_gray_campfire, VeBlocks.cyan_campfire, VeBlocks.purple_campfire, VeBlocks.blue_campfire, VeBlocks.brown_campfire, VeBlocks.green_campfire, VeBlocks.red_campfire, VeBlocks.black_campfire));
	public static TileEntityType<VeDoubleSlabTileEntity> double_slab = register(VanillaExpansions.MOD_ID, "double_slab", TileEntityType.Builder.create(VeDoubleSlabTileEntity::new, VeBlocks.double_slab));
	public static TileEntityType<VeFrameTileEntity> frame = register(VanillaExpansions.MOD_ID, "frame", TileEntityType.Builder.create(VeFrameTileEntity::new, VeBlocks.oak_frame, VeBlocks.spruce_frame, VeBlocks.birch_frame, VeBlocks.jungle_frame, VeBlocks.acacia_frame, VeBlocks.dark_oak_frame));
	
	@SuppressWarnings("unused")
	private static <T extends TileEntity> TileEntityType<T> register(String name, TileEntityType.Builder<T> builder)
	{
		return register(new ResourceLocation(name), builder);
	}
	
	private static <T extends TileEntity> TileEntityType<T> register(String id, String name, TileEntityType.Builder<T> builder)
	{
		return register(new ResourceLocation(id, name), builder);
	}
	
	private static <T extends TileEntity> TileEntityType<T> register(ResourceLocation location, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null);
        type.setRegistryName(location);
        TILE_ENTITY_TYPES.add(type);
        return type;
    }
	
	@SubscribeEvent
    public static void registerTypes(final RegistryEvent.Register<TileEntityType<?>> event)
    {
        TILE_ENTITY_TYPES.forEach(type -> event.getRegistry().register(type));
        TILE_ENTITY_TYPES.clear();
		
        VanillaExpansions.LOGGER.info("Tile entity types registered.");
    }
}
