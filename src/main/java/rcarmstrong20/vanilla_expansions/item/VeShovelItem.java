package rcarmstrong20.vanilla_expansions.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;

public class VeShovelItem extends ShovelItem
{
	public VeShovelItem(IItemTier tier, float attackDamageIn, Properties builder)
	{
		super(tier, attackDamageIn, -3.0F, builder);
	}
}
