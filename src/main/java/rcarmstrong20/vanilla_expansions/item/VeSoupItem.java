package rcarmstrong20.vanilla_expansions.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeItems;

/**
 *
 * @author Ryan
 *
 */
public class VeSoupItem extends Item
{
    public VeSoupItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity livingEntityIn)
    {
        super.onItemUseFinish(stack, worldIn, livingEntityIn);
        Item item = this.getItem();

        if (item == VeItems.noodle_soup)
        {
            return new ItemStack(VeItems.noodle_bowl);
        }
        else if (item == VeItems.void_bucket)
        {
            return new ItemStack(Items.BUCKET);
        }
        else if (item == VeItems.caramel_apple)
        {
            return new ItemStack(Items.STICK);
        }
        return new ItemStack(Items.BOWL);
    }
}
