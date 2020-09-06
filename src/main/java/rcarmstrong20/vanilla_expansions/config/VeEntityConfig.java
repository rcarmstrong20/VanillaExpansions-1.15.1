package rcarmstrong20.vanilla_expansions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class VeEntityConfig
{
    public static BooleanValue enableSaveTheBunnies;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
    {
        server.comment("Vanilla Expansions Entity Configuration");

        server.comment("Entity Fall Damage Configuration").push("entity_fall_damage");

        enableSaveTheBunnies = server.comment("Cancels bunny fall damage when true.")
                .translation("ve.configEntityFallDamage.enableSaveTheBunnies").worldRestart()
                .define("enable_save_the_bunnies", true);
    }
}
