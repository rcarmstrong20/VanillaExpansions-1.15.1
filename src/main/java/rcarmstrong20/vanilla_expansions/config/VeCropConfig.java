package rcarmstrong20.vanilla_expansions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class VeCropConfig
{
	public static BooleanValue enableRightClickHarvesting;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
	{
		server.comment("Vanilla Expansions Crops Configuration");
		
		server.comment("Crop Harvest Configuration")
			  .push("crop_harvest");
		
		enableRightClickHarvesting = server.comment("If true crops can be harvested and re-planted simply by clicking the crops.")
										   .translation("ve.configCrop.enableRightClickHarvesting")
										   .worldRestart()
										   .define("enable_right_click_harvesting", true);
		server.pop();
	}
}
