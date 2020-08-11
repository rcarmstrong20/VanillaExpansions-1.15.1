package rcarmstrong20.vanilla_expansions.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.block.VeBerryBushBlock;
import rcarmstrong20.vanilla_expansions.block.VeColoredCampfireBlock;
import rcarmstrong20.vanilla_expansions.block.VeDirectionalBlock;
import rcarmstrong20.vanilla_expansions.block.VeEaselBlock;
import rcarmstrong20.vanilla_expansions.block.VeFlowingVoidBlock;
import rcarmstrong20.vanilla_expansions.block.VeFrameBlock;
import rcarmstrong20.vanilla_expansions.block.VeGlassBlock;
import rcarmstrong20.vanilla_expansions.block.VeLadderBlock;
import rcarmstrong20.vanilla_expansions.block.VeMushroomBlock;
import rcarmstrong20.vanilla_expansions.block.VeOreBlock;
import rcarmstrong20.vanilla_expansions.block.VePaneBlock;
import rcarmstrong20.vanilla_expansions.block.VePlantingPotBlock;
import rcarmstrong20.vanilla_expansions.block.VePlushBlock;
import rcarmstrong20.vanilla_expansions.block.VePottedSnapdragonBlock;
import rcarmstrong20.vanilla_expansions.block.VePufferfishPlushBlock;
import rcarmstrong20.vanilla_expansions.block.VePurpleHugeMushroomBlock;
import rcarmstrong20.vanilla_expansions.block.VeSevenStageCropBlock;
import rcarmstrong20.vanilla_expansions.block.VeSlimDoorBlock;
import rcarmstrong20.vanilla_expansions.block.VeSnapdragonBlock;
import rcarmstrong20.vanilla_expansions.block.VeTallPlushBlock;
import rcarmstrong20.vanilla_expansions.block.VeThreeStageCropsBlock;
import rcarmstrong20.vanilla_expansions.block.VeTurkeyBlock;
import rcarmstrong20.vanilla_expansions.block.VeWoodcutterBlock;

/*
 * Author: rcarmstrong20
 */
