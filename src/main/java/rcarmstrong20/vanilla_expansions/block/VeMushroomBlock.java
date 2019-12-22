package rcarmstrong20.vanilla_expansions.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import rcarmstrong20.vanilla_expansions.core.VeBiomes;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeFeature;

public class VeMushroomBlock extends MushroomBlock
{
	public VeMushroomBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean func_226940_a_(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState, Random random)
	{
		serverWorld.removeBlock(blockPos, false);
		ConfiguredFeature<BigMushroomFeatureConfig, ?> configuredfeature;
		if (this == VeBlocks.purple_mushroom)
		{
			configuredfeature = VeFeature.HUGE_PURPLE_MUSHROOM.func_225566_b_(VeBiomes.HUGE_PURPLE_MUSHROOM_CONFIG);
		}
		else
		{
			return super.func_226940_a_(serverWorld, blockPos, blockState, random);
		}
		if (configuredfeature.place(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), random, blockPos))
		{
			return true;
		}
		return super.func_226940_a_(serverWorld, blockPos, blockState, random);
	}
}
