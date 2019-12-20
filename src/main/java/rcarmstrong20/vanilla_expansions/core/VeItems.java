package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Suppliers;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SoupItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.item.VeDrinkItem;
import rcarmstrong20.vanilla_expansions.item.VeGlassVialItem;
import rcarmstrong20.vanilla_expansions.item.VeSoupItem;
import rcarmstrong20.vanilla_expansions.item.VeVoidBucketItem;

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
	
	public static Item ruby = register(VanillaExpansions.location("ruby"), new Item(VE_ITEMS));
	/*
	public static Item ruby_axe = register(VanillaExpansions.location("ruby_axe"), new VeAxeItem(VeItemTier.RUBY, 5.0F, -2.8F, new Item.Properties().addToolType(ToolType.AXE, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_pickaxe = register(VanillaExpansions.location("ruby_pickaxe"), new VePickaxeItem(VeItemTier.RUBY, 1, new Item.Properties().addToolType(ToolType.PICKAXE, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_shovel = register(VanillaExpansions.location("ruby_shovel"), new VeShovelItem(VeItemTier.RUBY, 1.5F, new Item.Properties().addToolType(ToolType.SHOVEL, 4).group(VanillaExpansions.VE_GROUP)));
	public static Item ruby_sword = register(VanillaExpansions.location("ruby_sword"), new VeSwordItem(VeItemTier.RUBY, 3, VE_SINGLE_ITEMS));
	public static Item ruby_hoe = register(VanillaExpansions.location("ruby_hoe"), new HoeItem(VeItemTier.RUBY, 1, VE_SINGLE_ITEMS));
	public static Item ruby_helmet = register(VanillaExpansions.location("ruby_helmet"), new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item ruby_chestplate = register(VanillaExpansions.location("ruby_chestplate"), new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item ruby_leggings = register(VanillaExpansions.location("ruby_leggings"), new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item ruby_boots = register(VanillaExpansions.location("ruby_boots"), new ArmorItem(VeArmorMaterial.RUBY, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item emerald_axe = register(VanillaExpansions.location("emerald_axe"), new VeAxeItem(VeItemTier.EMERALD, 6.0F, -3.0F, new Item.Properties().addToolType(ToolType.AXE, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_pickaxe = register(VanillaExpansions.location("emerald_pickaxe"), new VePickaxeItem(VeItemTier.EMERALD, 1, new Item.Properties().addToolType(ToolType.PICKAXE, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_shovel = register(VanillaExpansions.location("emerald_shovel"), new VeShovelItem(VeItemTier.EMERALD, 2, new Item.Properties().addToolType(ToolType.SHOVEL, 2).group(VanillaExpansions.VE_GROUP)));
	public static Item emerald_sword = register(VanillaExpansions.location("emerald_sword"), new VeSwordItem(VeItemTier.EMERALD, 4, VE_SINGLE_ITEMS));
	public static Item emerald_hoe = register(VanillaExpansions.location("emerald_hoe"), new HoeItem(VeItemTier.EMERALD, 0, VE_SINGLE_ITEMS));
	public static Item emerald_helmet = register(VanillaExpansions.location("emerald_helmet"), new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item emerald_chestplate = register(VanillaExpansions.location("emerald_chestplate"), new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item emerald_leggings = register(VanillaExpansions.location("emerald_leggings"), new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item emerald_boots = register(VanillaExpansions.location("emerald_boots"), new ArmorItem(VeArmorMaterial.EMERALD, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item airite_ingot = register(VanillaExpansions.location("airite_ingot"), new Item(VE_ITEMS));
	public static Item air_diamond_helmet = register(VanillaExpansions.location("air_diamond_helmet"), new ArmorItem(VeArmorMaterial.AIR_DIAMOND, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item air_diamond_chestplate = register(VanillaExpansions.location("air_diamond_chestplate"), new ArmorItem(VeArmorMaterial.AIR_DIAMOND, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item air_diamond_leggings = register(VanillaExpansions.location("air_diamond_leggings"), new ArmorItem(VeArmorMaterial.AIR_DIAMOND, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item air_diamond_boots = register(VanillaExpansions.location("air_diamond_boots"), new ArmorItem(VeArmorMaterial.AIR_DIAMOND, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item air_iron_helmet = register(VanillaExpansions.location("air_iron_helmet"), new ArmorItem(VeArmorMaterial.AIR_IRON, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item air_iron_chestplate = register(VanillaExpansions.location("air_iron_chestplate"), new ArmorItem(VeArmorMaterial.AIR_IRON, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item air_iron_leggings = register(VanillaExpansions.location("air_iron_leggings"), new ArmorItem(VeArmorMaterial.AIR_IRON, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item air_iron_boots = register(VanillaExpansions.location("air_iron_boots"), new ArmorItem(VeArmorMaterial.AIR_IRON, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item air_gold_helmet = register(VanillaExpansions.location("air_gold_helmet"), new ArmorItem(VeArmorMaterial.AIR_GOLD, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item air_gold_chestplate = register(VanillaExpansions.location("air_gold_chestplate"), new ArmorItem(VeArmorMaterial.AIR_GOLD, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item air_gold_leggings = register(VanillaExpansions.location("air_gold_leggings"), new ArmorItem(VeArmorMaterial.AIR_GOLD, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item air_gold_boots = register(VanillaExpansions.location("air_gold_boots"), new ArmorItem(VeArmorMaterial.AIR_GOLD, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item air_ruby_helmet = register(VanillaExpansions.location("air_ruby_helmet"), new ArmorItem(VeArmorMaterial.AIR_RUBY, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item air_ruby_chestplate = register(VanillaExpansions.location("air_ruby_chestplate"), new ArmorItem(VeArmorMaterial.AIR_RUBY, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item air_ruby_leggings = register(VanillaExpansions.location("air_ruby_leggings"), new ArmorItem(VeArmorMaterial.AIR_RUBY, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item air_ruby_boots = register(VanillaExpansions.location("air_ruby_boots"), new ArmorItem(VeArmorMaterial.AIR_RUBY, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	public static Item air_emerald_helmet = register(VanillaExpansions.location("air_emerald_helmet"), new ArmorItem(VeArmorMaterial.AIR_EMERALD, EquipmentSlotType.HEAD, VE_SINGLE_ITEMS));
	public static Item air_emerald_chestplate = register(VanillaExpansions.location("air_emerald_chestplate"), new ArmorItem(VeArmorMaterial.AIR_EMERALD, EquipmentSlotType.CHEST, VE_SINGLE_ITEMS));
	public static Item air_emerald_leggings = register(VanillaExpansions.location("air_emerald_leggings"), new ArmorItem(VeArmorMaterial.AIR_EMERALD, EquipmentSlotType.LEGS, VE_SINGLE_ITEMS));
	public static Item air_emerald_boots = register(VanillaExpansions.location("air_emerald_boots"), new ArmorItem(VeArmorMaterial.AIR_EMERALD, EquipmentSlotType.FEET, VE_SINGLE_ITEMS));
	*/
	public static Item bok_choy_seeds = register(VanillaExpansions.location("bok_choy_seeds"), new BlockNamedItem(VeBlocks.bok_choy, VE_ITEMS));
	public static Item bok_choy = register(VanillaExpansions.location("bok_choy"), new Item(VE_ITEMS));
	public static Item garlic = register(VanillaExpansions.location("garlic"), new BlockNamedItem(VeBlocks.garlic, VE_ITEMS));
	public static Item green_onion = register(VanillaExpansions.location("green_onion"), new BlockNamedItem(VeBlocks.green_onions, VE_ITEMS));
	public static Item quinoa = register(VanillaExpansions.location("quinoa"), new BlockNamedItem(VeBlocks.quinoa, VE_ITEMS));
	public static Item ginger_root = register(VanillaExpansions.location("ginger_root"), new BlockNamedItem(VeBlocks.ginger, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.GINGER_ROOT)));
	public static Item blueberries = register(VanillaExpansions.location("blueberries"), new BlockNamedItem(VeBlocks.blueberry_bush, VE_ITEMS.food(VeFoods.BLUEBERRIES)));
	public static Item cranberries = register(VanillaExpansions.location("cranberries"), new BlockNamedItem(VeBlocks.cranberry_bush, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.CRANBERRIES)));
	public static Item cranberry_sauce = register(VanillaExpansions.location("cranberry_sauce"), new VeDrinkItem(new Item.Properties().maxStackSize(16).group(VanillaExpansions.VE_GROUP).food(VeFoods.CRANBERRY_SAUCE)));
	public static Item noodles = register(VanillaExpansions.location("noodles"), new Item(VE_ITEMS));
	public static Item cooked_noodles = register(VanillaExpansions.location("cooked_noodles"), new Item(VE_ITEMS));
	public static Item noodle_soup = register(VanillaExpansions.location("noodle_soup"), new VeSoupItem(new Item.Properties().maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.NOODLE_SOUP)));
	public static Item noodle_bowl = register(VanillaExpansions.location("noodle_bowl"), new Item(VE_ITEMS));
	public static Item chop_sticks = register(VanillaExpansions.location("chop_sticks"), new Item(VE_ITEMS));
	public static Item quinoa_cerceal = register(VanillaExpansions.location("quinoa_cerceal"), new SoupItem(new Item.Properties().maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.QUINOA_CERCEAL)));
	public static Item smoky_quartz = register(VanillaExpansions.location("smoky_quartz"), new Item(VE_ITEMS));
	public static Item void_bucket = register(VanillaExpansions.location("void_bucket"), new VeVoidBucketItem(Suppliers.ofInstance(VeFluids.VOID), new Item.Properties().maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.VOID_WATER_BUCKET)));
	public static Item frosting = register(VanillaExpansions.location("frosting"), new Item(VE_ITEMS));
	public static Item gingerbread = register(VanillaExpansions.location("gingerbread"), new Item(VE_ITEMS));
	public static Item orange_gumdrop = register(VanillaExpansions.location("orange_gumdrop"), new Item(VE_ITEMS));
	public static Item red_gumdrop = register(VanillaExpansions.location("red_gumdrop"), new Item(VE_ITEMS));
	public static Item caramel_apple = register(VanillaExpansions.location("caramel_apple"), new VeSoupItem(new Item.Properties().maxStackSize(1).group(VanillaExpansions.VE_GROUP).food(VeFoods.CARAMEL_APPLE)));
	public static Item caramel = register(VanillaExpansions.location("caramel"), new Item(VE_ITEMS));
	public static Item bread_crumbs = register(VanillaExpansions.location("bread_crumbs"), new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.BREAD_CRUMBS)));
	public static Item porkchop_bits = register(VanillaExpansions.location("porkchop_bits"), new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.PORKCHOP_BITS)));
	public static Item spruce_cone = register(VanillaExpansions.location("spruce_cone"), new Item(new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.SPRUCE_CONE)));
	public static Item forests_bounty = register(VanillaExpansions.location("forests_bounty"), new SoupItem(new Item.Properties().group(VanillaExpansions.VE_GROUP).maxStackSize(1).food(VeFoods.FORESTS_BOUNTY)));
	public static Item witchs_cradle_branch = register(VanillaExpansions.location("witchs_cradle_branch"), new BlockNamedItem(VeBlocks.witchs_cradle, new Item.Properties().group(VanillaExpansions.VE_GROUP).food(VeFoods.WITCHS_CRADLE_BRANCH)));
	public static Item witchs_cradle_soup = register(VanillaExpansions.location("witchs_cradle_soup"), new VeSoupItem(new Item.Properties().group(VanillaExpansions.VE_GROUP).maxStackSize(1).food(VeFoods.WITCHS_CRADLE_SOUP)));
	public static Item glass_vial = register(VanillaExpansions.location("glass_vial"), new VeGlassVialItem(VE_ITEMS));
	public static Item blood_vial = register(VanillaExpansions.location("blood_vial"), new VeDrinkItem(new Item.Properties().group(VanillaExpansions.VE_GROUP).maxStackSize(16).food(VeFoods.BLOOD_VIAL)));
	
	//Vanilla Replacement Items
	
	public static Item wheat_seeds = register(VanillaExpansions.vanillaLocation("wheat_seeds"), new BlockNamedItem(VeBlocks.wheat, new Item.Properties().group(ItemGroup.MATERIALS)));
	public static Item beetroot_seeds = register(VanillaExpansions.vanillaLocation("beetroot_seeds"), new BlockNamedItem(VeBlocks.beetroots, new Item.Properties().group(ItemGroup.MATERIALS)));
	public static Item nether_wart = register(VanillaExpansions.vanillaLocation("nether_wart"), new BlockNamedItem(VeBlocks.nether_wart, new Item.Properties().group(ItemGroup.MATERIALS)));
	public static Item carrot = register(VanillaExpansions.vanillaLocation("carrot"), new BlockNamedItem(VeBlocks.carrots, new Item.Properties().group(ItemGroup.FOOD)));
	public static Item potato = register(VanillaExpansions.vanillaLocation("potato"), new BlockNamedItem(VeBlocks.potatoes, new Item.Properties().group(ItemGroup.FOOD)));
	public static Item sweet_berries = register(VanillaExpansions.vanillaLocation("sweet_berries"), new BlockNamedItem(VeBlocks.sweet_berry_bush, new Item.Properties().group(ItemGroup.FOOD)));
	//public static Item rabbit_spawn_egg = register(VanillaExpansions.vanillaLocation("rabbit_spawn_egg"), new SpawnEggItem(VeEntityType.rabbit, 10051392, 7555121, (new Item.Properties()).group(ItemGroup.MISC)));
	
	/*
	 * Set the registry name the items and add them to the registry list.
	 */
	private static Item register(ResourceLocation name, Item item)
	{
		item.setRegistryName(name);
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
