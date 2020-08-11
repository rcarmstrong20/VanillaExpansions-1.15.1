package rcarmstrong20.vanilla_expansions.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import rcarmstrong20.vanilla_expansions.core.VeBlockTags;

public class VeSnapdragonBlock extends FlowerBlock
{
	public VeSnapdragonBlock(Effect effect, int effectDuration, Properties properties)
	{
		super(effect, effectDuration, properties);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return isValidBlock(worldIn, pos);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return isValidBlock(worldIn, pos);
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable)
	{
		return isValidBlock(world, pos);
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double x = (double)pos.getX() + rand.nextFloat();
		double y = (double)pos.getY() + rand.nextFloat();
		double z = (double)pos.getZ() + rand.nextFloat();
		
		worldIn.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
		super.animateTick(stateIn, worldIn, pos, rand);
	}
	
	/**
	 * A helper method used for checking that the plant placement is valid.
	 */
	private boolean isValidBlock(IBlockReader world, BlockPos pos)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		
		if(block instanceof VePlantingPotBlock)
		{
			return VeBlockTags.END_PLANTABLE.contains(block) && VeBlockTags.END_POTTABLE.contains(this.getBlock());
		}
		else
		{
			return VeBlockTags.END_PLANTABLE.contains(block);
		}
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if(!world.isRemote && entity instanceof PlayerEntity)
		{
			PlayerEntity playerEntity = (PlayerEntity) entity;
			
			double x = playerEntity.getPosX();
	        double y = playerEntity.getPosY();
	        double z = playerEntity.getPosZ();
			
	        if(!playerEntity.isSneaking() && playerEntity.getMotion().getY() == 0.0D)
	        {
	        	for(int i = 0; i < 16; ++i)
				{
	        		double newX = playerEntity.getPosX() + (playerEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
	        		double newY = MathHelper.clamp(playerEntity.getPosY() + (double)(playerEntity.getRNG().nextInt(16) - 8), 0.0D, (double)(world.getActualHeight() - 1));
	        		double newZ = playerEntity.getPosZ() + (playerEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
	        		if (playerEntity.isPassenger())
	        		{
	        			playerEntity.stopRiding();
	        		}
	        		
	        		if (playerEntity.attemptTeleport(newX, newY, newZ, true))
	        		{
	        			world.playSound((PlayerEntity)null, x, y, z, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
	        			playerEntity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
	        			break;
	        		}
				}
	        }
		}
	}
}
