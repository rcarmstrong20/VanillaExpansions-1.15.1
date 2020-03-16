package rcarmstrong20.vanilla_expansions.block;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeFluidTags;

public class VeFlowingVoidBlock extends FlowingFluidBlock
{
	public VeFlowingVoidBlock(Supplier<? extends FlowingFluid> supplier, Properties builder)
	{
		super(supplier, builder);
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return false;
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if(entityIn instanceof LivingEntity && this.getFluid().isIn(VeFluidTags.VOID))
		{
			((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 2, true, true));
			((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 20, 5, true, true));
		}
	}
}
