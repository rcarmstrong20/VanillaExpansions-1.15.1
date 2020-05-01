package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VeSlabBlock extends SlabBlock
{
	public VeSlabBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		Block itemblock = Block.getBlockFromItem(context.getItem().getItem());
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		
		if(itemblock != blockstate.getBlock() && blockstate.getBlock() instanceof SlabBlock)
		{
			if(blockstate.get(SlabBlock.TYPE) == SlabType.BOTTOM)
			{
				VeDoubleSlabBlock.fillInventory(itemblock, blockstate.getBlock());
			}
			else
			{
				VeDoubleSlabBlock.fillInventory(blockstate.getBlock(), itemblock);
			}
			return VeBlocks.double_slab.getDefaultState();
		}
		else
		{
			return super.getStateForPlacement(context);
		}
	}
	
	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		Block itemblock = Block.getBlockFromItem(useContext.getItem().getItem());
		SlabType slabtype = state.get(TYPE);
		
		if(slabtype != SlabType.DOUBLE && state.getBlock() != itemblock && itemblock instanceof VeSlabBlock)
		{
			if (useContext.replacingClickedOnBlock())
			{
				boolean flag = useContext.getHitVec().y - (double)useContext.getPos().getY() > 0.5D;
				Direction direction = useContext.getFace();
				if (slabtype == SlabType.BOTTOM)
				{
					return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
				}
				else
				{
					return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
				}
			}
			else
			{
				return true;
			}
		}
		else
		{
			return super.isReplaceable(state, useContext);
		}
	}
}
