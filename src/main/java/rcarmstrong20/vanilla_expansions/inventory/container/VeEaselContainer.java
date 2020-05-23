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
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;
import rcarmstrong20.vanilla_expansions.core.VeContainerTypes;
import rcarmstrong20.vanilla_expansions.core.VeRecipeTypes;
import rcarmstrong20.vanilla_expansions.item.crafting.VeEaselRecipe;

public class VeEaselContainer extends Container
{
	/** The index of the selected recipe in the GUI. */
	private IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
	private List<VeEaselRecipe> recipes = Lists.newArrayList();
	/** The {@plainlink ItemStack} set in the input slot by the player. */
	//private ItemStack itemStackInput1 = ItemStack.EMPTY;
	//private ItemStack itemStackInput2 = ItemStack.EMPTY;
	//private ItemStack itemStackInput3 = ItemStack.EMPTY;
	private IWorldPosCallable worldPosCallable;
	private World world;
	/**
	 * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
	 * prevent the sound from being played multiple times on the same tick.
	 */
	private long lastOnTake;
	private Slot paperInventorySlot;
	private Slot dyeInventorySlot;
	private Slot dyeInventorySlot2;
	
	private ItemStack itemStackInput0 = ItemStack.EMPTY;
	private ItemStack itemStackInput1 = ItemStack.EMPTY;
	private ItemStack itemStackInput2 = ItemStack.EMPTY;
	
	/** The inventory slot that stores the output of the crafting recipe. */
	private Slot outputInventorySlot;
	private Runnable inventoryUpdateListener = () -> {};
	
	private final IInventory inputInventory = new Inventory(3)
	{
		public void markDirty()
		{
			super.markDirty();
			VeEaselContainer.this.onCraftMatrixChanged(this);
			VeEaselContainer.this.inventoryUpdateListener.run();
		}
	};
	
	private final CraftResultInventory outputInventory = new CraftResultInventory();
	
	public VeEaselContainer(int id, PlayerInventory playerInventory)
	{
		this(id, playerInventory, IWorldPosCallable.DUMMY);
	}
	
