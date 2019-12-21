package rcarmstrong20.vanilla_expansions.enums;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public enum VeArmorMaterial implements IArmorMaterial
{
	EMERALD("ve:emerald", 18, new int[]{2, 6, 7, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, () -> {
		return Ingredient.fromItems(Items.EMERALD);
	}),
	RUBY("ve:ruby", 51, new int[]{4, 10, 7, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, () -> {
		return Ingredient.fromItems(VeItems.ruby);
	}),
	AIR_IRON("ve:air_iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.fromItems(Items.IRON_INGOT);
	}),
	AIR_GOLD("ve:air_gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, () -> {
		return Ingredient.fromItems(Items.GOLD_INGOT);
	}),
	AIR_DIAMOND("ve:air_diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> {
		return Ingredient.fromItems(Items.DIAMOND);
	}),
	AIR_EMERALD("ve:air_emerald", 34, new int[]{2, 6, 7, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, () -> {
		return Ingredient.fromItems(Items.EMERALD);
	}),
	AIR_RUBY("ve:air_ruby", 51, new int[]{4, 10, 7, 4}, 11, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, () -> {
		return Ingredient.fromItems(VeItems.ruby);
	});
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	   private final String name;
	   private final int maxDamageFactor;
	   private final int[] damageReductionAmountArray;
	   private final int enchantability;
	   private final SoundEvent soundEvent;
	   private final float toughness;
	   private final LazyValue<Ingredient> repairMaterial;

	   private VeArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
	      this.name = nameIn;
	      this.maxDamageFactor = maxDamageFactorIn;
	      this.damageReductionAmountArray = damageReductionAmountsIn;
	      this.enchantability = enchantabilityIn;
	      this.soundEvent = equipSoundIn;
	      this.toughness = toughness;
	      this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	   }

	   public int getDurability(EquipmentSlotType slotIn) {
	      return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	   }

	   public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	      return this.damageReductionAmountArray[slotIn.getIndex()];
	   }

	   public int getEnchantability() {
	      return this.enchantability;
	   }

	   public SoundEvent getSoundEvent() {
	      return this.soundEvent;
	   }

	   public Ingredient getRepairMaterial() {
	      return this.repairMaterial.getValue();
	   }

	   @OnlyIn(Dist.CLIENT)
	   public String getName() {
	      return this.name;
	   }

	   public float getToughness() {
	      return this.toughness;
	   }
}