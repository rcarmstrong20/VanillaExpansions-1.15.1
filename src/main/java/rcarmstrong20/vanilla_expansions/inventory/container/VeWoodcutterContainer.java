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
import rcarmstrong20.vanilla_expansions.item.crafting.VeWoodCuttingRecipe;

public class VeWoodcutterContainer extends Container
{
	//static final ImmutableList<Item> field_217084_c = ImmutableList.of(Items.STONE, Items.SANDSTONE, Items.RED_SANDSTONE, Items.QUARTZ_BLOCK, Items.COBBLESTONE, Items.STONE_BRICKS, Items.BRICKS, Items.NETHER_BRICKS, Items.RED_NETHER_BRICKS, Items.PURPUR_BLOCK, Items.PRISMARINE, Items.PRISMARINE_BRICKS, Items.DARK_PRISMARINE, Items.ANDESITE, Items.POLISHED_ANDESITE, Items.GRANITE, Items.POLISHED_GRANITE, Items.DIORITE, Items.POLISHED_DIORITE, Items.MOSSY_STONE_BRICKS, Items.MOSSY_COBBLESTONE, Items.SMOOTH_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, Items.SMOOTH_QUARTZ, Items.END_STONE, Items.END_STONE_BRICKS, Items.SMOOTH_STONE, Items.CUT_SANDSTONE, Items.CUT_RED_SANDSTONE);
	private final IWorldPosCallable world_callable;
	private final IntReferenceHolder single_interface_holder = IntReferenceHolder.single();
	private final World world;
	private List<VeWoodCuttingRecipe> recipes = Lists.newArrayList();
	private ItemStack empty_stack = ItemStack.EMPTY;
	private long field_217093_l;
	final Slot slot1;
	final Slot slot2;
	private Runnable inventoryUpdateListener = () -> {};
	
	public final IInventory inventorySlot1 = new Inventory(1)
	{
		public void markDirty()
		{
			super.markDirty();
			VeWoodcutterContainer.this.onCraftMatrixChanged(this);
			VeWoodcutterContainer.this.inventoryUpdateListener.run();
		}
	};
	
	private final CraftResultInventory inventory = new CraftResultInventory();
	
	public VeWoodcutterContainer(int p_i50059_1_, PlayerInventory playerInventory)
	{
		this(p_i50059_1_, playerInventory, IWorldPosCallable.DUMMY);
	}
	
	public VeWoodcutterContainer(int p_i50060_1_, PlayerInventory playerInventory, final IWorldPosCallable worldPosCallable)
	{
		super(VeContainerTypes.WOODCUTTER, p_i50060_1_);
		this.world_callable = worldPosCallable;
		this.world = playerInventory.player.world;
		this.slot1 = this.addSlot(new Slot(this.inventorySlot1, 0, 20, 33));
		this.slot2 = this.addSlot(new Slot(this.inventory, 1, 143, 33)
		{
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
					if (VeWoodcutterContainer.this.field_217093_l != l) {
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
		this.trackInt(this.single_interface_holder);
	}
	
	@OnlyIn(Dist.CLIENT)
	public int func_217073_e()
	{
		return this.single_interface_holder.get();
	}
	
	@OnlyIn(Dist.CLIENT)
	public List<VeWoodCuttingRecipe> getRecipeList()
	{
		return this.recipes;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getRecipeListSize()
	{
		return this.recipes.size();
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean func_217083_h()
	{
		return this.slot1.getHasStack() && !this.recipes.isEmpty();
	}
	
	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return isWithinUsableDistance(this.world_callable, playerIn, VeBlocks.woodcutter);
	}
	
	/**
	 * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
	 */
	public boolean enchantItem(PlayerEntity playerIn, int id)
	{
		if (id >= 0 && id < this.recipes.size())
		{
			this.single_interface_holder.set(id);
			this.func_217082_i();
		}
		return true;
	}
	
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack itemstack = this.slot1.getStack();
		if (itemstack.getItem() != this.empty_stack.getItem())
		{
			this.empty_stack = itemstack.copy();
			this.updateAvailableRecipes(inventoryIn, itemstack);
		}
	}
	
	private void updateAvailableRecipes(IInventory inventory, ItemStack itemStack)
	{
		this.recipes.clear();
		this.single_interface_holder.set(-1);
		this.slot2.putStack(ItemStack.EMPTY);
		if (!itemStack.isEmpty())
		{
			this.recipes = this.world.getRecipeManager().getRecipes(VeRecipeTypes.WOODCUTTING, inventory, this.world);
		}
	}
	
	private void func_217082_i()
	{
		if (!this.recipes.isEmpty())
		{
			VeWoodCuttingRecipe cuttingrecipe = this.recipes.get(this.single_interface_holder.get());
			this.slot2.putStack(cuttingrecipe.getCraftingResult(this.inventorySlot1));
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
		return VeContainerTypes.WOODCUTTER;
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
		return false;
	}
	
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			Item item = itemstack1.getItem();
			itemStack = itemstack1.copy();
			if (index == 1)
			{
				item.onCreated(itemstack1, playerIn.world, playerIn);
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemStack);
			}
			else if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (this.recipeHasStack(itemstack1))
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
			if (itemstack1.getCount() == itemStack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
			this.detectAndSendChanges();
		}
		return itemStack;
	}
	
	protected boolean recipeHasStack(ItemStack itemStack)
	{
		return this.world.getRecipeManager().getRecipe(VeRecipeTypes.WOODCUTTING, new Inventory(itemStack), this.world).isPresent();
	}
	
	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerEntity)
	{
		super.onContainerClosed(playerEntity);
		this.inventory.removeStackFromSlot(1);
		this.world_callable.consume((world, blockPos) ->
		{
			this.clearContainer(playerEntity, playerEntity.world, this.inventorySlot1);
		});
	}
}
