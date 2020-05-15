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
	
	/*
	//Needed
	private final IWorldPosCallable worldPosCallable;
	private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
	private List<VeEaselRecipe> recipes = Lists.newArrayList();
	private final Slot paperSlot;
	private final Slot dyeSlot;
	private final Slot dyeSlot2;
	private final Slot outputSlot;
	
	private final IInventory inputInventory = new Inventory(3)
	{
		public void markDirty()
		{
			super.markDirty();
			VeEaselContainer.this.onCraftMatrixChanged(this);
			VeEaselContainer.this.runnable.run();
		}
	};
	
	private final IInventory outputInventory = new Inventory(1)
	{
		public void markDirty()
		{
			super.markDirty();
			VeEaselContainer.this.runnable.run();
		}
	};
	
	//Checking
	
	private final World world;
	private Runnable runnable = () -> {};
	private long field_226622_j_;
	*/
	
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
			
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
			{
				paperInventorySlot.decrStackSize(1);
				dyeInventorySlot.decrStackSize(1);
				if (!paperInventorySlot.getHasStack() || !dyeInventorySlot.getHasStack() || !this.getHasStack())
				{
					selectedRecipe.set(0);
				}
				
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
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack1 = slot.getStack();
			itemstack = itemStack1.copy();
			
			if (index == this.getOutputInventorySlot().getSlotIndex())
			{
				if (!this.mergeItemStack(itemStack1, 4, 40, true))
				{
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(itemStack1, itemstack);
			}
			else if (index != this.getPaperInventorySlot().getSlotIndex() && index != this.getDyeInventorySlot().getSlotIndex() && index != this.getDyeInventorySlot2().getSlotIndex())
			{
				if (!this.mergeItemStack(itemStack1, this.getPaperInventorySlot().getSlotIndex(), this.getPaperInventorySlot().getSlotIndex() + 1, false) && this.getPaperInventorySlot().isItemValid(itemStack1))
				{
					return ItemStack.EMPTY;
				}
				
				else if (itemStack1.getItem() instanceof DyeItem)
				{
					if(ItemStack.areItemStacksEqual(this.getDyeInventorySlot().getStack(), itemStack1) || this.getDyeInventorySlot().getStack().isEmpty())
					{
						if (!this.mergeItemStack(itemStack1, this.getDyeInventorySlot().getSlotIndex(), this.getDyeInventorySlot().getSlotIndex() + 1, false))
						{
							return ItemStack.EMPTY;
						}
					}
					else if(ItemStack.areItemStacksEqual(this.getDyeInventorySlot2().getStack(), slot.getStack()) || this.getDyeInventorySlot2().getStack().isEmpty())
					{
						if (!this.mergeItemStack(itemStack1, this.getDyeInventorySlot2().getSlotIndex(), this.getDyeInventorySlot2().getSlotIndex() + 1, false))
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
			
			slot.onTake(playerIn, itemStack1);
		}
		
		return itemstack;
	}
	
	/*
	private void func_217031_j()
	{
		if (this.intReferenceHolder.get() > 0)
		{
			ItemStack itemstack = this.paperSlot.getStack();
			ItemStack itemstack1 = this.dyeSlot.getStack();
			ItemStack itemstack2 = ItemStack.EMPTY;
			if (!itemstack.isEmpty() && !itemstack1.isEmpty())
			{
				itemstack2 = itemstack.copy();
				itemstack2.setCount(1);
				BannerPattern bannerpattern = BannerPattern.values()[this.intReferenceHolder.get()];
				DyeColor dyecolor = ((DyeItem)itemstack1.getItem()).getDyeColor();
				CompoundNBT compoundnbt = itemstack2.getOrCreateChildTag("BlockEntityTag");
				ListNBT listnbt;
				if (compoundnbt.contains("Patterns", 9))
				{
					listnbt = compoundnbt.getList("Patterns", 10);
				}
				else
				{
					listnbt = new ListNBT();
					compoundnbt.put("Patterns", listnbt);
				}
				
				CompoundNBT compoundnbt1 = new CompoundNBT();
				compoundnbt1.putString("Pattern", bannerpattern.getHashname());
				compoundnbt1.putInt("Color", dyecolor.getId());
				listnbt.add(compoundnbt1);
			}
			
			if (!ItemStack.areItemStacksEqual(itemstack2, this.outputSlot.getStack()))
			{
				this.outputSlot.putStack(itemstack2);
			}
		}
	}
	*/
	
	/*
	public void onCraftMatrixChanged(IInventory inputInventory)
	{
		ItemStack itemstack = this.paperSlot.getStack();
		ItemStack itemstack1 = this.dyeSlot.getStack();
		ItemStack itemstack2 = this.dyeSlot2.getStack();
		ItemStack itemstack3 = this.outputSlot.getStack();
		if (itemstack3.isEmpty() || !itemstack.isEmpty() && !itemstack1.isEmpty() && this.intReferenceHolder.get() > 0 && (this.intReferenceHolder.get() < BannerPattern.field_222480_O - 5 || !itemstack2.isEmpty()))
		{
			if (!itemstack2.isEmpty() && itemstack2.getItem() instanceof BannerPatternItem)
			{
				CompoundNBT compoundnbt = itemstack.getOrCreateChildTag("BlockEntityTag");
				boolean flag = compoundnbt.contains("Patterns", 9) && !itemstack.isEmpty() && compoundnbt.getList("Patterns", 10).size() >= 6;
				if (flag)
				{
					this.intReferenceHolder.set(0);
				}
				else
				{
					this.intReferenceHolder.set(((BannerPatternItem)itemstack2.getItem()).func_219980_b().ordinal());
				}
			}
		}
		else
		{
			this.outputSlot.putStack(ItemStack.EMPTY);
			this.intReferenceHolder.set(0);
		}
		
		this.func_217031_j();
		this.detectAndSendChanges();
	}
	*/
	
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
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack paperItemStack = this.getPaperInventorySlot().getStack();
		ItemStack dyeItemStack = this.getDyeInventorySlot().getStack();
		ItemStack dyeItemStack2 = this.getDyeInventorySlot2().getStack();
		
		this.updateAvailableRecipes(inventoryIn, paperItemStack, dyeItemStack, dyeItemStack2);
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
	
	public ContainerType<?> getType()
	{
		return VeContainerTypes.easel;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setInventoryUpdateListener(Runnable listener)
	{
		this.inventoryUpdateListener = listener;
	}
	
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.outputInventory && super.canMergeSlot(stack, slotIn);
	}
	
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
