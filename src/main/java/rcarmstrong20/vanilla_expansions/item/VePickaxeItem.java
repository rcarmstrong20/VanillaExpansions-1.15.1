package rcarmstrong20.vanilla_expansions.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;

public class VePickaxeItem extends PickaxeItem
{
	public VePickaxeItem(IItemTier tier, int attackDamageIn, Properties builder)
	{
		super(tier, attackDamageIn, -2.8F, builder);
	}
}
