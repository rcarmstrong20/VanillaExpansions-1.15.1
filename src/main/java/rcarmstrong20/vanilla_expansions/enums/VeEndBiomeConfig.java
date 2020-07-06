package rcarmstrong20.vanilla_expansions.enums;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public enum VeEndBiomeConfig
{
	SMALL_END_ISLANDS(Biomes.SMALL_END_ISLANDS),
	END_MIDLANDS(Biomes.END_MIDLANDS),
	END_HIGHLANDS(Biomes.END_HIGHLANDS),
	END_BARRENS(Biomes.END_BARRENS);
	
	private final Biome biome;
	
	private VeEndBiomeConfig(Biome biome)
	{
		this.biome = biome;
	}
	
	public Biome getBiome()
	{
		return this.biome;
	}
}
