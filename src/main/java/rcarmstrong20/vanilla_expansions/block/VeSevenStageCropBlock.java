package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.IItemProvider;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeSevenStageCropBlock extends CropsBlock
{
	public VeSevenStageCropBlock(Block.Properties properties) 
	{
		super(properties);
	}
	
	@Override
    protected IItemProvider getSeedsItem()
    {
		Block block = this.getBlock();
		
    	if(block == VeBlocks.bok_choy)
    	{
    		return VeItems.bok_choy_seeds;
    	}
    	else if(block == VeBlocks.quinoa)
    	{
    		return VeItems.quinoa;
    	}
		return Items.WHEAT_SEEDS;
    }
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(this.getAgeProperty());
	}
}
