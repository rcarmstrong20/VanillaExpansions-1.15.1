package rcarmstrong20.vanilla_expansions.core;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

/**
 *
 * @author Ryan
 *
 */
public class VeFoods
{
    public static final Food VOID_BUCKET = new Food.Builder().setAlwaysEdible()
            .effect(() -> new EffectInstance(Effects.BLINDNESS, 600, 2, true, true), 1.0F)
            .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 600, 2, true, true), 1.0F)
            .effect(() -> new EffectInstance(Effects.WITHER, 200, 0, true, true), 1.0F)
            .effect(() -> new EffectInstance(Effects.WEAKNESS, 600, 1, true, true), 1.0F).build();

    public static final Food GARLIC = new Food.Builder().hunger(4).saturation(4.3F).build();
    public static final Food GREEN_ONION = new Food.Builder().hunger(2).saturation(2.2F).build();
    public static final Food QUINOA = new Food.Builder().hunger(3).saturation(2.5F).build();
    public static final Food BOK_CHOY = new Food.Builder().hunger(5).saturation(3.5F).build();
    public static final Food BLUEBERRIES = new Food.Builder().hunger(3).saturation(0.7F).build();
    public static final Food COOKED_NOODLES = new Food.Builder().hunger(4).saturation(5.5F).build();
    public static final Food NOODLE_SOUP = new Food.Builder().hunger(8).saturation(14.5F).build();
    public static final Food QUINOA_CERCEAL = new Food.Builder().hunger(12).saturation(20.5F).build();
    public static final Food CARAMEL_APPLE = new Food.Builder().hunger(5).saturation(6.5F).build();
    public static final Food WITCHS_CRADLE_BRANCH = new Food.Builder().hunger(4).saturation(0.6F).build();
    public static final Food WITCHS_CRADLE_SOUP = new Food.Builder().hunger(7).saturation(0.6F)
            .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 1000, 0, true, true), 1.0F).build();
    public static final Food BLOOD_VIAL = new Food.Builder().hunger(3).saturation(0.6F).build();
    public static final Food CRANBERRIES = new Food.Builder().hunger(2).saturation(0.5F).build();
    public static final Food CRANBERRY_SAUCE = new Food.Builder().hunger(5).saturation(1.5F).build();
    public static final Food GINGER_ROOT = new Food.Builder().hunger(3).saturation(4.5F).build();
    public static final Food CRISPY_SPIDER = new Food.Builder().hunger(3).saturation(13.5F).build();
    public static final Food GRILLED_GRUB_ON_A_STICK = new Food.Builder().hunger(5).saturation(15.5F).build();
    public static final Food MEAL_WORM_JERKY = new Food.Builder().hunger(4).saturation(16.5F).build();
    public static final Food CRISPY_BEATLE = new Food.Builder().hunger(5).saturation(18.5F).build();
    public static final Food SPRUCE_CONE = new Food.Builder().hunger(2).saturation(0.3F).build();
    public static final Food FORESTS_BOUNTY = new Food.Builder().hunger(5).saturation(0.5F).build();
}
