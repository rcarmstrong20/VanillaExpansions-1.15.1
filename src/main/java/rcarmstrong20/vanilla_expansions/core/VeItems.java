package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Suppliers;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.enums.VeArmorMaterial;
import rcarmstrong20.vanilla_expansions.enums.VeItemTier;
import rcarmstrong20.vanilla_expansions.item.VeAxeItem;
import rcarmstrong20.vanilla_expansions.item.VeDrinkItem;
import rcarmstrong20.vanilla_expansions.item.VeGlassVialItem;
import rcarmstrong20.vanilla_expansions.item.VePickaxeItem;
import rcarmstrong20.vanilla_expansions.item.VeShovelItem;
import rcarmstrong20.vanilla_expansions.item.VeSoupItem;
import rcarmstrong20.vanilla_expansions.item.VeSwordItem;

/**
 * Author: rcarmstrong20
 */

@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeItems
{
	//Item Property Presets
	
	public static final Item.Properties VE_ITEMS = new Item.Properties().group(VanillaExpansions.VE_GROUP);
	public static final Item.Properties VE_SINGLE_ITEMS = new Item.Properties().maxStackSize(1).group(VanillaExpansions.VE_GROUP);
	private static final List<Item> ITEMS = new ArrayList<>();
	
	//Vanilla Expansions Items
	
	public static Item ruby = register("ruby", new Item(VE_ITEMS));
	public static Item ruby_axe = register("ruby_axe", new VeAxeItem(VeItemTier.RUBY, 5.0F, -2.8F, new Item.Properties().addToolType(ToolType.AXE, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_pickaxe = register("ruby_pickaxe", new VePickaxeItem(VeItemTier.RUBY, 1, new Item.Properties().addToolType(ToolType.PICKAXE, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_shovel = register("ruby_shovel", new VeShovelItem(VeItemTier.RUBY, 1.5F, new Item.Properties().addToolType(ToolType.SHOVEL, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_sword = register("ruby_sword", new VeSwordItem(VeItemTier.RUBY, 3, VE_SINGLE_ITEMS));
	public static Item ruby_hoe = register("ruby_hoe", new HoeItem(VeItemTier.RUBY, 1, VE_SINGLE_ITEMS));
	public static Item ruby_helmet = register("ruby_helmet", new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item ruby_chestplate = register("ruby_chestplate", new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item ruby_leggings = register("ruby_leggings", new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item ruby_boots = register("ruby_boots", new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item ruby_horse_armor = register("ruby_horse_armor", 12, "ruby");
	public static Item emerald_axe = register("emerald_axe", new VeAxeItem(VeItemTier.EMERALD, 6.0F, -3.0F, new Item.Properties().addToolType(ToolType.AXE, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_pickaxe = register("emerald_pickaxe", new VePickaxeItem(VeItemTier.EMERALD, 1, new Item.Properties().addToolType(ToolType.PICKAXE, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_shovel = register("emerald_shovel", new VeShovelItem(VeItemTier.EMERALD, 2, new Item.Properties().addToolType(ToolType.SHOVEL, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_sword = register("emerald_sword", new VeSwordItem(VeItemTier.EMERALD, 4, VE_SINGLE_ITEMS));
	public static Item emerald_hoe = register("emerald_hoe", new HoeItem(VeItemTier.EMERALD, 0, VE_SINGLE_ITEMS));
	public static Item emerald_helmet = register("emerald_helmet", new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item emerald_chestplate = register("emerald_chestplate", new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item emerald_leggings = register("emerald_leggings", new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item emerald_boots = register("emerald_boots", new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item emerald_horse_armor = register("emerald_horse_armor", 8, "emerald");
	public static Item bok_choy_seeds = register("bok_choy_seeds", new BlockNamedItem(VeBlocks.bok_choy, VE_ITEMS));
	public static Item bok_choy = register("bok_choy", new Item(VE_ITEMS));
	public static Item garlic = register("garlic", new BlockNamedItem(VeBlocks.garlic, VE_ITEMS));
	public static Item green_onion = register("green_onion", new BlockNamedItem(VeBlocks.green_onions, VE_ITEMS));
	public static Item quinoa = register("quinoa", new BlockNamedItem(VeBlocks.quinoa, VE_ITEMS));
	public static Item blueberries = register("blueberries", new BlockNamedItem(VeBlocks.blueberry_bush, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.BLUEBERRIES)));
	public static Item cranberries = register("cranberries", new BlockNamedItem(VeBlocks.cranberry_bush, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.CRANBERRIES)));
	public static Item cranberry_sauce = register("cranberry_sauce", new VeDrinkItem(new Item.Properties().containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(VanillaExpansions.VE_GROUP).food(VeFoods.CRANBERRY_SAUCE)));
	public static Item noodles = register("noodles", new Item(VE_ITEMS));
	public static Item cooked_noodles = register("cooked_noodles", new Item(VE_ITEMS));
	public static Item noodle_soup = register("noodle_soup", new VeSoupItem(new Item.Properties().containerItem(Items.BOWL).maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.NOODLE_SOUP)));
	public static Item noodle_bowl = register("noodle_bowl", new Item(VE_ITEMS));
	public static Item quinoa_cerceal = register("quinoa_cerceal", new SoupItem(new Item.Properties().maxStackSize(1).containerItem(Items.BOWL).group(VanillaExpansions.VE_GROUP).food(VeFoods.QUINOA_CERCEAL)));
	public static Item smoky_quartz = register("smoky_quartz", new Item(VE_ITEMS));
	public static Item void_bucket = register("void_bucket", new BucketItem(Suppliers.ofInstance(VeFluids.VOID), new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.VOID_BUCKET)));
	public static Item caramel_apple = register("caramel_apple", new VeSoupItem(new Item.Properties().maxStackSize(1).containerItem(Items.STICK).group(VanillaExpansions.VE_GROUP).food(VeFoods.CARAMEL_APPLE)));
	public static Item caramel = register("caramel", new Item(VE_ITEMS));
	public static Item spruce_cone = register("spruce_cone", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.SPRUCE_CONE)));
	public static Item forests_bounty = register("forests_bounty", new SoupItem(new Item.Properties().containerItem(Items.BOWL).group(VanillaExpansions.VE_GROUP).maxStackSize(1).food(VeFoods.FORESTS_BOUNTY)));
	public static Item witchs_cradle_branch = register("witchs_cradle_branch", new BlockNamedItem(VeBlocks.witchs_cradle, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.WITCHS_CRADLE_BRANCH)));
	public static Item witchs_cradle_soup = register("witchs_cradle_soup", new VeSoupItem(new Item.Properties().containerItem(Items.BOWL).group(VanillaExpansions.VE_GROUP).maxStackSize(1).food(VeFoods.WITCHS_CRADLE_SOUP)));
	public static Item glass_vial = register("glass_vial", new VeGlassVialItem(VE_ITEMS));
	public static Item blood_vial = register("blood_vial", new VeDrinkItem(new Item.Properties().containerItem(VeItems.glass_vial).group(VanillaExpansions.VE_GROUP).maxStackSize(16).food(VeFoods.BLOOD_VIAL)));
	public static Item fire_painting = register("fire_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item water_painting = register("water_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item earth_painting = register("earth_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item wind_painting = register("wind_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item kebab_painting = register("kebab_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item alban_painting = register("alban_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item aztec_painting = register("aztec_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item aztec_painting2 = register("aztec_painting_two", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item bomb_painting = register("bomb_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item plant_painting = register("plant_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item wasteland_painting = register("wasteland_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item starry_night_painting = register("starry_night_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item the_scream_painting = register("the_scream_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item nether_wastes_painting = register("nether_wastes_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item wanderer_painting = register("wanderer_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item wanderer_painting_top = register("wanderer_painting_top", new Item(new Item.Properties()));
	public static Item wanderer_painting_bottom = register("wanderer_painting_bottom", new Item(new Item.Properties()));
	public static Item graham_painting = register("graham_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item graham_painting_top = register("graham_painting_top", new Item(new Item.Properties()));
	public static Item graham_painting_bottom = register("graham_painting_bottom", new Item(new Item.Properties()));
	public static Item courbet_painting = register("courbet_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item courbet_painting_right = register("courbet_painting_right", new Item(new Item.Properties()));
	public static Item courbet_painting_left = register("courbet_painting_left", new Item(new Item.Properties()));
	public static Item creebet_painting = register("creebet_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item creebet_painting_right = register("creebet_painting_right", new Item(new Item.Properties()));
	public static Item creebet_painting_left = register("creebet_painting_left", new Item(new Item.Properties()));
	public static Item pool_painting = register("pool_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item pool_painting_right = register("pool_painting_right", new Item(new Item.Properties()));
	public static Item pool_painting_left = register("pool_painting_left", new Item(new Item.Properties()));
	public static Item sea_painting = register("sea_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item sea_painting_right = register("sea_painting_right", new Item(new Item.Properties()));
	public static Item sea_painting_left = register("sea_painting_left", new Item(new Item.Properties()));
	public static Item sunset_painting = register("sunset_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item sunset_painting_right = register("sunset_painting_right", new Item(new Item.Properties()));
	public static Item sunset_painting_left = register("sunset_painting_left", new Item(new Item.Properties()));
	public static Item wither_painting = register("wither_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item wither_painting_top_right = register("wither_painting_top_right", new Item(new Item.Properties()));
	public static Item wither_painting_top_left = register("wither_painting_top_left", new Item(new Item.Properties()));
	public static Item wither_painting_bottom_right = register("wither_painting_bottom_right", new Item(new Item.Properties()));
	public static Item wither_painting_bottom_left = register("wither_painting_bottom_left", new Item(new Item.Properties()));
	public static Item bust_painting = register("bust_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item bust_painting_top_right = register("bust_painting_top_right", new Item(new Item.Properties()));
	public static Item bust_painting_top_left = register("bust_painting_top_left", new Item(new Item.Properties()));
	public static Item bust_painting_bottom_right = register("bust_painting_bottom_right", new Item(new Item.Properties()));
	public static Item bust_painting_bottom_left = register("bust_painting_bottom_left", new Item(new Item.Properties()));
	public static Item match_painting = register("match_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item match_painting_top_right = register("match_painting_top_right", new Item(new Item.Properties()));
	public static Item match_painting_top_left = register("match_painting_top_left", new Item(new Item.Properties()));
	public static Item match_painting_bottom_right = register("match_painting_bottom_right", new Item(new Item.Properties()));
	public static Item match_painting_bottom_left = register("match_painting_bottom_left", new Item(new Item.Properties()));
	public static Item skull_and_roses_painting = register("skull_and_roses_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item skull_and_roses_painting_top_right = register("skull_and_roses_painting_top_right", new Item(new Item.Properties()));
	public static Item skull_and_roses_painting_top_left = register("skull_and_roses_painting_top_left", new Item(new Item.Properties()));
	public static Item skull_and_roses_painting_bottom_right = register("skull_and_roses_painting_bottom_right", new Item(new Item.Properties()));
	public static Item skull_and_roses_painting_bottom_left = register("skull_and_roses_painting_bottom_left", new Item(new Item.Properties()));
	public static Item stage_painting = register("stage_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item stage_painting_top_right = register("stage_painting_top_right", new Item(new Item.Properties()));
	public static Item stage_painting_top_left = register("stage_painting_top_left", new Item(new Item.Properties()));
	public static Item stage_painting_bottom_right = register("stage_painting_bottom_right", new Item(new Item.Properties()));
	public static Item stage_painting_bottom_left = register("stage_painting_bottom_left", new Item(new Item.Properties()));
	public static Item void_painting = register("void_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	public static Item void_painting_top_right = register("void_painting_top_right", new Item(new Item.Properties()));
	public static Item void_painting_top_left = register("void_painting_top_left", new Item(new Item.Properties()));
	public static Item void_painting_bottom_right = register("void_painting_bottom_right", new Item(new Item.Properties()));
	public static Item void_painting_bottom_left = register("void_painting_bottom_left", new Item(new Item.Properties()));
	
	public static Item fighters_painting = register("fighters_painting", new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP)));
	
	public static Item fighters_painting_top_right = register("fighters_painting_top_right", new Item(new Item.Properties()));
	public static Item fighters_painting_top_right_middle = register("fighters_painting_top_right_middle", new Item(new Item.Properties()));
	public static Item fighters_painting_top_left_middle = register("fighters_painting_top_left_middle", new Item(new Item.Properties()));
	public static Item fighters_painting_top_left = register("fighters_painting_top_left", new Item(new Item.Properties()));
	public static Item fighters_painting_bottom_right = register("fighters_painting_bottom_right", new Item(new Item.Properties()));
	public static Item fighters_painting_bottom_right_middle = register("fighters_painting_bottom_right_middle", new Item(new Item.Properties()));
	public static Item fighters_painting_bottom_left_middle = register("fighters_painting_bottom_left_middle", new Item(new Item.Properties()));
	public static Item fighters_painting_bottom_left = register("fighters_painting_bottom_left", new Item(new Item.Properties()));
	
	private static Item register(String name, int strength, String armorMaterial)
	{
		Item item = new HorseArmorItem(strength, new ResourceLocation(VanillaExpansions.MOD_ID, "textures/entity/horse/armor/horse_armor_" + armorMaterial + ".png"), VE_SINGLE_ITEMS);
		item.setRegistryName(VanillaExpansions.MOD_ID, name);
		ITEMS.add(item);
		return item;
	}
	
	/*
	 * Set the registry name the items and add them to the registry list.
	 */
	private static Item register(String name, Item item)
	{
		item.setRegistryName(VanillaExpansions.MOD_ID, name);
		ITEMS.add(item);
		return item;
	}
	
	/*
	 * Register the Items to the game
	 */
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event)
	{
		ITEMS.forEach(item -> event.getRegistry().register(item));
		ITEMS.clear();
		
		VanillaExpansions.LOGGER.info("Items registered.");
	}
}
