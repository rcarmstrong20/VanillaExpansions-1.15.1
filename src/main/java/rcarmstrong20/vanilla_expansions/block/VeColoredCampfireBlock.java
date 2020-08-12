package rcarmstrong20.vanilla_expansions.block;

import java.util.Optional;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.particles.IParticleData;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.tile_entity.VeColoredCampfireTileEntity;

public class VeColoredCampfireBlock extends CampfireBlock
{
    private final IParticleData particle;

    public VeColoredCampfireBlock(IParticleData particle, Properties propertiesIn)
    {
        super(propertiesIn);
        this.particle = particle;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.get(LIT))
        {
            if (rand.nextInt(10) == 0)
            {
                worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                        SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(),
                        rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(5) == 0)
            {
                for (int i = 0; i < rand.nextInt(1) + 1; ++i)
                {
                    worldIn.addParticle(particle, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                            rand.nextFloat() / 2.0F, 5.0E-5D, rand.nextFloat() / 2.0F);
                }
            }
        }
    }

    /**
     * Called when the player right-clicks a block.
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult p_225533_6_)
    {
        if (state.get(LIT))
        {
            VeColoredCampfireTileEntity campfiretileentity = (VeColoredCampfireTileEntity) worldIn.getTileEntity(pos);
            ItemStack itemstack = player.getHeldItem(handIn);
            Optional<CampfireCookingRecipe> optional = campfiretileentity.findMatchingRecipe(itemstack);

            if (optional.isPresent())
            {
                if (!worldIn.isRemote && campfiretileentity.addItem(
                        player.abilities.isCreativeMode ? itemstack.copy() : itemstack, optional.get().getCookTime()))
                {
                    player.addStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.PASS;
    }

    /**
     * Called when the player right-clicks this block with a new block.
     */
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof VeColoredCampfireTileEntity)
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
                    for (int i = 0; i < 20; ++i)
                    {
                        spawnSmokeParticles(worldIn.getWorld(), pos, state.get(SIGNAL_FIRE), true);
                    }
                }
                else
                {
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                TileEntity tileentity = worldIn.getTileEntity(pos);
                ((VeColoredCampfireTileEntity) tileentity).dropAllItems();
            }
            worldIn.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(WATERLOGGED, Boolean.valueOf(true))
                    .with(LIT, Boolean.valueOf(false)), 3);
            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(),
                    fluidStateIn.getFluid().getTickRate(worldIn));
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

    /**
     * Creates a list of properties that this block can have.
     */
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder)
    {
        builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
    }
}
