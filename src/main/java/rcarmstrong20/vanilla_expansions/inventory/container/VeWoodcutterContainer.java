package rcarmstrong20.vanilla_expansions.inventory.container;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeContainerTypes;
import rcarmstrong20.vanilla_expansions.core.VeRecipeTypes;
import rcarmstrong20.vanilla_expansions.core.VeSoundEvents;
import rcarmstrong20.vanilla_expansions.item.crafting.VeWoodcuttingRecipe;

public class VeWoodcutterContainer extends Container
{
	private final IWorldPosCallable WORLD_POS_CALLABLE;
	private final IntReferenceHolder SINGLE_INTERFACE_HOLDER = IntReferenceHolder.single();
	private final World world;
	private List<VeWoodcuttingRecipe> recipes = Lists.newArrayList();
	private ItemStack itemStack = ItemStack.EMPTY;
	private long field_217093_l;
	final Slot slot1;
	final Slot slot2;
	private Runnable inventoryUpdateListener = () -> {};
	
	public final IInventory inventory = new Inventory(1)
	{
		/**
		 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
		 * it hasn't changed and skip it.
		 */
		public void markDirty()
		{
			super.markDirty();
			VeWoodcutterContainer.this.onCraftMatrixChanged(this);
			VeWoodcutterContainer.this.inventoryUpdateListener.run();
		}
	};
	private final CraftResultInventory inventoryResult = new CraftResultInventory();
	
	public VeWoodcutterContainer(int p_i50059_1_, PlayerInventory playerInventory)
	{
		this(p_i50059_1_, playerInventory, IWorldPosCallable.DUMMY);
	}
	
	public VeWoodcutterContainer(int p_i50060_1_, PlayerInventory playerInventory, final IWorldPosCallable worldPosCallable)
	{
		super(VeContainerTypes.woodcutter, p_i50060_1_);
		this.WORLD_POS_CALLABLE = worldPosCallable;
		this.world = playerInventory.player.world;
		this.slot1 = this.addSlot(new Slot(this.inventory, 0, 20, 33));
		this.slot2 = this.addSlot(new Slot(this.inventoryResult, 1, 143, 33) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return false;
			}
			
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
			{
				ItemStack itemstack = VeWoodcutterContainer.this.slot1.decrStackSize(1);
				if (!itemstack.isEmpty())
				{
					VeWoodcutterContainer.this.func_217082_i();
				}
				
				stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
				worldPosCallable.consume((p_216954_1_, p_216954_2_) ->
				{
					long l = p_216954_1_.getGameTime();
					if (VeWoodcutterContainer.this.field_217093_l != l)
					{
						p_216954_1_.playSound((PlayerEntity)null, p_216954_2_, VeSoundEvents.UI_WOODCUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						VeWoodcutterContainer.this.field_217093_l = l;
					}
				});
				return super.onTake(thePlayer, stack);
			}
		});
		
		for(int i = 0; i < 3; ++i)
		{
			for(int j = 0; j < 9; ++j)
			{
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int k = 0; k < 9; ++k)
		{
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
		this.trackInt(this.SINGLE_INTERFACE_HOLDER);
	}
	
	@OnlyIn(Dist.CLIENT)
	public int func_217073_e()
	{
		return this.SINGLE_INTERFACE_HOLDER.get();
	}
	
	@OnlyIn(Dist.CLIENT)
	public List<VeWoodcuttingRecipe> getRecipeList()
	{
		return this.recipes;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getRecipeListSize()
	{
		return this.recipes.size();
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isSlot1Empty()
	{
		return this.slot1.getHasStack() && !this.recipes.isEmpty();
	}
	
	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return isWithinUsableDistance(this.WORLD_POS_CALLABLE, playerIn, VeBlocks.woodcutter);
	}
	
	/**
	 * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
	 */
	@Override
	public boolean enchantItem(PlayerEntity playerIn, int id)
	{
		if (id >= 0 && id < this.recipes.size())
		{
			this.SINGLE_INTERFACE_HOLDER.set(id);
			this.func_217082_i();
		}
		return true;
	}
	
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack itemstack = this.slot1.getStack();
		if (itemstack.getItem() != this.itemStack.getItem())
		{
			this.itemStack = itemstack.copy();
			this.updateAvailableRecipes(inventoryIn, itemstack);
		}
	}
	
	private void updateAvailableRecipes(IInventory p_217074_1_, ItemStack p_217074_2_)
	{
		this.recipes.clear();
		this.SINGLE_INTERFACE_HOLDER.set(-1);
		this.slot2.putStack(ItemStack.EMPTY);
		if (!p_217074_2_.isEmpty())
		{
			this.recipes = this.world.getRecipeManager().getRecipes(VeRecipeTypes.WOODCUTTING, p_217074_1_, this.world);
		}
	}
	
	private void func_217082_i()
	{
		if (!this.recipes.isEmpty())
		{
			VeWoodcuttingRecipe woodcuttingrecipe = this.recipes.get(this.SINGLE_INTERFACE_HOLDER.get());
			this.slot2.putStack(woodcuttingrecipe.getCraftingResult(this.inventory));
		}
		else
		{
			this.slot2.putStack(ItemStack.EMPTY);
		}
		this.detectAndSendChanges();
	}
	
	@Override
	public ContainerType<?> getType()
	{
		return VeContainerTypes.woodcutter;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setInventoryUpdateListener(Runnable p_217071_1_)
	{
		this.inventoryUpdateListener = p_217071_1_;
	}
	
	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.inventoryResult && super.canMergeSlot(stack, slotIn);
	}
	
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			Item item = itemstack1.getItem();
			itemstack = itemstack1.copy();
			if (index == 1)
			{
				item.onCreated(itemstack1, playerIn.world, playerIn);
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (this.world.getRecipeManager().getRecipe(VeRecipeTypes.WOODCUTTING, new Inventory(itemstack1), this.world).isPresent())
			{
				if (!this.mergeItemStack(itemstack1, 0, 1, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 2 && index < 29)
			{
				if (!this.mergeItemStack(itemstack1, 29, 38, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
			{
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			slot.onSlotChanged();
			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
			this.detectAndSendChanges();
		}
		return itemstack;
	}
	
	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(PlayerEntity playerIn)
	{
		super.onContainerClosed(playerIn);
		this.inventoryResult.removeStackFromSlot(1);
		this.WORLD_POS_CALLABLE.consume((p_217079_2_, p_217079_3_) ->
		{
			this.clearContainer(playerIn, playerIn.world, this.inventory);
		});
	}
}
