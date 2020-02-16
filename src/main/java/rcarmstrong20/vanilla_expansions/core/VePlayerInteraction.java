package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VePlayerInteraction
{
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickItem event)
	{
		BlockPos pos = event.getPos();
		
		String activeItemStackName = event.getItemStack().getDisplayName().getString();
		Block worldBlock = event.getWorld().getBlockState(pos).getBlock();
		String worldBlockName = worldBlock.getRegistryName().toString();
		String bottom_double_slab = worldBlockName + "_" + activeItemStackName;
		//System.out.print(bottom_double_slab);
		
		event.getWorld().setBlockState(pos, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(bottom_double_slab)).getDefaultState());
		event.setResult(Result.DENY);
		event.setCanceled(true);
		
		VanillaExpansions.LOGGER.info("right click event");
		
		if(worldBlock instanceof SlabBlock)
		{
			
		}
	}
}
