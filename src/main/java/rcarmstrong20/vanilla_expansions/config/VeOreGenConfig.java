package rcarmstrong20.vanilla_expansions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class VeOreGenConfig
{
	public static BooleanValue enableNetherSmokyQuartzOreSpawns;
	public static BooleanValue enableNetherRubyOreSpawns;
	
	public static IntValue netherSmokyQuartzOreVeinSize;
	public static IntValue netherRubyOreVeinSize;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
	{
		server.comment("Vanilla Expansions Ores Configuration");
		
		server.comment("Ore Spawn Configuration")
			  .push("ore_spawn");
		
		enableNetherSmokyQuartzOreSpawns = server.comment(compileBooleanSpawnComment("nether smoky quartz ores"))
												 .translation("ve.configNetherOre.enableNetherSmokyQuartzOreSpawns")
												 .worldRestart()
												 .define("enable_nether_smoky_quartz_ore_spawns", true);
		
		enableNetherRubyOreSpawns = server.comment(compileBooleanSpawnComment("nether ruby ores"))
										  .translation("ve.configNetherOre.enableNetherRubyOreSpawns")
										  .worldRestart()
										  .define("enable_nether_ruby_ore_spawns", true);
		server.pop();
		
		server.comment("Ore Vein Size Configuration")
			  .push("ore_vein_size");
		
		netherSmokyQuartzOreVeinSize = server.comment(compileVeinSizeComment("nether smoky quartz ore", "14"))
											 .translation("ve.configNetherOre.netherSmokyQuartzOreVeinSize")
											 .worldRestart()
											 .defineInRange("nether_smoky_quartz_ore_vein_size", 14, 0, 17);
	
		netherRubyOreVeinSize = server.comment(compileVeinSizeComment("nether ruby ore", "8"))
									  .translation("ve.configNetherOre.netherRubyOreVeinSize")
									  .worldRestart()
									  .defineInRange("nether_ruby_ore_vein_size", 8, 0, 17);
		server.pop();
	}
	
	private static String compileBooleanSpawnComment(String name)
	{
		return "If true " + name + " Will spawn in the world.";
	}
	
	private static String compileVeinSizeComment(String name, String defaultValue)
	{
		return "Sets vein size for " + name + ". (Default: " + defaultValue + ")";
	}
}
