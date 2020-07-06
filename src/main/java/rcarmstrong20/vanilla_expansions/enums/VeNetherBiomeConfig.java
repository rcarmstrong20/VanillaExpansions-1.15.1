package rcarmstrong20.vanilla_expansions.enums;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public enum VeNetherBiomeConfig
{
	NETHER(Biomes.NETHER);
	
	private final Biome biome;
	
	private VeNetherBiomeConfig(Biome biome)
	{
		this.biome = biome;
	}
	
	public Biome getBiome()
	{
		return this.biome;
	}
}
