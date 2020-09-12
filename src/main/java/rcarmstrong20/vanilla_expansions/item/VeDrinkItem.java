package rcarmstrong20.vanilla_expansions.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeItems;

/**
 *
 * @author Ryan
 *
 */
public class VeDrinkItem extends Item
{
    public VeDrinkItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
            if (this == VeItems.blood_vial)
            {
                ((PlayerEntity) entityLiving).addItemStackToInventory(new ItemStack(VeItems.glass_vial));
            }
            else
            {
                ((PlayerEntity) entityLiving).addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.DRINK;
    }
}
