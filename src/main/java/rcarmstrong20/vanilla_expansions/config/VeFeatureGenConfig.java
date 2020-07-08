package rcarmstrong20.vanilla_expansions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class VeFeatureGenConfig
{
	public static BooleanValue enableBlueberryBushSpawns;
	public static BooleanValue enableCranberryBushSpawns;
	public static BooleanValue enableWitchsCradleSpawns;
	
	public static BooleanValue enableBigPurpleMushroomSpawns;
	
	public static BooleanValue enableVoidLakeSpawns;
	
	public static BooleanValue enableCabinSpawns;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
	{
		server.comment("Vanilla Expansions Feature Configuration");
		
		server.comment("Bush Configuration")
			  .push("ve_bush_config");
		
		server.comment("Bush Spawn Configuration")
			  .push("bush_spawn");
		
		enableBlueberryBushSpawns = server.comment(compileComment("blueberry bushes"))
										  .translation("ve.configBushes.enableBlueberryBushSpawns")
										  .worldRestart()
										  .define("enable_blueberry_bush_spawns", true);
		
		enableCranberryBushSpawns = server.comment(compileComment("cranberry bushes"))
										 .translation("ve.configBushes.enableCranberryBushSpawns")
										 .worldRestart()
										 .define("enable_cranberry_bush_spawns", true);
		
		enableWitchsCradleSpawns = server.comment(compileComment("witch's cradles"))
										 .translation("ve.configBushes.enableWitchsCradleSpawns")
										 .worldRestart()
										 .define("enable_witchs_cradle_spawns", true);
		
		server.pop(2);
		
		server.comment("Vanilla Expansions Mushroom Configuration")
			   .push("ve_mushroom_config");
		
		server.comment("Mushroom Spawn Configuration")
			  .push("mushroom_spawn");
		
		enableBigPurpleMushroomSpawns = server.comment(compileComment("big purple mushrooms"))
											  .translation("ve.configMushroom.enableBigPurpleMushroomSpawns")
											  .worldRestart()
											  .define("enable_big_purple_mushroom_spawns", true);
		server.pop(2);
		
		server.comment("Vanilla Expansions Lake Configuration")
			  .push("ve_lake_config");
		
		server.comment("Lake Spawn Configuration")
			  .push("lake_spawn");
		
		enableVoidLakeSpawns = server.comment(compileComment("void lakes"))
									 .translation("ve.configLake.enableVoidLakeSpawns")
									 .worldRestart()
									 .define("enable_void_lake_spawns", true);
		server.pop(2);
		
		server.comment("Vanilla Expansions Structure Configuration")
			  .push("ve_structure_config");
		
		server.comment("Structure Spawn Configuration")
			  .push("structure_spawn");
		
		enableCabinSpawns = server.comment(compileComment("cabins"))
								  .translation("ve.configStructure.enableCabinSpawns")
								  .worldRestart()
								  .define("enable_cabin_spawns", true);
		
		server.pop(2);
	}
	
	private static String compileComment(String name)
	{
		return "If true " + name + " will spawn in the world.";
	}
}
