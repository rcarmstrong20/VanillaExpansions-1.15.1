package rcarmstrong20.vanilla_expansions.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.core.VeEntityType;

public class VeRabbitEntity extends RabbitEntity
{
	public VeRabbitEntity(EntityType<? extends RabbitEntity> rabbitEntity, World world)
	{
		super(rabbitEntity, world);
	}
	
	/**
	 * Adds the functionality to change a white bunny to a killer bunny with a name tag.
	 */
	@Override
	public void baseTick()
	{
		super.baseTick();
		if(this.getRabbitType() == 1 || this.getRabbitType() == 99 && this.hasCustomName())
		{
			this.setRabbitType("The Killer Bunny".equals(this.getName().getUnformattedComponentText()) ? 99 : 1);
		}
	}
	
	/*
	 * Fix the spawn egg bug with spawning a baby rabbit instead of a baby pig
	 */
	@Override
	public RabbitEntity createChild(AgeableEntity ageable)
	{
		VeRabbitEntity rabbitEntity = VeEntityType.rabbit.create(this.world);
		int parentType = this.getRabbitType();
		rabbitEntity.setRabbitType(parentType);
		return rabbitEntity;
	}
}
