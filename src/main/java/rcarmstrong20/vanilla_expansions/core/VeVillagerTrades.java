package rcarmstrong20.vanilla_expansions.core;

import com.google.common.collect.ImmutableMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class VeVillagerTrades
{
	public static final Int2ObjectMap<ITrade[]> LUMBERJACK_TRADES = getAsIntMap(ImmutableMap.of(1, new VillagerTrades.ITrade[] {itemForEmeraldTrade(new ItemStack(Items.APPLE), 16, 2), itemForEmeraldTrade(new ItemStack(VeItems.spruce_cone), 10, 2)},
			 																				 2, new VillagerTrades.ITrade[] {new BasicTrade(2, new ItemStack(Items.GOLDEN_AXE), 6, 10, 0.02F), new BasicTrade(3, new ItemStack(Items.IRON_AXE), 3, 15, 0.02F)},
			 																				 3, new VillagerTrades.ITrade[] {convertItemForEmeraldTrade(new ItemStack(Blocks.OAK_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15), convertItemForEmeraldTrade(new ItemStack(Blocks.BIRCH_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15), convertItemForEmeraldTrade(new ItemStack(Blocks.JUNGLE_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15), convertItemForEmeraldTrade(new ItemStack(Blocks.ACACIA_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15), convertItemForEmeraldTrade(new ItemStack(Blocks.DARK_OAK_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15)},
			 																				 4, new VillagerTrades.ITrade[] {new BasicTrade(1, new ItemStack(Blocks.OAK_SAPLING, 3), 5, 20, 0.05F), new BasicTrade(1, new ItemStack(Blocks.BIRCH_SAPLING, 3), 5, 20, 0.05F), new BasicTrade(1, new ItemStack(Blocks.JUNGLE_SAPLING, 5), 5, 20, 0.05F), new BasicTrade(1, new ItemStack(Blocks.ACACIA_SAPLING, 5), 5, 20, 0.05F), new BasicTrade(1, new ItemStack(Blocks.DARK_OAK_SAPLING, 10), 5, 20, 0.05F)},
			 																				 5, new VillagerTrades.ITrade[] {masterTrade(1, new ItemStack(Blocks.NOTE_BLOCK), 10, 0.05F), masterTrade(5, new ItemStack(Blocks.JUKEBOX), 5, 0.05F)}));
	
	private static Int2ObjectMap<VillagerTrades.ITrade[]> getAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> trades_map)
	{
		return new Int2ObjectOpenHashMap<>(trades_map);
	}
	
	private static BasicTrade itemForEmeraldTrade(ItemStack price, int maxTrades, int xp)
	{
		return new BasicTrade(price, new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
	}
	
	private static BasicTrade convertItemForEmeraldTrade(ItemStack price, ItemStack forSale, int maxTrades, int xp)
	{
		return new BasicTrade(price, new ItemStack(Items.EMERALD), forSale, maxTrades, xp, 0.05F);
	}
	
	private static BasicTrade masterTrade(int emeralds, ItemStack forSale, int maxTrades, float priceMult)
	{
		return new BasicTrade(emeralds, forSale, maxTrades, 0, priceMult);
	}
}