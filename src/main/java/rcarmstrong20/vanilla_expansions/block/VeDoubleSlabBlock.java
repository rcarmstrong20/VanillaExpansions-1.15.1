package rcarmstrong20.vanilla_expansions.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.tile_entity.VeDoubleSlabTileEntity;

public class VeDoubleSlabBlock extends Block
{
	public static final List<Block> INVENTORY_HOLDER = new ArrayList<Block>();
	
	public VeDoubleSlabBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity)
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			
			//Adds the current blocks held in the holder to the tile entity's inventory
			slabTileEntity.addItem(new ItemStack(INVENTORY_HOLDER.get(0)), 0);
			slabTileEntity.addItem(new ItemStack(INVENTORY_HOLDER.get(1)), 1);
			
			//Clears the inventory holder so it can hold new items
			INVENTORY_HOLDER.clear();
		}
	}
	
	/*
	 * This method fills the inventory holder keeping track of what blocks the double slab is made of before the tile entity is created.
	 */
	public static void fillInventory(Block block1, Block block2)
	{
		INVENTORY_HOLDER.add(block1);
		INVENTORY_HOLDER.add(block2);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity && !player.isCreative())
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			
			InventoryHelper.dropItems(worldIn, pos, slabTileEntity.getInventory());
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	/*
	 * Get the appropriate block from the inventory depending on if the top or bottom half of the block is clicked.
	 */
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity)
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			
			if(target.getHitVec().y - pos.getY() > 0.5D)
			{
				return slabTileEntity.getInventory().get(0);
			}
			return slabTileEntity.getInventory().get(1);
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new VeDoubleSlabTileEntity();
	}
}
