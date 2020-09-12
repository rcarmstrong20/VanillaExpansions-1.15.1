package rcarmstrong20.vanilla_expansions.item;

import java.util.Random;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeItemTags;

/**
 *
 * @author Ryan
 *
 */
public class VeMixedSeedPacketItem extends Item
{
    public VeMixedSeedPacketItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        Random random = new Random();
        ItemStack heldStack = player.getHeldItem(hand);

        if (!world.isRemote())
        {
            for (int i = 0; i <= 2; i++)
            {
                Item randomSeed = VeItemTags.PACKET_SEEDS.getRandomElement(random);
                int randomCount = random.nextInt(i + 1) + 1;

                player.addItemStackToInventory(new ItemStack(randomSeed, randomCount));
            }
        }

        player.playSound(SoundEvents.BLOCK_BAMBOO_STEP, 1.0F, 1.0F);

        if (player.isCreative())
        {
            return ActionResult.resultPass(heldStack);
        }
        return ActionResult.resultConsume(new ItemStack(heldStack.getItem(), heldStack.getCount() - 1));
    }
}
