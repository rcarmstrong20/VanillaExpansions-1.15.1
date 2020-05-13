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
	private final IWorldPosCallable worldPosCallable;
	/** The index of the selected recipe in the GUI. */
	private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
	private final World world;
	private List<VeWoodcuttingRecipe> recipes = Lists.newArrayList();
	/** The {@plainlink ItemStack} set in the input slot by the player. */
	private ItemStack itemStackInput = ItemStack.EMPTY;
	/**
	 * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
	 * prevent the sound from being played multiple times on the same tick.
	 */
	private long lastOnTake;
	final Slot inputInventorySlot;
	/** The inventory slot that stores the output of the crafting recipe. */
	final Slot outputInventorySlot;
	private Runnable inventoryUpdateListener = () -> {
	};
	public final IInventory inputInventory = new Inventory(1)
	{
		public void markDirty()
		{
			super.markDirty();
			VeWoodcutterContainer.this.onCraftMatrixChanged(this);
			VeWoodcutterContainer.this.inventoryUpdateListener.run();
		}
	};
	/** The inventory that stores the output of the crafting recipe. */
	private final CraftResultInventory outputInventory = new CraftResultInventory();
	
	public VeWoodcutterContainer(int windowId, PlayerInventory playerInventory)
	{
		this(windowId, playerInventory, IWorldPosCallable.DUMMY);
	}
	
	public VeWoodcutterContainer(int windowId, PlayerInventory playerInventory, final IWorldPosCallable worldPosCallable)
	{
		super(VeContainerTypes.woodcutter, windowId);
		this.worldPosCallable = worldPosCallable;
		this.world = playerInventory.player.world;
		this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
		this.outputInventorySlot = this.addSlot(new Slot(this.outputInventory, 1, 143, 33)
		{
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return false;
			}
			
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
			{
				ItemStack itemstack = VeWoodcutterContainer.this.inputInventorySlot.decrStackSize(1);
				if (!itemstack.isEmpty())
				{
					VeWoodcutterContainer.this.updateRecipeResultSlot();
				}
				
				stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
				worldPosCallable.consume((world, blockPos) ->
				{
					long l = world.getGameTime();
					if (VeWoodcutterContainer.this.lastOnTake != l)
					{
						world.playSound((PlayerEntity)null, blockPos, VeSoundEvents.UI_WOODCUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						VeWoodcutterContainer.this.lastOnTake = l;
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
		
		this.trackInt(this.selectedRecipe);
	}
	
	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return isWithinUsableDistance(this.worldPosCallable, playerIn, VeBlocks.woodcutter);
	}
	
	/**
	 * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
	 */
	public boolean enchantItem(PlayerEntity playerIn, int id)
	{
		if (id >= 0 && id < this.getRecipeList().size())
		{
			this.selectedRecipe.set(id);
			this.updateRecipeResultSlot();
		}
		return true;
	}
	
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack itemstack = this.inputInventorySlot.getStack();
		if (itemstack.getItem() != this.itemStackInput.getItem())
		{
			this.itemStackInput = itemstack.copy();
			this.updateAvailableRecipes(inventoryIn, itemstack);
		}
	}
	
	private void updateAvailableRecipes(IInventory inventoryIn, ItemStack stack)
	{
		this.getRecipeList().clear();
		this.selectedRecipe.set(-1);
		this.outputInventorySlot.putStack(ItemStack.EMPTY);
		if (!stack.isEmpty())
		{
			this.setRecipeList(this.world.getRecipeManager().getRecipes(VeRecipeTypes.woodcutting, inventoryIn, this.world));
		}
	}
	
	private void updateRecipeResultSlot()
	{
		if (!this.getRecipeList().isEmpty())
		{
			VeWoodcuttingRecipe stonecuttingrecipe = this.getRecipeList().get(this.selectedRecipe.get());
			this.outputInventorySlot.putStack(stonecuttingrecipe.getCraftingResult(this.inputInventory));
		}
		else
		{
			this.outputInventorySlot.putStack(ItemStack.EMPTY);
		}
		this.detectAndSendChanges();
	}
	
	public ContainerType<?> getType()
	{
		return VeContainerTypes.woodcutter;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setInventoryUpdateListener(Runnable listenerIn)
	{
		this.inventoryUpdateListener = listenerIn;
	}
	
	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	 * null for the initial slot that was double-clicked.
	 */
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.outputInventory && super.canMergeSlot(stack, slotIn);
	}
	
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
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
			else if (this.world.getRecipeManager().getRecipe(VeRecipeTypes.woodcutting, new Inventory(itemstack1), this.world).isPresent())
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
	public void onContainerClosed(PlayerEntity playerIn)
	{
		super.onContainerClosed(playerIn);
		this.outputInventory.removeStackFromSlot(1);
		this.worldPosCallable.consume((p_217079_2_, p_217079_3_) ->
		{
			this.clearContainer(playerIn, playerIn.world, this.inputInventory);
		});
	}
	
	/**
	 * Returns the index of the selected recipe.
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSelectedRecipe()
	{
		return this.selectedRecipe.get();
	}
	
	@OnlyIn(Dist.CLIENT)
	public List<VeWoodcuttingRecipe> getRecipeList()
	{
		return this.recipes;
	}
	
	public void setRecipeList(List<VeWoodcuttingRecipe> recipes)
	{
		this.recipes = recipes;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getRecipeListSize()
	{
		return this.recipes.size();
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean hasItemsinInputSlot()
	{
		return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
	}
}
