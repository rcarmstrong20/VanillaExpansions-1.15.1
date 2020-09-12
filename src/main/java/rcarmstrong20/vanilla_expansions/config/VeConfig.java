package rcarmstrong20.vanilla_expansions.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

/**
 *
 * @author Ryan
 *
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Bus.MOD)
public class VeConfig
{
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_CONFIG;

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static
    {
        VeOreGenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        VeCropConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        VeFeatureGenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        VeEntityConfig.init(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path)
    {
        VanillaExpansions.LOGGER.info("Loading config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        VanillaExpansions.LOGGER.info("Built config: " + path);
        file.load();
        VanillaExpansions.LOGGER.info("Loaded config: " + path);
        config.setConfig(file);
    }

    /**
     * Returns the inputed string with the minecraft id.
     */
    public static String getMinecraftPrefix(String name)
    {
        return "minecraft:" + name;
    }
}
