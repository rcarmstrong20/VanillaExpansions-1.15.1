package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeItems;

public class VeBerryBushBlock extends SweetBerryBushBlock
{
	private static final VoxelShape WITCHS_CRADLE_STAGE_0_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);
	
	public VeBerryBushBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		int currentAge = state.get(AGE);
		boolean maxAgeFlag = currentAge == this.getMaxAge();
		
		if (!maxAgeFlag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL)
		{
			return ActionResultType.PASS;
		}
		else if(currentAge > 1)
		{
			spawnDrops(state, world, pos);
			world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
			world.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
			return ActionResultType.SUCCESS;
		}
		return super.onBlockActivated(state, world, pos, player, handIn, hit);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		if(this.getBlock() == VeBlocks.witchs_cradle && state.get(AGE) == 0)
		{
			return WITCHS_CRADLE_STAGE_0_SHAPE;
		}
		else
		{
			return super.getShape(state, world, pos, context);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
	{
		Block block = this.getBlock();
		
		if(block == VeBlocks.blueberry_bush)
		{
			return new ItemStack(VeItems.blueberries);
		}
		else if(block == VeBlocks.cranberry_bush)
		{
			return new ItemStack(VeItems.cranberries);
		}
		else if(block == VeBlocks.witchs_cradle)
		{
			return new ItemStack(VeItems.witchs_cradle_branch);
		}
		return super.getItem(world, pos, state);
	}
	
	private int getMaxAge()
	{
		return 3;
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entityIn)
	{
		if(this.getBlock() == VeBlocks.witchs_cradle)
		{
			super.onEntityCollision(state, world, pos, entityIn);
		}
		else
		{
			if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE)
			{
				entityIn.setMotionMultiplier(state, new Vec3d((double)0.8F, 0.75D, (double)0.8F));
			}
		}
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos)
	{
		return PlantType.Plains;
	}
}
