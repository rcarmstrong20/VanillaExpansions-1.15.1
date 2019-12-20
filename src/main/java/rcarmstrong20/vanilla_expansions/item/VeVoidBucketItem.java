package rcarmstrong20.vanilla_expansions.item;

import java.util.function.Supplier;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

public class VeVoidBucketItem extends BucketItem
{
	public VeVoidBucketItem(Supplier<? extends Fluid> supplier, Properties properties)
	{
		super(supplier, properties);
	}
	
	/*
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		super.onItemUseFinish(stack, worldIn, entityLiving);
		
		return new ItemStack(Items.BUCKET);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		if(dimType == DimensionType.THE_END && itemstack == new ItemStack(Items.BUCKET))
		{
			return new ActionResult<>(ActionResultType.SUCCESS, new ItemStack(VeItems.void_bucket));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	*/
}
