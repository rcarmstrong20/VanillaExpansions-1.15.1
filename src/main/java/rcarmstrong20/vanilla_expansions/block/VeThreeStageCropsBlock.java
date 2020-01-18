package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeThreeStageCropBlock extends VeSevenStageCropBlock
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
	private static final VoxelShape[] ONION_SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D), Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D), Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};
	private static final VoxelShape[] GINGER_SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	
	public VeThreeStageCropBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public IntegerProperty getAgeProperty()
	{
		return AGE;
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if(this == VeBlocks.green_onions || this == VeBlocks.garlic)
		{
			return ONION_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
		}
		return GINGER_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
	}
	
	@Override
	public int getMaxAge()
	{
		return 3;
	}
	
	@Override
	protected IItemProvider getSeedsItem()
	{
		if(this == VeBlocks.green_onions)
		{
			return VeItems.green_onion;
		}
		else if(this == VeBlocks.garlic)
		{
			return VeItems.garlic;
		}
		else if(this == VeBlocks.ginger)
		{
			return VeItems.ginger_root;
		}
		return Items.WHEAT_SEEDS;
	}
}
