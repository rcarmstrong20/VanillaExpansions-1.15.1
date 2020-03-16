package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeBerryBushBlock extends SweetBerryBushBlock
{
	private static final VoxelShape WITCHS_CRADLE_STAGE_0_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);
	
	public VeBerryBushBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if(this == VeBlocks.witchs_cradle && state.get(AGE) == 0)
		{
			return WITCHS_CRADLE_STAGE_0_SHAPE;
		}
		return super.getShape(state, worldIn, pos, context);
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
	{
		if(this == VeBlocks.blueberry_bush)
		{
			return new ItemStack(VeItems.blueberries);
		}
		else if(this == VeBlocks.cranberry_bush)
		{
			return new ItemStack(VeItems.cranberries);
		}
		else if(this == VeBlocks.witchs_cradle)
		{
			return new ItemStack(VeItems.witchs_cradle_branch);
		}
		return new ItemStack(Items.SWEET_BERRIES);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if(this == Blocks.SWEET_BERRY_BUSH || this == VeBlocks.witchs_cradle)
		{
			super.onEntityCollision(state, worldIn, pos, entityIn);
		}
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos)
	{
		return PlantType.Plains;
	}
}
