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
import rcarmstrong20.vanilla_expansions.tile_entity.VeCampfireTileEntity;

/**
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeTileEntityType
{
	public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
	
	public static final TileEntityType<VeCampfireTileEntity> campfire = buildType(VanillaExpansions.location("campfire"), TileEntityType.Builder.create(VeCampfireTileEntity::new, VeBlocks.white_campfire, VeBlocks.orange_campfire, VeBlocks.magenta_campfire, VeBlocks.light_blue_campfire, VeBlocks.yellow_campfire, VeBlocks.lime_campfire, VeBlocks.pink_campfire, VeBlocks.gray_campfire, VeBlocks.light_gray_campfire, VeBlocks.cyan_campfire, VeBlocks.purple_campfire, VeBlocks.blue_campfire, VeBlocks.brown_campfire, VeBlocks.green_campfire, VeBlocks.red_campfire, VeBlocks.black_campfire));
	
	private static <T extends TileEntity> TileEntityType<T> buildType(ResourceLocation name, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null);
        type.setRegistryName(name);
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
