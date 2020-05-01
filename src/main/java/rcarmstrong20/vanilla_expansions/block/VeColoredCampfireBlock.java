package rcarmstrong20.vanilla_expansions.block;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.tile_entity.VeColoredCampfireTileEntity;

public class VeColoredCampfireBlock extends CampfireBlock
{
	public VeColoredCampfireBlock(Properties propertiesIn)
	{
		super(propertiesIn);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
	{
		if (state.get(LIT))
		{
			VeColoredCampfireTileEntity campfiretileentity = (VeColoredCampfireTileEntity) worldIn.getTileEntity(pos);
			ItemStack itemstack = player.getHeldItem(handIn);
			Optional<CampfireCookingRecipe> optional = campfiretileentity.findMatchingRecipe(itemstack);
			
			if (optional.isPresent())
			{
				if (!worldIn.isRemote && campfiretileentity.addItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack, optional.get().getCookTime()))
				{
					player.addStat(Stats.INTERACT_WITH_CAMPFIRE);
					return ActionResultType.SUCCESS;
				}
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if(tileentity instanceof VeColoredCampfireTileEntity)
			{
				VeColoredCampfireTileEntity campfireTileEntity = (VeColoredCampfireTileEntity) tileentity;
				InventoryHelper.dropItems(worldIn, pos, campfireTileEntity.getInventory());
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn)
	{
		if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER)
		{
			boolean flag = state.get(LIT);
			if (flag)
			{
				if (worldIn.isRemote())
				{
					for(int i = 0; i < 20; ++i)
					{
						spawnSmokeParticles(worldIn.getWorld(), pos, state.get(SIGNAL_FIRE), true);
					}
				}
				else
				{
					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				
				TileEntity tileentity = worldIn.getTileEntity(pos);
				((VeColoredCampfireTileEntity)tileentity).dropAllItems();
			}
			worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)).with(LIT, Boolean.valueOf(false)), 3);
			worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new VeColoredCampfireTileEntity();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
	}
}
