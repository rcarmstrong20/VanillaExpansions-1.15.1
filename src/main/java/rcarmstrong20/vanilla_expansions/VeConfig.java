package rcarmstrong20.vanilla_expansions;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Bus.MOD)
public class VeConfig
{
	public static class Common
	{
		public static BooleanValue enableRightClickHarvesting;
		public static ConfigValue<List<String>> rightClickHarvestBlackList;
		
		public static ConfigValue<List<String>> blueberryBushSpawnBiomes;
		public static ConfigValue<List<String>> cranberryBushSpawnBiomes;
		public static ConfigValue<List<String>> witchsCradleSpawnBiomes;
		
		public static ConfigValue<List<String>> bigPurpleMushroomSpawnBiomes;
		
		public static ConfigValue<List<String>> voidLakeSpawnBiomes;
		
		public static ConfigValue<List<String>> taigaCabinSpawnBiomes;
		public static ConfigValue<List<String>> birchForestCabinSpawnBiomes;
		public static ConfigValue<List<String>> forestCabinSpawnBiomes;
		
		public static ConfigValue<List<String>> netherSmokyQuartzOreSpawnBiomes;
		public static ConfigValue<List<String>> netherRubyOreSpawnBiomes;
		
		public static IntValue netherSmokyQuartzOreVeinSize;
		public static IntValue netherRubyOreVeinSize;
		
		private static List<String> netherBiomes = Arrays.asList(Common.getMinecraftPrefix("nether"));
		
		private static List<String> swampBiomes = Arrays.asList(Common.getMinecraftPrefix("swamp"),
																Common.getMinecraftPrefix("swamp_hills"));
		
		private static List<String> darkForestBiomes = Arrays.asList(Common.getMinecraftPrefix("dark_forest"),
																	 Common.getMinecraftPrefix("dark_forest_hills"));
		
		private static List<String> endCityBiomes = Arrays.asList(Common.getMinecraftPrefix("end_barrens"),
																  Common.getMinecraftPrefix("end_highlands"),
																  Common.getMinecraftPrefix("end_midlands"),
																  Common.getMinecraftPrefix("small_end_islands"));
		
		private static List<String> forestBiomes = Arrays.asList(Common.getMinecraftPrefix("forest"),
																 Common.getMinecraftPrefix("birch_forest"),
																 Common.getMinecraftPrefix("birch_forest_hills"),
																 Common.getMinecraftPrefix("tall_birch_forest"),
																 Common.getMinecraftPrefix("flower_forest"));
		
		private static List<String> taigaCabinBiomes = Arrays.asList(Common.getMinecraftPrefix("taiga"),
																	 Common.getMinecraftPrefix("taiga_hills"),
																	 Common.getMinecraftPrefix("taiga_mountains"),
																	 Common.getMinecraftPrefix("giant_spruce_taiga"),
																	 Common.getMinecraftPrefix("giant_spruce_taiga_hills"),
																	 Common.getMinecraftPrefix("giant_tree_taiga"),
																	 Common.getMinecraftPrefix("giant_tree_taiga_hills"),
																	 Common.getMinecraftPrefix("snowy_taiga"),
																	 Common.getMinecraftPrefix("snowy_taiga_hills"),
																	 Common.getMinecraftPrefix("snowy_taiga_mountains"));
		
		private static List<String> forestCabinBiomes = Arrays.asList(Common.getMinecraftPrefix("forest"));
		
		private static List<String> birchForestCabinBiomes = Arrays.asList(Common.getMinecraftPrefix("birch_forest"),
																		   Common.getMinecraftPrefix("birch_forest_hills"),
																		   Common.getMinecraftPrefix("tall_birch_forest"),
																		   Common.getMinecraftPrefix("tall_birch_hills"));
		