	public VeEaselContainer(int id, PlayerInventory playerInventory, final IWorldPosCallable worldPosCallable)
	{
		super(VeContainerTypes.easel, id);
		this.world = playerInventory.player.world;
		this.worldPosCallable = worldPosCallable;
		
		this.paperInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 13, 26)
		{
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return stack.getItem() == Items.PAPER;
			}
		});
		this.dyeInventorySlot = this.addSlot(new Slot(this.inputInventory, 1, 33, 26)
		{
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return stack.getItem() instanceof DyeItem;
			}
		});
		this.dyeInventorySlot2 = this.addSlot(new Slot(this.inputInventory, 2, 23, 45)
		{
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return stack.getItem() instanceof DyeItem;
			}
		});
		this.outputInventorySlot = this.addSlot(new Slot(this.outputInventory, 0, 143, 58)
		{
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack)
			{
				return false;
			}
			
			/*
			 * Called when the player takes the item from the output slot
			 */
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
			{
				ItemStack paperItemStack = paperInventorySlot.decrStackSize(1);
				ItemStack dyeItemStack = dyeInventorySlot.decrStackSize(1);
				ItemStack dyeItemStack2 = dyeInventorySlot2.decrStackSize(1);
				
				if (!paperItemStack.isEmpty() || !dyeItemStack.isEmpty() || !dyeItemStack2.isEmpty())
				{
					updateRecipeResultSlot();
				}
				
				stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
				worldPosCallable.consume((world, pos) ->
				{
					long l = world.getGameTime();
					if (VeEaselContainer.this.lastOnTake != l)
					{
						world.playSound((PlayerEntity)null, pos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						VeEaselContainer.this.lastOnTake = l;
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
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		ItemStack dyeSlotStack = this.getDyeInventorySlot().getStack();
		int paperSlotIndex = this.getPaperInventorySlot().getSlotIndex();
		int dyeSlotIndex = this.getDyeInventorySlot().getSlotIndex();
		int dyeSlot2Index = this.getDyeInventorySlot2().getSlotIndex();
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack1 = slot.getStack();
			itemstack = itemStack1.copy();
			
			if (index == this.outputInventorySlot.getSlotIndex())
			{
				if (!this.mergeItemStack(itemStack1, 4, 40, true))
				{
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(itemStack1, itemstack);
			}
			else if (index != paperSlotIndex && index != dyeSlotIndex && index != dyeSlot2Index)
			{
				if (!this.mergeItemStack(itemStack1, paperSlotIndex, paperSlotIndex + 1, false) && this.getPaperInventorySlot().isItemValid(itemStack1))
				{
					return ItemStack.EMPTY;
				}
				
				if(itemStack1.getItem() instanceof DyeItem)
				{
					//If the slot is empty move the dye the first dye slot or if the dye matches the dye in the slot merge the item stacks
					if(dyeSlotStack.getItem() == itemStack1.getItem() || dyeSlotStack.isEmpty())
					{
						if (!this.mergeItemStack(itemStack1, dyeSlotIndex, dyeSlotIndex + 1, false))
						{
							return ItemStack.EMPTY;
						}
					}
					else
					{
						if (!this.mergeItemStack(itemStack1, dyeSlot2Index, dyeSlot2Index + 1, false))
						{
							return ItemStack.EMPTY;
						}
					}
				}
				else if (index >= 4 && index < 31)
				{
					if (!this.mergeItemStack(itemStack1, 31, 40, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 31 && index < 40 && !this.mergeItemStack(itemStack1, 4, 31, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemStack1, 4, 40, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (itemStack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (itemStack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			
			slot.onTake(playerIn, itemstack);
			this.detectAndSendChanges();
		}
		
		return itemstack;
	}
	
	public boolean canInteractWith(PlayerEntity player)
	{
		return isWithinUsableDistance(this.worldPosCallable, player, VeBlocks.easel);
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
	
	/*
	 * Fill in the output slot for the currently selected recipe.
	 */
	private void updateRecipeResultSlot()
	{
		if (!this.getRecipeList().isEmpty())
		{
			VeEaselRecipe easelRecipe = this.getRecipeList().get(this.selectedRecipe.get());
			this.getOutputInventorySlot().putStack(easelRecipe.getCraftingResult(this.inputInventory));
		}
		else
		{
			this.getOutputInventorySlot().putStack(ItemStack.EMPTY);
		}
		this.detectAndSendChanges();
	}
	
	/*
	 * Called whenever the inventory changes
	 */
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack paperItemStack = this.getPaperInventorySlot().getStack();
		ItemStack dyeItemStack = this.getDyeInventorySlot().getStack();
		ItemStack dyeItemStack2 = this.getDyeInventorySlot2().getStack();
		
		if(paperItemStack.getItem() != this.itemStackInput0.getItem() || dyeItemStack.getItem() != this.itemStackInput1.getItem() || dyeItemStack2.getItem() != this.itemStackInput2.getItem())
		{
			this.itemStackInput0 = paperItemStack.copy();
			this.itemStackInput1 = dyeItemStack.copy();
			this.itemStackInput2 = dyeItemStack2.copy();
			
			this.updateAvailableRecipes(inventoryIn, itemStackInput0, itemStackInput1, itemStackInput2);
		}
	}
	
	private void updateAvailableRecipes(IInventory inventory, ItemStack stack, ItemStack stack1, ItemStack stack2)
	{
		this.getRecipeList().clear();
		this.selectedRecipe.set(-1);
		this.outputInventorySlot.putStack(ItemStack.EMPTY);
		
		if (!stack.isEmpty() && !stack1.isEmpty() && !stack2.isEmpty())
		{
			this.setRecipeList(this.world.getRecipeManager().getRecipes(VeRecipeTypes.easel, inventory, this.world));
		}
	}
	
	@Override
	public ContainerType<?> getType()
	{
		return VeContainerTypes.easel;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setInventoryUpdateListener(Runnable listener)
	{
		this.inventoryUpdateListener = listener;
	}
	
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.outputInventory && super.canMergeSlot(stack, slotIn);
	}
	
	@Override
	public void onContainerClosed(PlayerEntity playerIn)
	{
		super.onContainerClosed(playerIn);
		this.outputInventory.removeStackFromSlot(1);
		this.worldPosCallable.consume((world, pos) ->
		{
			this.clearContainer(playerIn, playerIn.world, this.inputInventory);
		});
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getPaperInventorySlot()
	{
		return this.paperInventorySlot;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getDyeInventorySlot()
	{
		return this.dyeInventorySlot;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getDyeInventorySlot2()
	{
		return this.dyeInventorySlot2;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getOutputInventorySlot()
	{
		return outputInventorySlot;
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
	public List<VeEaselRecipe> getRecipeList()
	{
		return this.recipes;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setRecipeList(List<VeEaselRecipe> recipes)
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
		return this.getPaperInventorySlot().getHasStack() && this.getDyeInventorySlot().getHasStack() && this.getDyeInventorySlot2().getHasStack() && !this.recipes.isEmpty();
	}
}
