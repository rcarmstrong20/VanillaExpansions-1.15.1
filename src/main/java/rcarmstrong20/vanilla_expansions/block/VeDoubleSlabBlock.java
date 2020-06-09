package rcarmstrong20.vanilla_expansions.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.tile_entity.VeDoubleSlabTileEntity;

public class VeDoubleSlabBlock extends ContainerBlock
{
	public static final List<Block> INVENTORY_HOLDER = new ArrayList<Block>();
	
	public VeDoubleSlabBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
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
	
	@Override
	public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity)
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			ItemStack itemStack = slabTileEntity.getInventory().get(0);
			ItemStack itemStack1 = slabTileEntity.getInventory().get(1);
			
			if(itemStack != ItemStack.EMPTY)
			{
				return Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getMaterial().getColor();
			}
			else
			{
				return Block.getBlockFromItem(itemStack1.getItem()).getDefaultState().getMaterial().getColor();
			}
		}
		return MaterialColor.AIR;
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
	public float getBlockHardness(BlockState blockState, IBlockReader worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity)
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			
			return Block.getBlockFromItem(slabTileEntity.getInventory().get(0).getItem()).getDefaultState().getBlockHardness(worldIn, pos);
		}
		return blockHardness;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof VeDoubleSlabTileEntity && !player.isCreative())
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileentity;
			
			//String heldItem = player.getHeldItem(player.getActiveHand()).getItem().getName().toString();
			
			//NonNullList<ItemStack> inventory = slabTileEntity.getInventory();
			
			//Block slab1 = Block.getBlockFromItem(inventory.get(0).getItem());
			//Block slab2 = Block.getBlockFromItem(inventory.get(1).getItem());
			
			//Material slab1Material = slab1.getDefaultState().getMaterial();
			//Material slab2Material = slab2.getDefaultState().getMaterial();
			
			//if()//!slab1Material.isToolNotRequired()|| !slab2Material.isToolNotRequired())
			//{
			InventoryHelper.dropItems(worldIn, pos, slabTileEntity.getInventory());
			//}
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	/*
	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof VeDoubleSlabTileEntity)
		{
			VeDoubleSlabTileEntity slabTileEntity = (VeDoubleSlabTileEntity) tileEntity;
			
			String heldItem = player.getHeldItem(player.getActiveHand()).getItem().getName().toString();
			
			NonNullList<ItemStack> inventory = slabTileEntity.getInventory();
			
			Block slab1 = Block.getBlockFromItem(inventory.get(0).getItem());
			Block slab2 = Block.getBlockFromItem(inventory.get(1).getItem());
			
			Material slab1Material = slab1.getDefaultState().getMaterial();
			Material slab2Material = slab2.getDefaultState().getMaterial();
			
			//return this.getHarvestTool(slab1.getDefaultState()).getName().equals(heldItem) || this.getHarvestTool(slab2.getDefaultState()).getName().equals(heldItem) ? true : false;
		}
		return super.canHarvestBlock(state, world, pos, player);
	}
	*/
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
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new VeDoubleSlabTileEntity();
	}
}