		public Common(ForgeConfigSpec.Builder builder)
		{
			builder.comment("Vanilla Expansions Crops Configuration")
				   .push("ve_crop_config");
			
			builder.comment("Crop Harvest Configuration")
				   .push("crop_harvest");
			
			enableRightClickHarvesting = builder.comment("If true crops can be harvested and re-planted simply by clicking the crops.")
												.translation("ve.configCrop.enableRightClickHarvesting")
												.worldRestart()
												.define("enable_right_click_harvesting", true);
			
			rightClickHarvestBlackList = builder.comment("This is a black list for disabling specific crops for the right-click harvest feature. Example: 've:bok_choy', 'minecraft:wheat'")
												.translation("ve.configCrop.rightClickHarvestBlackList")
												.worldRestart()
												.define("right_click_harvest_black_list", Arrays.asList());
			builder.pop(2);
			
			builder.comment("Vanilla Expansions Bushes Configuration")
			   	   .push("ve_bushes_config");
			
			builder.comment("Bush Spawn Configuration")
				   .push("bush_spawn");
			
			blueberryBushSpawnBiomes = builder.comment("A white list of biomes blueberry bushes can spawn in.")
											  .translation("ve.configBushes.blueberryBushSpawnBiomes")
											  .worldRestart()
											  .define("blueberry_bush_spawn_biomes", forestBiomes);
			
			cranberryBushSpawnBiomes = builder.comment("A white list of biomes cranberry bushes can spawn in.")
											  .translation("ve.configBushes.cranberryBushSpawnBiomes")
											  .worldRestart()
											  .define("cranberry_bush_spawn_biomes", forestBiomes);
			
			witchsCradleSpawnBiomes = builder.comment("A white list of biomes witch's cradle can spawn in.")
											 .translation("ve.configBushes.witchsCradleSpawnBiomes")
											 .worldRestart()
											 .define("witchs_cradle_spawn_biomes", swampBiomes);
			builder.pop(2);
			
			builder.comment("Vanilla Expansions Mushroom Configuration")
				   .push("ve_mushroom_config");
			
			builder.comment("Mushroom Spawn Configuration")
				   .push("mushroom_spawn");
			
			bigPurpleMushroomSpawnBiomes = builder.comment("A white list of biomes big purple mushrooms can spawn in.")
												  .translation("ve.configMushroom.bigPurpleMushroomSpawnBiomes")
												  .worldRestart()
												  .define("big_purple_mushroom_spawn_biomes", darkForestBiomes);
			builder.pop(2);
			
			builder.comment("Vanilla Expansions Lake Configuration")
				   .push("ve_lake_config");
			
			builder.comment("Lake Spawn Configuration")
			   	   .push("lake_spawn");
			
			voidLakeSpawnBiomes = builder.comment("A white list of biomes void lakes can spawn in.")
										 .translation("ve.configLake.voidLakeSpawnBiomes")
										 .worldRestart()
										 .define("void_lake_spawn_biomes", endCityBiomes);
			builder.pop(2);
			
			builder.comment("Vanilla Expansions Structure Configuration")
				   .push("ve_structure_config");
			
			builder.comment("Structure Spawn Configuration")
				   .push("structure_spawn");
			
			taigaCabinSpawnBiomes = builder.comment("A white list of biomes taiga cabins can spawn in.")
										   .translation("ve.configStructure.taigaCabinSpawnBiomes")
										   .worldRestart()
										   .define("taiga_cabin_spawn_biomes", taigaCabinBiomes);
			
			birchForestCabinSpawnBiomes = builder.comment("A white list of biomes birch forest cabins can spawn in.")
												 .translation("ve.configStructure.birchForestCabinSpawnBiomes")
												 .worldRestart()
												 .define("birch_forest_cabin_spawn_biomes", birchForestCabinBiomes);
			
			forestCabinSpawnBiomes = builder.comment("A white list of biomes forest cabins can spawn in.")
											.translation("ve.configStructure.forestCabinSpawnBiomes")
											.worldRestart()
											.define("forest_cabin_spawn_biomes", forestCabinBiomes);
			
			builder.pop(2);
			
			builder.comment("Vanilla Expansions Ores Configuration")
			   	   .push("ve_ore_config");
			
			builder.comment("Ore Spawn Configuration")
				   .push("ore_spawn");
			
			netherSmokyQuartzOreSpawnBiomes = builder.comment("A white list of biomes nether smoky quartz ore can spawn in.")
													 .translation("ve.configNetherOre.netherSmokyQuartzOreSpawnBiomes")
													 .worldRestart()
													 .define("nether_smoky_quartz_ore_spawn_biomes", netherBiomes);
			
			netherRubyOreSpawnBiomes = builder.comment("A white list of biomes nether ruby ore can spawn in.")
											  .translation("ve.configNetherOre.netherRubyOreSpawnBiomes")
											  .worldRestart()
											  .define("nether_ruby_ore_spawn_biomes", netherBiomes);
			
			builder.pop();
			
			builder.comment("Ore Vein Size Configuration")
				   .push("ore_vein_size");
			
			netherSmokyQuartzOreVeinSize = builder.comment("This number determines the vein size for the nether smoky quartz ore. It has a default value of 14.")
												  .translation("ve.configNetherOre.netherSmokyQuartzOreVeinSize")
												  .worldRestart()
												  .defineInRange("nether_smoky_quartz_ore_vein_size", 14, 0, 17);
			
			netherRubyOreVeinSize = builder.comment("This number determines the vein size for the nether ruby ore. It has a default value of 8.")
					  					   .translation("ve.configNetherOre.netherRubyOreVeinSize")
					  					   .worldRestart()
					  					   .defineInRange("nether_ruby_ore_vein_size", 8, 0, 17);
			builder.pop(2);
		}
		
		private static String getMinecraftPrefix(String name)
		{
			return "minecraft:" + name;
		}
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	static
	{
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event)
	{
		
	}
	
	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading event)
	{
		
	}
}
