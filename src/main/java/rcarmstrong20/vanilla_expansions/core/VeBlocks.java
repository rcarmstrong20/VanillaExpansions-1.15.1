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
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.VeBlock;
import rcarmstrong20.vanilla_expansions.block.VeBerryBushBlock;
import rcarmstrong20.vanilla_expansions.block.VeColoredCampfireBlock;
import rcarmstrong20.vanilla_expansions.block.VeDirectionalBlock;
import rcarmstrong20.vanilla_expansions.block.VeDoubleSlabBlock;
import rcarmstrong20.vanilla_expansions.block.VeFlowingVoidBlock;
import rcarmstrong20.vanilla_expansions.block.VeFrameBlock;
import rcarmstrong20.vanilla_expansions.block.VeGlassBlock;
import rcarmstrong20.vanilla_expansions.block.VeLadderBlock;
import rcarmstrong20.vanilla_expansions.block.VeMushroomBlock;
import rcarmstrong20.vanilla_expansions.block.VeOreBlock;
import rcarmstrong20.vanilla_expansions.block.VePaneBlock;
import rcarmstrong20.vanilla_expansions.block.VePlantingPotBlock;
import rcarmstrong20.vanilla_expansions.block.VePlushBlock;
import rcarmstrong20.vanilla_expansions.block.VePufferfishPlushBlock;
import rcarmstrong20.vanilla_expansions.block.VePurpleHugeMushroomBlock;
import rcarmstrong20.vanilla_expansions.block.VeSevenStageCropBlock;
import rcarmstrong20.vanilla_expansions.block.VeSleepingBagBlock;
import rcarmstrong20.vanilla_expansions.block.VeSlimDoorBlock;
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
	
	//Vanilla Expansions
	
	public static Block bat_plush = register("bat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
	public static Block blaze_plush = register("blaze_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block cave_spider_plush = register("cave_spider_plush", true, new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
	public static Block chicken_plush = register("chicken_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block cow_plush = register("cow_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block baby_cow_plush = register("baby_cow_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block creeper_plush = register("creeper_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
	public static Block enderman_plush = register("enderman_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block endermite_plush = register("endermite_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
	public static Block ghast_plush = register("ghast_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block guardian_plush = register("guardian_plush", true, new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
	public static Block white_horse_plush = register("white_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block gray_horse_plush = register("gray_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
	public static Block light_gray_horse_plush = register("light_gray_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
	public static Block brown_horse_plush = register("brown_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block black_horse_plush = register("black_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block purple_horse_plush = register("purple_horse_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
	public static Block magma_cube_plush = register("magma_cube_plush", true, new VeTallPlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
	public static Block red_mooshroom_plush = register("red_mooshroom_plush", true, new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
	public static Block baby_red_mooshroom_plush = register("baby_red_mooshroom_plush", true, new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
	public static Block brown_mooshroom_plush = register("brown_mooshroom_plush", true, new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
	public static Block baby_brown_mooshroom_plush = register("baby_brown_mooshroom_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block ocelot_plush = register("ocelot_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block tabby_cat_plush = register("tabby_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block tuxedo_cat_plush = register("tuxedo_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block red_cat_plush = register("red_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
	public static Block siamese_cat_plush = register("siamese_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block british_shorthair_cat_plush = register("british_shorthair_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
	public static Block calico_cat_plush = register("calico_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block persian_cat_plush = register("persian_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block ragdoll_cat_plush = register("ragdoll_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block white_cat_plush = register("white_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block jellie_cat_plush = register("jellie_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block black_cat_plush = register("black_cat_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block pig_plush = register("pig_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
	public static Block rabbit_plush = register("rabbit_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block white_sheep_plush = register("white_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block orange_sheep_plush = register("orange_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
	public static Block magenta_sheep_plush = register("magenta_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.MAGENTA_WOOL)));
	public static Block light_blue_sheep_plush = register("light_blue_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_BLUE_WOOL)));
	public static Block yellow_sheep_plush = register("yellow_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block lime_sheep_plush = register("lime_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIME_WOOL)));
	public static Block pink_sheep_plush = register("pink_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
	public static Block gray_sheep_plush = register("gray_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GRAY_WOOL)));
	public static Block light_gray_sheep_plush = register("light_gray_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
	public static Block cyan_sheep_plush = register("cyan_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.CYAN_WOOL)));
	public static Block purple_sheep_plush = register("purple_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
	public static Block blue_sheep_plush = register("blue_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLUE_WOOL)));
	public static Block brown_sheep_plush = register("brown_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block green_sheep_plush = register("green_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
	public static Block red_sheep_plush = register("red_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.RED_WOOL)));
	public static Block black_sheep_plush = register("black_sheep_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block silverfish_plush = register("silverfish_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
	public static Block skeleton_plush = register("skeleton_plush", true, new VePlushBlock(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)));
	public static Block slime_plush = register("slime_plush", true, new VeTallPlushBlock(Block.Properties.from(Blocks.LIME_WOOL)));
	public static Block spider_plush = register("spider_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block squid_plush = register("squid_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLUE_WOOL)));
	public static Block bee_plush = register("bee_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block plains_villager_plush = register("plains_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block desert_villager_plush = register("desert_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.ORANGE_WOOL)));
	public static Block jungle_villager_plush = register("jungle_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block savanna_villager_plush = register("savanna_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block snow_villager_plush = register("snow_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block swamp_villager_plush = register("swamp_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block taiga_villager_plush = register("taiga_villager_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BROWN_WOOL)));
	public static Block witch_plush = register("witch_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PURPLE_WOOL)));
	public static Block wolf_plush = register("wolf_plush", true, new VePlushBlock(Block.Properties.from(Blocks.WHITE_WOOL)));
	public static Block zombie_plush = register("zombie_plush", true, new VePlushBlock(Block.Properties.from(Blocks.GREEN_WOOL)));
	public static Block zombie_demon_plush = register("zombie_demon_plush", true, new VePlushBlock(Block.Properties.from(Blocks.BLACK_WOOL)));
	public static Block zombie_pigman_plush = register("zombie_pigman_plush", true, new VePlushBlock(Block.Properties.from(Blocks.PINK_WOOL)));
	public static Block pufferfish_plush = register("pufferfish_plush", true, new VePufferfishPlushBlock(Block.Properties.from(Blocks.YELLOW_WOOL)));
	public static Block regice_pokedoll = register("regice_pokedoll", true, new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.ICE).hardnessAndResistance(VeBlock.CLOTH_HARDNESS).sound(SoundType.CLOTH).notSolid()));
	public static Block regirock_pokedoll = register("regirock_pokedoll", true, new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.SAND).hardnessAndResistance(VeBlock.CLOTH_HARDNESS).sound(SoundType.CLOTH).notSolid()));
	public static Block registeel_pokedoll = register("registeel_pokedoll", true, new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.IRON).hardnessAndResistance(VeBlock.CLOTH_HARDNESS).sound(SoundType.CLOTH).notSolid()));
	public static Block regigigas_pokedoll = register("regigigas_pokedoll", true, new VeDirectionalBlock(Block.Properties.create(Material.CLAY, MaterialColor.STONE).hardnessAndResistance(VeBlock.CLOTH_HARDNESS).sound(SoundType.CLOTH).notSolid()));
	public static Block ruby_ore = register("nether_ruby_ore", true, new VeOreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(VeBlock.STONE_HARDNESS, VeBlock.STONE_RESISTANCE).sound(SoundType.STONE)));
	public static Block ruby_block = register("ruby_block", true, new Block(Block.Properties.create(Material.IRON, MaterialColor.RED).hardnessAndResistance(VeBlock.STONE_HARDNESS, VeBlock.STONE_RESISTANCE).sound(SoundType.STONE)));
	public static Block smoky_quartz_ore = register("nether_smoky_quartz_ore", true, new VeOreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(VeBlock.STONE_HARDNESS, VeBlock.STONE_RESISTANCE).sound(SoundType.STONE)));
	public static Block smoky_quartz_block = register("smoky_quartz_block", true, new Block(Block.Properties.from(Blocks.QUARTZ_BLOCK)));
	public static Block chiseled_smoky_quartz_block = register("chiseled_smoky_quartz_block", true, new Block(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smoky_quartz_pillar = register("smoky_quartz_pillar", true, new RotatedPillarBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smoky_quartz_stairs = register("smoky_quartz_stairs", true, new StairsBlock(() -> VeBlocks.smoky_quartz_block.getDefaultState(), Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smoky_quartz_slab = register("smoky_quartz_slab", true, new SlabBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smoky_quartz_wall = register("smoky_quartz_wall", true, new WallBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smooth_smoky_quartz = register("smooth_smoky_quartz", true, new Block(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smooth_smoky_quartz_stairs = register("smooth_smoky_quartz_stairs", true, new StairsBlock(() -> VeBlocks.smooth_smoky_quartz.getDefaultState(), Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smooth_smoky_quartz_slab = register("smooth_smoky_quartz_slab", true, new SlabBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block smooth_smoky_quartz_wall = register("smooth_smoky_quartz_wall", true, new WallBlock(Block.Properties.from(VeBlocks.smoky_quartz_block)));
	public static Block cracked_stone_brick_stairs = register("cracked_stone_brick_stairs", true, new StairsBlock(() -> Blocks.CRACKED_STONE_BRICKS.getDefaultState(), Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
	public static Block cracked_stone_brick_slab = register("cracked_stone_brick_slab", true, new SlabBlock(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
	public static Block cracked_stone_brick_wall = register("cracked_stone_brick_wall", true, new WallBlock(Block.Properties.from(Blocks.CRACKED_STONE_BRICKS)));
	public static Block quartz_wall = register("quartz_wall", true, new WallBlock(Block.Properties.from(Blocks.QUARTZ_BLOCK)));
	public static Block smooth_quartz_wall = register("smooth_quartz_wall", true, new WallBlock(Block.Properties.from(Blocks.SMOOTH_QUARTZ)));
	public static Block stone_wall = register("stone_wall", true, new WallBlock(Block.Properties.from(Blocks.STONE)));
	public static Block red_glass = register("red_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block red_glass_pane = register("red_glass_pane", true, new VePaneBlock(Block.Properties.from(Blocks.GLASS_PANE)));
	public static Block blueberry_bush = register("blueberry_bush", false, new VeBerryBushBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block cranberry_bush = register("cranberry_bush", false, new VeBerryBushBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block bok_choy = register("bok_choy", false, new VeSevenStageCropBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block garlic = register("garlic", false, new VeThreeStageCropsBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block green_onions = register("green_onions", false, new VeThreeStageCropsBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block quinoa = register("quinoa", false, new VeSevenStageCropBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block oak_planting_pot = register("oak_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
	public static Block spruce_planting_pot = register("spruce_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.SPRUCE_PLANKS)));
	public static Block birch_planting_pot = register("birch_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.BIRCH_PLANKS)));
	public static Block jungle_planting_pot = register("jungle_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.JUNGLE_PLANKS)));
	public static Block acacia_planting_pot = register("acacia_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.ACACIA_PLANKS)));
	public static Block dark_oak_planting_pot = register("dark_oak_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.DARK_OAK_PLANKS)));
	public static Block nether_planting_pot = register("nether_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.NETHER_BRICKS)));
	public static Block end_planting_pot = register("end_planting_pot", true, new VePlantingPotBlock(Block.Properties.from(Blocks.END_STONE)));
	public static Block oak_wall = register("oak_wall", true, new WallBlock(Block.Properties.from(Blocks.OAK_LOG)));
	public static Block spruce_wall = register("spruce_wall", true, new WallBlock(Block.Properties.from(Blocks.SPRUCE_LOG)));
	public static Block birch_wall = register("birch_wall", true, new WallBlock(Block.Properties.from(Blocks.BIRCH_LOG)));
	public static Block jungle_wall = register("jungle_wall", true, new WallBlock(Block.Properties.from(Blocks.JUNGLE_LOG)));
	public static Block acacia_wall = register("acacia_wall", true, new WallBlock(Block.Properties.from(Blocks.ACACIA_LOG)));
	public static Block dark_oak_wall = register("dark_oak_wall", true, new WallBlock(Block.Properties.from(Blocks.DARK_OAK_LOG)));
	public static Block stripped_oak_wall = register("stripped_oak_wall", true, new WallBlock(Block.Properties.from(Blocks.OAK_LOG)));
	public static Block stripped_spruce_wall = register("stripped_spruce_wall", true, new WallBlock(Block.Properties.from(Blocks.SPRUCE_LOG)));
	public static Block stripped_birch_wall = register("stripped_birch_wall", true, new WallBlock(Block.Properties.from(Blocks.BIRCH_LOG)));
	public static Block stripped_jungle_wall = register("stripped_jungle_wall", true, new WallBlock(Block.Properties.from(Blocks.JUNGLE_LOG)));
	public static Block stripped_acacia_wall = register("stripped_acacia_wall", true, new WallBlock(Block.Properties.from(Blocks.ACACIA_LOG)));
	public static Block stripped_dark_oak_wall = register("stripped_dark_oak_wall", true, new WallBlock(Block.Properties.from(Blocks.DARK_OAK_LOG)));
	public static Block spruce_ladder = register("spruce_ladder", true, new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
	public static Block birch_ladder = register("birch_ladder", true, new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
	public static Block jungle_ladder = register("jungle_ladder", true, new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
	public static Block acacia_ladder = register("acacia_ladder", true, new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
	public static Block dark_oak_ladder = register("dark_oak_ladder", true, new VeLadderBlock(Block.Properties.from(Blocks.LADDER)));
	public static Block iron_ladder = register("iron_ladder", true, new VeLadderBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(0.4F, 5.0F).sound(SoundType.METAL).notSolid()));
	public static Block modern_door = register("modern_door", true, new VeSlimDoorBlock(Block.Properties.create(Material.ANVIL, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL)));
	public static Block dirt_slab = register("dirt_slab", true, new SlabBlock(Block.Properties.from(Blocks.DIRT)));
	public static Block dirt_stairs = register("dirt_stairs", true, new StairsBlock(() -> Blocks.DIRT.getDefaultState(), Block.Properties.from(Blocks.DIRT)));
	public static Block coarse_dirt_slab = register("coarse_dirt_slab", true, new SlabBlock(Block.Properties.from(Blocks.COARSE_DIRT)));
	public static Block coarse_dirt_stairs = register("coarse_dirt_stairs", true, new StairsBlock(() -> Blocks.COARSE_DIRT.getDefaultState(), Block.Properties.from(Blocks.COARSE_DIRT)));
	
	public static Block oak_frame = register("oak_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	public static Block spruce_frame = register("spruce_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	public static Block birch_frame = register("birch_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	public static Block jungle_frame = register("jungle_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	public static Block acacia_frame = register("acacia_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	public static Block dark_oak_frame = register("dark_oak_frame", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
	
	/*
	public static Block oak_fire_painting = register("oak_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block spruce_fire_painting = register("spruce_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block birch_fire_painting = register("birch_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block jungle_fire_painting = register("jungle_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block acacia_fire_painting = register("acacia_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block dark_oak_fire_painting = register("dark_oak_fire_painting", true, new VeFrameBlock(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.WOOD)));
	public static Block oak_water_painting = register("oak_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.oak_fire_painting)));
	public static Block spruce_water_painting = register("spruce_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.spruce_fire_painting)));
	public static Block birch_water_painting = register("birch_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.birch_fire_painting)));
	public static Block jungle_water_painting = register("jungle_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.jungle_fire_painting)));
	public static Block acacia_water_painting = register("acacia_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.acacia_fire_painting)));
	public static Block dark_oak_water_painting = register("dark_oak_water_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.dark_oak_fire_painting)));
	public static Block oak_earth_painting = register("oak_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.oak_fire_painting)));
	public static Block spruce_earth_painting = register("spruce_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.spruce_fire_painting)));
	public static Block birch_earth_painting = register("birch_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.birch_fire_painting)));
	public static Block jungle_earth_painting = register("jungle_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.jungle_fire_painting)));
	public static Block acacia_earth_painting = register("acacia_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.acacia_fire_painting)));
	public static Block dark_oak_earth_painting = register("dark_oak_earth_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.dark_oak_fire_painting)));
	public static Block oak_wind_painting = register("oak_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.oak_fire_painting)));
	public static Block spruce_wind_painting = register("spruce_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.spruce_fire_painting)));
	public static Block birch_wind_painting = register("birch_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.birch_fire_painting)));
	public static Block jungle_wind_painting = register("jungle_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.jungle_fire_painting)));
	public static Block acacia_wind_painting = register("acacia_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.acacia_fire_painting)));
	public static Block dark_oak_wind_painting = register("dark_oak_wind_painting", true, new VeFrameBlock(Block.Properties.from(VeBlocks.dark_oak_fire_painting)));
	*/
	
	//public static Block oak_wanderer_painting = register("oak_wanderer_painting", true, new VeTwoHighPaintingBlock(Block.Properties.from(VeBlocks.oak_fire_painting)));
	public static Block snow_bricks = register("snow_bricks", true, new Block(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).hardnessAndResistance(VeBlock.STONE_HARDNESS, VeBlock.STONE_RESISTANCE).sound(SoundType.STONE)));
	public static Block snow_brick_stairs = register("snow_brick_stairs", true, new StairsBlock(() -> VeBlocks.snow_bricks.getDefaultState(), Block.Properties.from(VeBlocks.snow_bricks)));
	public static Block snow_brick_slab = register("snow_brick_slab", true, new SlabBlock(Block.Properties.from(VeBlocks.snow_bricks)));
	public static Block snow_brick_wall = register("snow_brick_wall", true, new WallBlock(Block.Properties.from(VeBlocks.snow_bricks)));
	public static Block packed_snow_block = register("packed_snow_block", true, new Block(Block.Properties.create(Material.SAND, MaterialColor.LIGHT_BLUE).hardnessAndResistance(VeBlock.SNOW_HARDNESS, VeBlock.SNOW_RESISTANCE).sound(SoundType.SNOW).slipperiness(0.98F)));
	public static Block packed_snow_stairs = register("packed_snow_stairs", true, new StairsBlock(() -> VeBlocks.packed_snow_block.getDefaultState(), Block.Properties.from(VeBlocks.packed_snow_block)));
	public static Block packed_snow_slab = register("packed_snow_slab", true, new SlabBlock(Block.Properties.from(VeBlocks.packed_snow_block)));
	public static Block spider_glass = register("spider_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block spider_glass_pane = register("spider_glass_pane", true, new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block black_spider_glass = register("black_spider_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block black_spider_glass_pane = register("black_spider_glass_pane", true, new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block fancy_spider_glass = register("fancy_spider_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block fancy_spider_glass_pane = register("fancy_spider_glass_pane", true, new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block black_fancy_spider_glass = register("black_fancy_spider_glass", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block black_fancy_spider_glass_pane = register("black_fancy_spider_glass_pane", true, new VePaneBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block turkey = register("turkey", true, new VeTurkeyBlock(Block.Properties.create(Material.CAKE, MaterialColor.BROWN).hardnessAndResistance(VeBlock.CLOTH_HARDNESS).sound(SoundType.SNOW)), 1);
	public static Block glass_of_darkness = register("glass_of_darkness", true, new VeGlassBlock(Block.Properties.from(Blocks.GLASS)));
	public static Block purple_mushroom = register("purple_mushroom", true, new VeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM)));
	public static Block purple_mushroom_block = register("purple_mushroom_block", true, new VePurpleHugeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM_BLOCK)));
	public static Block woodcutter = register("woodcutter", true, new VeWoodcutterBlock(Block.Properties.from(Blocks.STONECUTTER)));
	public static Block white_campfire = register("white_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block orange_campfire = register("orange_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block magenta_campfire = register("magenta_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block light_blue_campfire = register("light_blue_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block yellow_campfire = register("yellow_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block lime_campfire = register("lime_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block pink_campfire = register("pink_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block gray_campfire = register("gray_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block light_gray_campfire = register("light_gray_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block cyan_campfire = register("cyan_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block purple_campfire = register("purple_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block blue_campfire = register("blue_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block brown_campfire = register("brown_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block green_campfire = register("green_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block red_campfire = register("red_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block black_campfire = register("black_campfire", true, new VeColoredCampfireBlock(Block.Properties.from(Blocks.CAMPFIRE)));
	public static Block witchs_cradle = register("witchs_cradle", false, new VeBerryBushBlock(Block.Properties.from(Blocks.WHEAT)));
	public static Block void_liquid = register("void", false, new VeFlowingVoidBlock(() -> VeFluids.FLOWING_VOID, Block.Properties.create(Material.WATER, MaterialColor.BLACK).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static Block bamboo_fence = register("bamboo_fence", true, new FenceBlock(Block.Properties.from(Blocks.BAMBOO)));
	public static Block bamboo_fence_gate = register("bamboo_fence_gate", true, new FenceGateBlock(Block.Properties.from(Blocks.BAMBOO)));
	public static Block bamboo_wall = register("bamboo_wall", true, new WallBlock(Block.Properties.from(Blocks.BAMBOO)));
	public static Block blue_sleeping_bag = register("blue_sleeping_bag", true, new VeSleepingBagBlock(DyeColor.BLUE, Block.Properties.from(Blocks.BLUE_BED)));
	public static Block snowflake_obsidian = register("snowflake_obsidian", true, new Block(Block.Properties.from(Blocks.OBSIDIAN)));
	public static Block nephilite = register("nephilite", true, new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.3F).sound(SoundType.STONE)));
	public static Block nephilite_slab = register("nephilite_slab", true, new SlabBlock(Block.Properties.from(VeBlocks.nephilite)));
	public static Block nephilite_stairs = register("nephilite_stairs", true, new StairsBlock(() -> VeBlocks.nephilite.getDefaultState(), Block.Properties.from(VeBlocks.nephilite)));
	public static Block nephilite_wall = register("nephilite_wall", true, new WallBlock(Block.Properties.from(VeBlocks.nephilite)));
	public static Block double_slab = register("double_slab", false, new VeDoubleSlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).notSolid()));
	
	//Vanilla Replacements
	
	/*
	public static final Block PRISMARINE_SLAB = register("prismarine_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.PRISMARINE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PRISMARINE_BRICK_SLAB = register("prismarine_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.PRISMARINE_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block DARK_PRISMARINE_SLAB = register("dark_prismarine_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.DARK_PRISMARINE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block OAK_SLAB = register("oak_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.OAK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SPRUCE_SLAB = register("spruce_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SPRUCE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block BIRCH_SLAB = register("birch_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.BIRCH_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block JUNGLE_SLAB = register("jungle_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.JUNGLE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block ACACIA_SLAB = register("acacia_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.ACACIA_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block DARK_OAK_SLAB = register("dark_oak_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.DARK_OAK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block STONE_SLAB = register("stone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.STONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SMOOTH_STONE_SLAB = register("smooth_stone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SMOOTH_STONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SANDSTONE_SLAB = register("sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CUT_SANDSTONE_SLAB = register("cut_sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.CUT_SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PETRIFIED_OAK_SLAB = register("petrified_oak_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.PETRIFIED_OAK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block COBBLESTONE_SLAB = register("cobblestone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.COBBLESTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block BRICK_SLAB = register("brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block STONE_BRICK_SLAB = register("stone_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.STONE_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block NETHER_BRICK_SLAB = register("nether_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.NETHER_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block QUARTZ_SLAB = register("quartz_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.QUARTZ_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block RED_SANDSTONE_SLAB = register("red_sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.RED_SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CUT_RED_SANDSTONE_SLAB = register("cut_red_sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.CUT_RED_SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PURPUR_SLAB = register("purpur_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.PURPUR_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block POLISHED_GRANITE_SLAB = register("polished_granite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.POLISHED_GRANITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SMOOTH_RED_SANDSTONE_SLAB = register("smooth_red_sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SMOOTH_RED_SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block MOSSY_STONE_BRICK_SLAB = register("mossy_stone_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.MOSSY_STONE_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block POLISHED_DIORITE_SLAB = register("polished_diorite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.POLISHED_DIORITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block MOSSY_COBBLESTONE_SLAB = register("mossy_cobblestone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.MOSSY_COBBLESTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block END_STONE_BRICK_SLAB = register("end_stone_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.END_STONE_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SMOOTH_SANDSTONE_SLAB = register("smooth_sandstone_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SMOOTH_SANDSTONE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block SMOOTH_QUARTZ_SLAB = register("smooth_quartz_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.SMOOTH_QUARTZ_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block GRANITE_SLAB = register("granite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.GRANITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block ANDESITE_SLAB = register("andesite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.ANDESITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block RED_NETHER_BRICK_SLAB = register("red_nether_brick_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.RED_NETHER_BRICK_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block POLISHED_ANDESITE_SLAB = register("polished_andesite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.POLISHED_ANDESITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	public static final Block DIORITE_SLAB = register("diorite_slab", true, new VeSlabBlock(Block.Properties.from(Blocks.DIORITE_SLAB)), ItemGroup.BUILDING_BLOCKS);
	*/
	
	private static Block register(String name, boolean hasItemBlock, Block block)
	{
		return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), hasItemBlock, block, new Item.Properties().group(VanillaExpansions.VE_GROUP));
	}
	
	private static Block register(String name, boolean hasItemBlock, Block block, int size)
	{
		return register(new ResourceLocation(VanillaExpansions.MOD_ID, name), hasItemBlock, block, new Item.Properties().group(VanillaExpansions.VE_GROUP).maxStackSize(size));
	}
	
	@SuppressWarnings("unused")
	private static Block register(String name, boolean hasItemBlock, Block block, ItemGroup group)
	{
		return register(new ResourceLocation(name), hasItemBlock, block, new Item.Properties().group(group));
	}
	
	private static Block register(ResourceLocation name, boolean hasItemBlock, Block block, Item.Properties properties)
	{
		return register(name, hasItemBlock, block, new BlockItem(block, properties));
	}
  
	private static Block register(ResourceLocation name, boolean hasItemBlock, Block block, BlockItem item)
	{
		block.setRegistryName(name);
		BLOCKS.add(block);
		if(hasItemBlock && block.getRegistryName() != null)
		{
			item.setRegistryName(name);
			ITEMS.add(item);
		}
		return block;
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		BLOCKS.forEach(block -> event.getRegistry().register(block));
		BLOCKS.clear();
		VanillaExpansions.LOGGER.info("Blocks registered.");
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
	{
		ITEMS.forEach(item -> event.getRegistry().register(item));
		ITEMS.clear();
		VanillaExpansions.LOGGER.info("Item blocks registered.");
	}
}
