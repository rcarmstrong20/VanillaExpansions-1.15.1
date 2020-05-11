package rcarmstrong20.vanilla_expansions.tile_entity;

import javax.annotation.Nullable;

import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import rcarmstrong20.vanilla_expansions.client.renderer.model.VeModelProperties;
import rcarmstrong20.vanilla_expansions.core.VeTileEntityType;

public class VeDoubleSlabTileEntity extends TileEntity implements IClearable
{
	private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
	
	public VeDoubleSlabTileEntity()
	{
		super(VeTileEntityType.double_slab);
	}
	
	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.inventory.clear();
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.inventory, true);
		return compound;
	}
	
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(this.getPos(), 1, this.getUpdateTag());
	}
	
	@Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
		//RenderUtil.causeRenderUpdate(this.getPos());
        CompoundNBT compound = pkt.getNbtCompound();
        this.read(compound);
    }
	
	@Override
	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
	
	/*
	 * Add an item to the specified index in the inventory list
	 */
	public boolean addItem(ItemStack stack, int i)
	{
		this.inventory.set(i, stack.split(1));
		this.inventoryChanged();
		
		return true;
	}
	
	private void inventoryChanged()
	{
		this.markDirty();
		this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
	}
	
	/**
	 * Returns a NonNullList<ItemStack> of items currently held in the double slab.
	 */
	public NonNullList<ItemStack> getInventory()
	{
		return this.inventory;
	}
	
	@Override
	public void clear()
	{
		this.inventory.clear();
	}
	
	public void dropAllItems()
	{
		if (!this.getWorld().isRemote)
		{
			InventoryHelper.dropItems(this.getWorld(), this.getPos(), this.getInventory());
		}
		this.inventoryChanged();
	}
	
	@Override
	public IModelData getModelData()
	{
		ModelDataMap.Builder builder = new ModelDataMap.Builder();
	    builder.withInitial(VeModelProperties.TILE_ENTITY_DOUBLE_SLAB, this);
	    ModelDataMap data = builder.build();
	    if (!this.getInventory().isEmpty())
	    {
	    	data.setData(VeModelProperties.TILE_ENTITY_DOUBLE_SLAB, this);
	    }
	    return (IModelData)data;
	}
}
