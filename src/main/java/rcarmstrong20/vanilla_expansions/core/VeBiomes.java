package rcarmstrong20.vanilla_expansions.core;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.block.VeBerryBushBlock;
import rcarmstrong20.vanilla_expansions.config.VeFeatureGenConfig;
import rcarmstrong20.vanilla_expansions.config.VeOreGenConfig;

/*
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeBiomes
{
    public static final List<Biome> COLD_BIOMES = Arrays.asList(Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE,
            Biomes.GRAVELLY_MOUNTAINS, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.TAIGA_MOUNTAINS,
            Biomes.WOODED_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS,
            Biomes.ICE_SPIKES, Biomes.FROZEN_RIVER, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
    public static final List<Biome> FOREST_BIOMES = Arrays.asList(Biomes.FOREST, Biomes.BIRCH_FOREST,
            Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.FLOWER_FOREST);
    public static final List<Biome> DARK_FOREST_BIOMES = Arrays.asList(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS);
    public static final List<Biome> END_CITY_BIOMES = Arrays.asList(Biomes.END_BARRENS, Biomes.END_HIGHLANDS,
            Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS);
    public static final List<Biome> TAIGA_CABIN_BIOMES = Arrays.asList(Biomes.TAIGA, Biomes.TAIGA_HILLS,
            Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA,
            Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS);
    public static final List<Biome> FOREST_CABIN_BIOME = Arrays.asList(Biomes.FOREST);
    public static final List<Biome> BIRCH_CABIN_BIOMES = Arrays.asList(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
            Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);

    public static final BlockState NETHER_SMOKY_QUARTZ_ORE = VeBlocks.nether_smoky_quartz_ore.getDefaultState();
    public static final BlockState NETHER_RUBY_ORE = VeBlocks.ruby_ore.getDefaultState();
    public static final BlockState BLUEBERRY_BUSH = VeBlocks.blueberry_bush.getDefaultState().with(VeBerryBushBlock.AGE,
            3);
    public static final BlockState CRANBERRY_BUSH = VeBlocks.cranberry_bush.getDefaultState().with(VeBerryBushBlock.AGE,
            3);
    public static final BlockState WITCHS_CRADLE = VeBlocks.witchs_cradle.getDefaultState().with(VeBerryBushBlock.AGE,
            3);
    public static final BlockState VOID_LIQUID = VeBlocks.void_liquid.getDefaultState();
    public static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
    public static final BlockState END_STONE = Blocks.END_STONE.getDefaultState();
    public static final BlockState SNAPDRAGON = VeBlocks.snapdragon.getDefaultState();
    public static final BlockState ENDER_GRASS = VeBlocks.ender_grass.getDefaultState();
    public static final BlockState PURPLE_MUSHROOM_BLOCK = VeBlocks.purple_mushroom_block.getDefaultState()
            .with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
    public static final BlockState MUSHROOM_STEM = Blocks.MUSHROOM_STEM.getDefaultState()
            .with(HugeMushroomBlock.UP, Boolean.valueOf(false)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
    public static final BlockClusterFeatureConfig BLUEBERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BLUEBERRY_BUSH), new SimpleBlockPlacer())).tries(64)
                    .whitelist(ImmutableSet.of(GRASS_BLOCK.getBlock())).func_227317_b_().build();
    public static final BlockClusterFeatureConfig CRANBERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(CRANBERRY_BUSH), new SimpleBlockPlacer())).tries(64)
                    .whitelist(ImmutableSet.of(GRASS_BLOCK.getBlock())).func_227317_b_().build();
    public static final BlockClusterFeatureConfig END_SNAPDRAGON_AND_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(
            (new WeightedBlockStateProvider()).addWeightedBlockstate(ENDER_GRASS, 2).addWeightedBlockstate(SNAPDRAGON,
                    1),
            new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(END_STONE.getBlock())).func_227317_b_()
                    .build();
    public static final BlockClusterFeatureConfig WITCHS_CRADLE_CONFIG = (new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(WITCHS_CRADLE), new SimpleBlockPlacer())).tries(64)
                    .whitelist(ImmutableSet.of(GRASS_BLOCK.getBlock())).func_227317_b_().build();
    public static final BigMushroomFeatureConfig BIG_PURPLE_MUSHROOM_CONFIG = new BigMushroomFeatureConfig(
            new SimpleBlockStateProvider(PURPLE_MUSHROOM_BLOCK), new SimpleBlockStateProvider(MUSHROOM_STEM), 2);
    public static final VillageConfig TAIGA_CABIN_CONFIG = new VillageConfig("ve:cabins/taiga_cabin", 3);
    public static final VillageConfig FOREST_CABIN_CONFIG = new VillageConfig("ve:cabins/forest_cabin", 4);

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event)
    {
        addFeature(Decoration.UNDERGROUND_ORES,
                Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,
                                NETHER_SMOKY_QUARTZ_ORE, VeOreGenConfig.netherSmokyQuartzOreVeinSize.get()))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 10, 20, 128))),
                VeOreGenConfig.enableNetherSmokyQuartzOreSpawns.get(), Category.NETHER);
        addFeature(Decoration.UNDERGROUND_ORES,
                Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,
                                NETHER_RUBY_ORE, VeOreGenConfig.netherRubyOreVeinSize.get()))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 16))),
                VeOreGenConfig.enableNetherRubyOreSpawns.get(), Category.NETHER);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.withConfiguration(BLUEBERRY_BUSH_CONFIG)
                        .withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))),
                VeFeatureGenConfig.enableBlueberryBushSpawns.get(), FOREST_BIOMES);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.withConfiguration(BLUEBERRY_BUSH_CONFIG)
                        .withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(12))),
                VeFeatureGenConfig.enableBlueberryBushSpawns.get(), FOREST_BIOMES);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.withConfiguration(CRANBERRY_BUSH_CONFIG)
                        .withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))),
                VeFeatureGenConfig.enableCranberryBushSpawns.get(), FOREST_BIOMES);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.withConfiguration(CRANBERRY_BUSH_CONFIG)
                        .withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(14))),
                VeFeatureGenConfig.enableCranberryBushSpawns.get(), FOREST_BIOMES);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.withConfiguration(WITCHS_CRADLE_CONFIG)
                        .withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(10))),
                VeFeatureGenConfig.enableWitchsCradleSpawns.get(), Category.SWAMP);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_BOOLEAN_SELECTOR
                        .withConfiguration(new TwoFeatureChoiceConfig(
                                Feature.HUGE_RED_MUSHROOM.withConfiguration(BIG_PURPLE_MUSHROOM_CONFIG),
                                Feature.HUGE_RED_MUSHROOM.withConfiguration(BIG_PURPLE_MUSHROOM_CONFIG)))
                        .withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))),
                VeFeatureGenConfig.enableBigPurpleMushroomSpawns.get(), DARK_FOREST_BIOMES);
        addFeature(Decoration.LOCAL_MODIFICATIONS,
                Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(VOID_LIQUID))
                        .withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))),
                VeFeatureGenConfig.enableVoidLakeSpawns.get(), END_CITY_BIOMES);
        addFeature(Decoration.VEGETAL_DECORATION,
                Feature.FLOWER.withConfiguration(END_SNAPDRAGON_AND_GRASS_CONFIG)
                        .withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))),
                VeFeatureGenConfig.enableSnapdragonSpawns.get(), END_CITY_BIOMES);
        addStructure(Decoration.SURFACE_STRUCTURES, VeFeature.CABIN, TAIGA_CABIN_CONFIG,
                VeFeatureGenConfig.enableTaigaCabinSpawns.get(), Category.TAIGA);
        addStructure(Decoration.SURFACE_STRUCTURES, VeFeature.CABIN, FOREST_CABIN_CONFIG,
                VeFeatureGenConfig.enableForestCabinSpawns.get(), FOREST_CABIN_BIOME);

        VanillaExpansions.LOGGER.info("Biome Features registered.");
    }

    /**
     * Add a new feature to the spawn list for every biome that exists in a certain
     * category.
     */
    private static void addFeature(Decoration decoration, ConfiguredFeature<?, ?> feature, boolean enable,
            Biome.Category category)
    {
        if (enable)
        {
            for (Biome biome : ForgeRegistries.BIOMES)
            {
                if (biome.getCategory() == category)
                {
                    biome.addFeature(decoration, feature);
                }
            }
        }
    }

    /**
     * Add a new feature to the spawn list for specific biomes.
     */
    private static void addFeature(Decoration decoration, ConfiguredFeature<?, ?> feature, boolean enable,
            List<Biome> biomes)
    {
        if (enable)
        {
            for (Biome biome : biomes)
            {
                if (biome != null && biomes.contains(biome))
                {
                    biome.addFeature(decoration, feature);
                }
            }
        }
    }

    /**
     * Add a new structure that uses the village config to the spawn list for every
     * biome that exists in a certain category.
     */
    private static <C extends IFeatureConfig> void addStructure(Decoration decorationType,
            Structure<VillageConfig> structure, VillageConfig config, boolean enable, Biome.Category category)
    {
        if (enable)
        {
            for (Biome biome : ForgeRegistries.BIOMES)
            {
                biome.addFeature(decorationType, structure.withConfiguration(config)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

                if (biome != null && biome.getCategory() == category)
                {
                    biome.addStructure(structure.withConfiguration(config));
                }
            }
        }
    }

    /**
     * Add a new structure that uses the village config to the spawn list for
     * specific biomes.
     */
    private static <C extends IFeatureConfig> void addStructure(Decoration decorationType,
            Structure<VillageConfig> structure, VillageConfig config, boolean enable, List<Biome> biomes)
    {
        if (enable)
        {
            for (Biome biome : biomes)
            {
                biome.addFeature(decorationType, structure.withConfiguration(config)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

                if (biome != null && biomes.contains(biome))
                {
                    biome.addStructure(structure.withConfiguration(config));
                }
            }
        }
    }

    /**
     * Add a new entity to be spawned into the world.
     */
    @SuppressWarnings("unused")
    private static void registerEntitySpawn(EntityType<?> entity, int weight, int maxCount, boolean enable,
            List<Biome> biomes)
    {
        if (enable)
        {
            for (Biome biome : biomes)
            {
                if (biome != null)
                {
                    biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(entity, weight, 1, maxCount));
                }
            }
        }
    }
}
