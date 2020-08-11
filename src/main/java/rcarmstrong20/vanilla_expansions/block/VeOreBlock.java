package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VeOreBlock extends OreBlock
{
	public VeOreBlock(Block.Properties properties) 
	{
		super(properties);
	}
	
    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch)
    {
       if (silktouch == 0)
       {
          int i = 0;
          if (this == VeBlocks.ruby_ore)
          {
             i = MathHelper.nextInt(RANDOM, 4, 8);
          }
          else if (this == VeBlocks.nether_smoky_quartz_ore) 
          {
             i = MathHelper.nextInt(RANDOM, 2, 5);
          }
          return i;
       }
       return 0;
    }
}
