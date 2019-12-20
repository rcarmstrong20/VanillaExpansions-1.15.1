package rcarmstrong20.vanilla_expansions.enums;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public enum VeItemTier implements IItemTier
{
	EMERALD(2, 650, 6.0F, 2.0F, 22, () -> {
		return Ingredient.fromItems(Items.EMERALD);
	}),
	RUBY(4, 2872, 8.0F, 4.0F, 10, () -> {
		return Ingredient.fromItems(VeItems.ruby);
	});
	
	/** The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = WOOD/GOLD) */
	private final int harvestLevel;
	/** The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32) */
	private final int maxUses;
	/** The strength of this tool material against blocks which it is effective against. */
	private final float efficiency;
	/** Damage versus entities. */
	private final float attackDamage;
	/** Defines the natural enchantability factor of the material. */
	private final int enchantability;
	private final LazyLoadBase<Ingredient> repairMaterial;
	
	private VeItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
	{
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
	}
	
	public int getMaxUses()
	{
		return this.maxUses;
	}
	
	public float getEfficiency()
	{
		return this.efficiency;
	}
	
	public float getAttackDamage()
	{
		return this.attackDamage;
	}
	
	public int getHarvestLevel()
	{
		return this.harvestLevel;
	}
	
	public int getEnchantability()
	{
		return this.enchantability;
	}
	
	public Ingredient getRepairMaterial()
	{
		return this.repairMaterial.getValue();
	}
}