@Mod.EventBusSubscriber(modid = VanillaExpansions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VeBlocks
{
    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<Item> ITEMS = new ArrayList<>();

    // Vanilla Expansions

    public static Block bat_plush = register("bat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
    public static Block blaze_plush = register("blaze_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block cave_spider_plush = register("cave_spider_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
    public static Block chicken_plush = register("chicken_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block cow_plush = register("cow_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block baby_cow_plush = register("baby_cow_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block creeper_plush = register("creeper_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
    public static Block enderman_plush = register("enderman_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block endermite_plush = register("endermite_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
    public static Block ghast_plush = register("ghast_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block guardian_plush = register("guardian_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
    public static Block white_horse_plush = register("white_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block gray_horse_plush = register("gray_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
    public static Block light_gray_horse_plush = register("light_gray_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
    public static Block brown_horse_plush = register("brown_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block black_horse_plush = register("black_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block purple_horse_plush = register("purple_horse_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
    public static Block magma_cube_plush = register("magma_cube_plush", true,
            new VeTallPlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
    public static Block red_mooshroom_plush = register("red_mooshroom_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
    public static Block baby_red_mooshroom_plush = register("baby_red_mooshroom_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
    public static Block brown_mooshroom_plush = register("brown_mooshroom_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
    public static Block baby_brown_mooshroom_plush = register("baby_brown_mooshroom_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block ocelot_plush = register("ocelot_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block tabby_cat_plush = register("tabby_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block tuxedo_cat_plush = register("tuxedo_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block red_cat_plush = register("red_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
    public static Block siamese_cat_plush = register("siamese_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block british_shorthair_cat_plush = register("british_shorthair_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
    public static Block calico_cat_plush = register("calico_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block persian_cat_plush = register("persian_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block ragdoll_cat_plush = register("ragdoll_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block white_cat_plush = register("white_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block jellie_cat_plush = register("jellie_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block black_cat_plush = register("black_cat_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block pig_plush = register("pig_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
    public static Block brown_rabbit_plush = register("brown_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block white_rabbit_plush = register("white_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block black_rabbit_plush = register("black_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block white_splotched_rabbit_plush = register("white_splotched_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block gold_rabbit_plush = register("gold_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block toast_rabbit_plush = register("toast_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block salt_rabbit_plush = register("salt_rabbit_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block white_sheep_plush = register("white_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block orange_sheep_plush = register("orange_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
    public static Block magenta_sheep_plush = register("magenta_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.MAGENTA_WOOL)));
    public static Block light_blue_sheep_plush = register("light_blue_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_BLUE_WOOL)));
    public static Block yellow_sheep_plush = register("yellow_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block lime_sheep_plush = register("lime_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIME_WOOL)));
    public static Block pink_sheep_plush = register("pink_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
    public static Block gray_sheep_plush = register("gray_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
    public static Block light_gray_sheep_plush = register("light_gray_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
    public static Block cyan_sheep_plush = register("cyan_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
    public static Block purple_sheep_plush = register("purple_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
    public static Block blue_sheep_plush = register("blue_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLUE_WOOL)));
    public static Block brown_sheep_plush = register("brown_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block green_sheep_plush = register("green_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
    public static Block red_sheep_plush = register("red_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
    public static Block black_sheep_plush = register("black_sheep_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block silverfish_plush = register("silverfish_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
    public static Block skeleton_plush = register("skeleton_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
    public static Block slime_plush = register("slime_plush", true,
            new VeTallPlushBlock(Block.Properties.from(Blocks.LIME_WOOL)));
    public static Block spider_plush = register("spider_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block squid_plush = register("squid_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLUE_WOOL)));
    public static Block bee_plush = register("bee_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block plains_villager_plush = register("plains_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block desert_villager_plush = register("desert_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
    public static Block jungle_villager_plush = register("jungle_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block savanna_villager_plush = register("savanna_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block snow_villager_plush = register("snow_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block swamp_villager_plush = register("swamp_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block taiga_villager_plush = register("taiga_villager_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
    public static Block witch_plush = register("witch_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
    public static Block wolf_plush = register("wolf_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
    public static Block zombie_plush = register("zombie_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
    public static Block zombie_demon_plush = register("zombie_demon_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
    public static Block zombie_pigman_plush = register("zombie_pigman_plush", true,
            new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
    public static Block pufferfish_plush = register("pufferfish_plush", true,
            new VePufferfishPlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
    public static Block regice_pokedoll = register("regice_pokedoll", true, new VeDirectionalBlock(Block.Properties
            .create(Material.CLAY, MaterialColor.ICE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
    public static Block regirock_pokedoll = register("regirock_pokedoll", true, new VeDirectionalBlock(Block.Properties
            .create(Material.CLAY, MaterialColor.SAND).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
    public static Block registeel_pokedoll = register("registeel_pokedoll", true,
            new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.IRON)
                    .hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
    public static Block regigigas_pokedoll = register("regigigas_pokedoll", true,
            new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.STONE)
                    .hardnessAndResistance(0.2F).sound(SoundType.CLOTH).notSolid()));
    public static Block ruby_ore = register("nether_ruby_ore", true,
            new VeOreBlock(Block.Properties.from(Blocks.DIAMOND_ORE)));
    public static Block ruby_block = register("ruby_block", true, new Block(Block.Properties
            .create(Material.IRON, MaterialColor.RED).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)));
    public static Block nether_smoky_quartz_ore = register("nether_smoky_quartz_ore", true,
            new VeOreBlock(Block.Properties.from(Blocks.NETHER_QUARTZ_ORE)));
    public static Block smoky_quartz_block = register("smoky_quartz_block", true,
            new Block(Block.Properties.from(Blocks.QUARTZ_BLOCK)));
    public static Block chiseled_smoky_quartz_block = register("chiseled_smoky_quartz_block", true,
            new Block(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smoky_quartz_pillar = register("smoky_quartz_pillar", true,
            new RotatedPillarBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smoky_quartz_stairs = register("smoky_quartz_stairs", true, new StairsBlock(
            () -> VeBlocks.smoky_quartz_block.getDefaultState(), Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smoky_quartz_slab = register("smoky_quartz_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smoky_quartz_wall = register("smoky_quartz_wall", true,
            new WallBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smooth_smoky_quartz = register("smooth_smoky_quartz", true,
            new Block(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smooth_smoky_quartz_stairs = register("smooth_smoky_quartz_stairs", true, new StairsBlock(
            () -> VeBlocks.smooth_smoky_quartz.getDefaultState(), Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smooth_smoky_quartz_slab = register("smooth_smoky_quartz_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block smooth_smoky_quartz_wall = register("smooth_smoky_quartz_wall", true,
            new WallBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
    public static Block cracked_stone_brick_stairs = register("cracked_stone_brick_stairs", true, new StairsBlock(
            () -> Blocks.CRACKED_STONE_BRICKS.getDefaultState(), Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
    public static Block cracked_stone_brick_slab = register("cracked_stone_brick_slab", true,
            new SlabBlock(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
    public static Block cracked_stone_brick_wall = register("cracked_stone_brick_wall", true,
            new WallBlock(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
    public static Block quartz_wall = register("quartz_wall", true,
            new WallBlock(Block.Properties.from(Blocks.QUARTZ_BLOCK)));
    public static Block smooth_quartz_wall = register("smooth_quartz_wall", true,
            new WallBlock(Block.Properties.from(Blocks.SMOOTH_QUARTZ)));
    public static Block stone_wall = register("stone_wall", true, new WallBlock(Block.Properties.from(Blocks.STONE)));
    public static Block red_glass = register("red_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block red_glass_pane = register("red_glass_pane", true,
            new VePaneBlock(Block.Properties.from(Blocks.GLASS_PANE)));
    public static Block blueberry_bush = register("blueberry_bush", false,
            new VeBerryBushBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block cranberry_bush = register("cranberry_bush", false,
            new VeBerryBushBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block bok_choy = register("bok_choy", false,
            new VeSevenStageCropBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block garlic = register("garlic", false,
            new VeThreeStageCropsBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block green_onions = register("green_onions", false,
            new VeThreeStageCropsBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block quinoa = register("quinoa", false,
            new VeSevenStageCropBlock(Block.Properties.from(Blocks.WHEAT)));
    public static Block stone_brick_planting_pot = register("stone_brick_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.STONE_BRICKS)));
    public static Block mossy_stone_brick_planting_pot = register("mossy_stone_brick_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.MOSSY_STONE_BRICKS)));
    public static Block cracked_stone_brick_planting_pot = register("cracked_stone_brick_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
    public static Block oak_planting_pot = register("oak_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static Block spruce_planting_pot = register("spruce_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.SPRUCE_PLANKS)));
    public static Block birch_planting_pot = register("birch_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.BIRCH_PLANKS)));
    public static Block jungle_planting_pot = register("jungle_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.JUNGLE_PLANKS)));
    public static Block acacia_planting_pot = register("acacia_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.ACACIA_PLANKS)));
    public static Block dark_oak_planting_pot = register("dark_oak_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.DARK_OAK_PLANKS)));
    public static Block nether_brick_planting_pot = register("nether_brick_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.NETHER_BRICKS)));
    public static Block obsidian_planting_pot = register("obsidian_planting_pot", true,
            new VePlantingPotBlock(Block.Properties.from(Blocks.END_STONE)));
    public static Block oak_wall = register("oak_wall", true, new WallBlock(Block.Properties.from(Blocks.OAK_LOG)));
    public static Block spruce_wall = register("spruce_wall", true,
            new WallBlock(Block.Properties.from(Blocks.SPRUCE_LOG)));
    public static Block birch_wall = register("birch_wall", true,
            new WallBlock(Block.Properties.from(Blocks.BIRCH_LOG)));
    public static Block jungle_wall = register("jungle_wall", true,
            new WallBlock(Block.Properties.from(Blocks.JUNGLE_LOG)));
    public static Block acacia_wall = register("acacia_wall", true,
            new WallBlock(Block.Properties.from(Blocks.ACACIA_LOG)));
    public static Block dark_oak_wall = register("dark_oak_wall", true,
            new WallBlock(Block.Properties.from(Blocks.DARK_OAK_LOG)));
    public static Block stripped_oak_wall = register("stripped_oak_wall", true,
            new WallBlock(Block.Properties.from(Blocks.OAK_LOG)));
    public static Block stripped_spruce_wall = register("stripped_spruce_wall", true,
            new WallBlock(Block.Properties.from(Blocks.SPRUCE_LOG)));
    public static Block stripped_birch_wall = register("stripped_birch_wall", true,
            new WallBlock(Block.Properties.from(Blocks.BIRCH_LOG)));
    public static Block stripped_jungle_wall = register("stripped_jungle_wall", true,
            new WallBlock(Block.Properties.from(Blocks.JUNGLE_LOG)));
    public static Block stripped_acacia_wall = register("stripped_acacia_wall", true,
            new WallBlock(Block.Properties.from(Blocks.ACACIA_LOG)));
    public static Block stripped_dark_oak_wall = register("stripped_dark_oak_wall", true,
            new WallBlock(Block.Properties.from(Blocks.DARK_OAK_LOG)));
    public static Block spruce_ladder = register("spruce_ladder", true,
            new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
    public static Block birch_ladder = register("birch_ladder", true,
            new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
    public static Block jungle_ladder = register("jungle_ladder", true,
            new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
    public static Block acacia_ladder = register("acacia_ladder", true,
            new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
    public static Block dark_oak_ladder = register("dark_oak_ladder", true,
            new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
    public static Block iron_ladder = register("iron_ladder", true,
            new VeLadderBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON)
                    .hardnessAndResistance(0.4F, 5.0F).sound(SoundType.METAL).notSolid()));
    public static Block modern_door = register("modern_door", true, new VeSlimDoorBlock(Block.Properties
            .create(Material.ANVIL, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL)));
    public static Block dirt_slab = register("dirt_slab", true, new SlabBlock(Block.Properties.from(Blocks.DIRT)));
    public static Block dirt_stairs = register("dirt_stairs", true,
            new StairsBlock(() -> Blocks.DIRT.getDefaultState(), Block.Properties.from(Blocks.DIRT)));
    public static Block coarse_dirt_slab = register("coarse_dirt_slab", true,
            new SlabBlock(Block.Properties.from(Blocks.COARSE_DIRT)));
    public static Block coarse_dirt_stairs = register("coarse_dirt_stairs", true,
            new StairsBlock(() -> Blocks.COARSE_DIRT.getDefaultState(), Block.Properties.from(Blocks.COARSE_DIRT)));
    public static Block oak_frame = register("oak_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block spruce_frame = register("spruce_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block birch_frame = register("birch_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block jungle_frame = register("jungle_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block acacia_frame = register("acacia_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block dark_oak_frame = register("dark_oak_frame", true,
            new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F)
                    .sound(SoundType.WOOD).notSolid().doesNotBlockMovement()));
    public static Block snow_bricks = register("snow_bricks", true,
            new Block(Block.Properties.create(Material.SNOW_BLOCK, MaterialColor.SNOW).hardnessAndResistance(0.6F)
                    .harvestTool(ToolType.SHOVEL).sound(SoundType.SNOW)));
    public static Block snow_brick_stairs = register("snow_brick_stairs", true,
            new StairsBlock(() -> VeBlocks.snow_bricks.getDefaultState(), Block.Properties.from(VeBlocks.snow_bricks)));
    public static Block snow_brick_slab = register("snow_brick_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.snow_bricks)));
    public static Block snow_brick_wall = register("snow_brick_wall", true,
            new WallBlock(Block.Properties.from(VeBlocks.snow_bricks)));
    public static Block packed_snow_block = register("packed_snow_block", true,
            new Block(Block.Properties.create(Material.SNOW_BLOCK, MaterialColor.SNOW).hardnessAndResistance(0.4F)
                    .harvestTool(ToolType.SHOVEL).sound(SoundType.SNOW).slipperiness(0.98F)));
    public static Block packed_snow_stairs = register("packed_snow_stairs", true, new StairsBlock(
            () -> VeBlocks.packed_snow_block.getDefaultState(), Block.Properties.from(VeBlocks.packed_snow_block)));
    public static Block packed_snow_slab = register("packed_snow_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.packed_snow_block)));
    public static Block spider_glass = register("spider_glass", true,
            new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block spider_glass_pane = register("spider_glass_pane", true,
            new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block black_spider_glass = register("black_spider_glass", true,
            new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block black_spider_glass_pane = register("black_spider_glass_pane", true,
            new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block fancy_spider_glass = register("fancy_spider_glass", true,
            new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block fancy_spider_glass_pane = register("fancy_spider_glass_pane", true,
            new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block black_fancy_spider_glass = register("black_fancy_spider_glass", true,
            new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block black_fancy_spider_glass_pane = register("black_fancy_spider_glass_pane", true,
            new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block turkey = register("turkey", true, new VeTurkeyBlock(Block.Properties
            .create(Material.CAKE, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), 1);
    public static Block glass_of_darkness = register("glass_of_darkness", true,
            new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
    public static Block purple_mushroom = register("purple_mushroom", true,
            new VeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM)));
    public static Block purple_mushroom_block = register("purple_mushroom_block", true,
            new VePurpleHugeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM_BLOCK)));
    public static Block woodcutter = register("woodcutter", true,
            new VeWoodcutterBlock(Block.Properties.from(Blocks.STONECUTTER)));
    public static Block white_campfire = register("white_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block orange_campfire = register("orange_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block magenta_campfire = register("magenta_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block light_blue_campfire = register("light_blue_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block yellow_campfire = register("yellow_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block lime_campfire = register("lime_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block pink_campfire = register("pink_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block gray_campfire = register("gray_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block light_gray_campfire = register("light_gray_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block cyan_campfire = register("cyan_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block purple_campfire = register("purple_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block blue_campfire = register("blue_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block brown_campfire = register("brown_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block green_campfire = register("green_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block red_campfire = register("red_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block black_campfire = register("black_campfire", true,
            new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
    public static Block witchs_cradle = register("witchs_cradle", false,
            new VeBerryBushBlock(Block.Properties.from(Blocks.SWEET_BERRY_BUSH)));
    public static Block void_liquid = register("void", false,
            new VeFlowingVoidBlock(() -> VeFluids.FLOWING_VOID,
                    Block.Properties.create(Material.WATER, MaterialColor.BLACK).doesNotBlockMovement()
                            .hardnessAndResistance(100.0F).noDrops()));
    public static Block bamboo_fence = register("bamboo_fence", true,
            new FenceBlock(Block.Properties.from(Blocks.BAMBOO)));
    public static Block bamboo_fence_gate = register("bamboo_fence_gate", true,
            new FenceGateBlock(Block.Properties.from(Blocks.BAMBOO)));
    public static Block bamboo_wall = register("bamboo_wall", true,
            new WallBlock(Block.Properties.from(Blocks.BAMBOO)));
    public static Block snowflake_obsidian = register("snowflake_obsidian", true,
            new Block(Block.Properties.from(Blocks.OBSIDIAN)));
    public static Block nephilite = register("nephilite", true,
            new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.3F).sound(SoundType.STONE)));
    public static Block nephilite_slab = register("nephilite_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_stairs = register("nephilite_stairs", true,
            new StairsBlock(() -> VeBlocks.nephilite.getDefaultState(), Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_wall = register("nephilite_wall", true,
            new WallBlock(Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_bricks = register("nephilite_bricks", true,
            new Block(Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_brick_slab = register("nephilite_brick_slab", true,
            new SlabBlock(Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_brick_stairs = register("nephilite_brick_stairs", true,
            new StairsBlock(() -> VeBlocks.nephilite.getDefaultState(), Block.Properties.from(VeBlocks.nephilite)));
    public static Block nephilite_brick_wall = register("nephilite_brick_wall", true,
            new WallBlock(Block.Properties.from(VeBlocks.nephilite)));
    public static Block chiseled_nephilite = register("chiseled_nephilite", true,
            new Block(Block.Properties.from(VeBlocks.nephilite)));
    public static Block easel = register("easel", true, new VeEaselBlock(
            Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).notSolid()));
    public static Block snow_slab = register("snow_slab", true,
            new SlabBlock(Block.Properties.from(Blocks.SNOW_BLOCK).harvestTool(ToolType.SHOVEL)));
    public static Block snow_stairs = register("snow_stairs", true,
            new StairsBlock(() -> Blocks.SNOW_BLOCK.getDefaultState(),
                    Block.Properties.from(Blocks.SNOW_BLOCK).harvestTool(ToolType.SHOVEL)));
    public static Block snow_wall = register("snow_wall", true,
            new WallBlock(Block.Properties.from(Blocks.SNOW_BLOCK).harvestTool(ToolType.SHOVEL)));
    public static Block snapdragon = register("snapdragon", true,
            new VeSnapdragonBlock(Effects.LUCK, 8, Block.Properties.from(Blocks.POPPY).lightValue(4)));
    public static Block potted_snapdragon = register("potted_snapdragon", false,
            new VePottedSnapdragonBlock(VeBlocks.snapdragon, Block.Properties.from(Blocks.POTTED_POPPY).lightValue(4)));

    /*
     * Registers a normal block that has default properties.
     */
    private static Block register(String name, boolean hasItemBlock, Block block)
    {
        return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), hasItemBlock, block,
                new Item.Properties().group(VanillaExpansions.VE_GROUP));
    }

    /*
     * Registers a block that can have a custom stack size.
     */
    private static Block register(String name, boolean hasItemBlock, Block block, int size)
    {
        return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), hasItemBlock, block,
                new Item.Properties().group(VanillaExpansions.VE_GROUP).maxStackSize(size));
    }

    @SuppressWarnings("unused")
    private static Block register(String name, boolean hasItemBlock, Block block, ItemGroup group)
    {
        return register(new ResourceLocation(name), hasItemBlock, block, new Item.Properties().group(group));
    }

    /*
     * A method that gets the data from the other registry methods and is used in
     * the register merthod that creates each list
     */
    private static Block register(ResourceLocation name, boolean hasItemBlock, Block block, Item.Properties properties)
    {
        return register(name, hasItemBlock, block, new BlockItem(block, properties));
    }

    /*
     * Adds the blocks and block items to two lists
     */
    private static Block register(ResourceLocation name, boolean hasItemBlock, Block block, BlockItem item)
    {
        block.setRegistryName(name);
        BLOCKS.add(block);
        if (hasItemBlock && block.getRegistryName() != null)
        {
            item.setRegistryName(name);
            ITEMS.add(item);
        }
        return block;
    }

    /*
     * Registers the blocks
     */
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
        BLOCKS.clear();
        VanillaExpansions.LOGGER.info("Blocks registered.");
    }

    /*
     * Registers the item blocks
     */
    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
    {
        ITEMS.forEach(item -> event.getRegistry().register(item));
        ITEMS.clear();
        VanillaExpansions.LOGGER.info("Item blocks registered.");
    }
}
