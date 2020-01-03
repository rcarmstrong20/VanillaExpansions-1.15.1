package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public class VeCampfireBlock extends CampfireBlock
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	
	public VeCampfireBlock(Block.Properties properties)
	{
		super(properties);
	}
	
	public static boolean func_226914_b_(World p_226914_0_, BlockPos p_226914_1_, int p_226914_2_)
	{
		for(int i = 1; i <= p_226914_2_; ++i)
		{
			BlockPos blockpos = p_226914_1_.down(i);
			BlockState blockstate = p_226914_0_.getBlockState(blockpos);
			if (func_226915_i_(blockstate))
			{
				return true;
			}
			
			boolean flag = VoxelShapes.compare(SHAPE, blockstate.getCollisionShape(p_226914_0_, p_226914_1_, ISelectionContext.dummy()), IBooleanFunction.AND);
			if (flag)
			{
				BlockState blockstate1 = p_226914_0_.getBlockState(blockpos.down());
				return func_226915_i_(blockstate1);
			}
		}
		return false;
	}
	
	private static boolean func_226915_i_(BlockState blockState)
	{
		return blockState.get(LIT);
	}
}
