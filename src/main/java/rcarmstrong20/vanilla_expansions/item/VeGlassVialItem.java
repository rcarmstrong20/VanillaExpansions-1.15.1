package rcarmstrong20.vanilla_expansions.item;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import rcarmstrong20.vanilla_expansions.core.VeItems;

/**
 *
 * @author Ryan
 *
 */
public class VeGlassVialItem extends Item
{
    private static final List<EntityType<?>> ANIMALS_THAT_GIVE_BLOOD = Arrays.asList(EntityType.SHEEP,
            EntityType.CHICKEN, EntityType.COW, EntityType.RABBIT, EntityType.PIG, EntityType.MOOSHROOM);

    public VeGlassVialItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if (stack.getItem() == VeItems.glass_vial)
        {
            if (target instanceof AnimalEntity && target.getHealth() > 0.0
                    && ANIMALS_THAT_GIVE_BLOOD.contains(target.getType()))
            {
                MobEntity animalTarget = (MobEntity) target;

                if (animalTarget.attackEntityFrom(DamageSource.GENERIC, 1))
                {
                    playerIn.addItemStackToInventory(new ItemStack(VeItems.blood_vial));
                    animalTarget.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
                    stack.shrink(1);
                    return true;
                }
            }
        }
        return false;
    }
}
