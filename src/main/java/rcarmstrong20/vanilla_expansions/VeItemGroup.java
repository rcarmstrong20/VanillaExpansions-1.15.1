package rcarmstrong20.vanilla_expansions;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import rcarmstrong20.vanilla_expansions.core.VeItems;

/**
 *
 * @author Ryan
 *
 */
public class VeItemGroup extends ItemGroup
{
    public VeItemGroup(String label)
    {
        super(label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(VeItems.ruby);
    }
}
