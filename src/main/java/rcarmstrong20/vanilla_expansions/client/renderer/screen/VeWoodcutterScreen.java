package rcarmstrong20.vanilla_expansions.client.renderer.screen;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.inventory.container.VeWoodcutterContainer;
import rcarmstrong20.vanilla_expansions.item.crafting.VeWoodcuttingRecipe;

@OnlyIn(Dist.CLIENT)
public class VeWoodcutterScreen extends ContainerScreen<VeWoodcutterContainer>
{
	/*
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
	private float sliderProgress;
	private boolean field_214148_m;
	private int recipeIndexOffset;
	
	public VeWoodcutterScreen(VeWoodcutterContainer containerIn, PlayerInventory playerInv, ITextComponent p_i51076_3_)
	{
		super(containerIn, playerInv, p_i51076_3_);
		containerIn.setInventoryUpdateListener(this::updateListener);
	}
	
	public void render(int p_render_1_, int p_render_2_, float p_render_3_)
	{
		super.render(p_render_1_, p_render_2_, p_render_3_);
		this.renderHoveredToolTip(p_render_1_, p_render_2_);
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.font.drawString(this.getTitle().getFormattedText(), 8.0F, 4.0F, 4210752);
		this.font.drawString(this.getPlayerInventory().getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.renderBackground();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);
		int k = (int)(41.0F * this.getSliderProgress());
		this.blit(i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
		int l = this.guiLeft + 52;
		int i1 = this.guiTop + 14;
		int j1 = this.getRecipeIndexOffset() + 12;
		this.func_214141_a(mouseX, mouseY, l, i1, j1);
		this.renderRecipeIcons(l, i1, j1);
	}
	
	private void func_214141_a(int p_214141_1_, int p_214141_2_, int p_214141_3_, int p_214141_4_, int p_214141_5_)
	{
		for(int i = this.getRecipeIndexOffset(); i < p_214141_5_ && i < this.container.getRecipes().size(); ++i)
		{
			int j = i - this.getRecipeIndexOffset();
			int k = p_214141_3_ + j % 4 * 16;
			int l = j / 4;
			int i1 = p_214141_4_ + l * 18 + 2;
			int j1 = this.ySize;
			if (i == this.container.getSingleInterfaceHolder().get())
			{
				j1 += 18;
			}
			else if (p_214141_1_ >= k && p_214141_2_ >= i1 && p_214141_1_ < k + 16 && p_214141_2_ < i1 + 18)
			{
				j1 += 36;
			}
			
			this.blit(k, i1 - 1, 0, j1, 16, 18);
		}
	}
	
	private void renderRecipeIcons(int p_214142_1_, int p_214142_2_, int p_214142_3_)
	{
		List<VeWoodcuttingRecipe> list = this.container.getRecipes();
		
		for(int i = this.getRecipeIndexOffset(); i < p_214142_3_ && i < this.container.getRecipes().size(); ++i)
		{
			int j = i - this.getRecipeIndexOffset();
			int k = p_214142_1_ + j % 4 * 16;
			int l = j / 4;
			int i1 = p_214142_2_ + l * 18 + 2;
			this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), k, i1);
		}
	}
	
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_)
	{
		this.field_214148_m = false;
		if (this.getContainer().isInputSlotEmpty())
		{
			int i = this.guiLeft + 52;
			int j = this.guiTop + 14;
			int k = this.getRecipeIndexOffset() + 12;
			
			for(int l = this.getRecipeIndexOffset(); l < k; ++l)
			{
				int i1 = l - this.getRecipeIndexOffset();
				double d0 = p_mouseClicked_1_ - (double)(i + i1 % 4 * 16);
				double d1 = p_mouseClicked_3_ - (double)(j + i1 / 4 * 18);
				if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.container.enchantItem(this.minecraft.player, l))
				{
					Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
					this.minecraft.playerController.sendEnchantPacket((this.container).windowId, l);
					return true;
				}
			}
			
			i = this.guiLeft + 119;
			j = this.guiTop + 9;
			if (p_mouseClicked_1_ >= (double)i && p_mouseClicked_1_ < (double)(i + 12) && p_mouseClicked_3_ >= (double)j && p_mouseClicked_3_ < (double)(j + 54))
			{
				this.field_214148_m = true;
			}
		}
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
	
	public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_)
	{
		if (this.field_214148_m && this.canScroll())
		{
			int i = this.guiTop + 14;
			int j = i + 54;
			this.setSliderProgress(((float)p_mouseDragged_3_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F));
			this.setSliderProgress(MathHelper.clamp(this.getSliderProgress(), 0.0F, 1.0F));
			this.setRecipeIndexOffset((int)((double)(this.getSliderProgress() * (float)this.getHiddenRows()) + 0.5D) * 4);
			return true;
		}
		else
		{
			return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
		}
	}
	
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_)
	{
		if (this.canScroll())
		{
			int i = this.getHiddenRows();
			this.setSliderProgress((float)((double)this.getSliderProgress() - p_mouseScrolled_5_ / (double)i));
			this.setSliderProgress(MathHelper.clamp(this.getSliderProgress(), 0.0F, 1.0F));
			this.setRecipeIndexOffset((int)((double)(this.getSliderProgress() * (float)i) + 0.5D) * 4);
		}
		return true;
	}
	
	private boolean canScroll()
	{
		return this.getContainer().isInputSlotEmpty() && this.container.getRecipes().size() > 12;
	}
	
	protected int getHiddenRows()
	{
		return (this.getContainer().getRecipes().size() + 4 - 1) / 4 - 3;
	}
	
	private void updateListener()
	{
		if (!this.getContainer().isInputSlotEmpty())
		{
			this.setSliderProgress(0.0F);
			this.setRecipeIndexOffset(0);
		}
	}
	
	private PlayerInventory getPlayerInventory()
	{
		return this.playerInventory;
	}
	
	public float getSliderProgress()
	{
		return this.sliderProgress;
	}
	
	public void setSliderProgress(float sliderProgress)
	{
		this.sliderProgress = sliderProgress;
	}
	
	public int getRecipeIndexOffset()
	{
		return this.recipeIndexOffset;
	}
	
	public void setRecipeIndexOffset(int recipeIndexOffset)
	{
		this.recipeIndexOffset = recipeIndexOffset;
	}
	*/
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
	private float sliderProgress;
	/** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
	private boolean clickedOnScroll;
	/**
	 * The index of the first recipe to display.
	 * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
	 * row, this value would be 4 (representing the index of the first slot on the second row).
	 */
	private int recipeIndexOffset;
	private boolean hasItemsInInputSlot;
	
	public VeWoodcutterScreen(VeWoodcutterContainer containerIn, PlayerInventory playerInv, ITextComponent titleIn)
	{
		super(containerIn, playerInv, titleIn);
		containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
	}
	
	public void render(int p_render_1_, int p_render_2_, float p_render_3_)
	{
		super.render(p_render_1_, p_render_2_, p_render_3_);
		this.renderHoveredToolTip(p_render_1_, p_render_2_);
	}
	
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
	}
	
	/**
	 * Draws the background layer of this container (behind the items).
	 */
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.renderBackground();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);
		int k = (int)(41.0F * this.sliderProgress);
		this.blit(i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
		int l = this.guiLeft + 52;
		int i1 = this.guiTop + 14;
		int j1 = this.recipeIndexOffset + 12;
		this.drawRecipesBackground(mouseX, mouseY, l, i1, j1);
		this.drawRecipesItems(l, i1, j1);
	}
	
	private void drawRecipesBackground(int mouseX, int mouseY, int left, int top, int recipeIndexOffsetMax)
	{
		for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i)
		{
			int j = i - this.recipeIndexOffset;
			int k = left + j % 4 * 16;
			int l = j / 4;
			int i1 = top + l * 18 + 2;
			int j1 = this.ySize;
			if (i == this.container.getSelectedRecipe())
			{
				j1 += 18;
			}
			else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18)
			{
				j1 += 36;
			}
			this.blit(k, i1 - 1, 0, j1, 16, 18);
		}
	}
	
	private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax)
	{
		List<VeWoodcuttingRecipe> list = this.container.getRecipeList();
		
		for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i)
		{
			int j = i - this.recipeIndexOffset;
			int k = left + j % 4 * 16;
			int l = j / 4;
			int i1 = top + l * 18 + 2;
			this.getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), k, i1);
		}
	}
	
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_)
	{
		this.clickedOnScroll = false;
		if (this.hasItemsInInputSlot)
		{
			int i = this.guiLeft + 52;
			int j = this.guiTop + 14;
			int k = this.recipeIndexOffset + 12;
			
			for(int l = this.recipeIndexOffset; l < k; ++l)
			{
				int i1 = l - this.recipeIndexOffset;
				double d0 = p_mouseClicked_1_ - (double)(i + i1 % 4 * 16);
				double d1 = p_mouseClicked_3_ - (double)(j + i1 / 4 * 18);
				if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.container.enchantItem(this.minecraft.player, l))
				{
					Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
					this.minecraft.playerController.sendEnchantPacket((this.container).windowId, l);
					return true;
				}
			}
			
			i = this.guiLeft + 119;
			j = this.guiTop + 9;
			if (p_mouseClicked_1_ >= (double)i && p_mouseClicked_1_ < (double)(i + 12) && p_mouseClicked_3_ >= (double)j && p_mouseClicked_3_ < (double)(j + 54))
			{
				this.clickedOnScroll = true;
			}
		}
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
	
	public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_)
	{
		if (this.clickedOnScroll && this.canScroll())
		{
			int i = this.guiTop + 14;
			int j = i + 54;
			this.sliderProgress = ((float)p_mouseDragged_3_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
			this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
			this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
			return true;
		}
		else
		{
			return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
		}
	}
	
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_)
	{
		if (this.canScroll())
		{
			int i = this.getHiddenRows();
			this.sliderProgress = (float)((double)this.sliderProgress - p_mouseScrolled_5_ / (double)i);
			this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
			this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)i) + 0.5D) * 4;
		}
		return true;
	}
	
	private boolean canScroll()
	{
		return this.hasItemsInInputSlot && this.container.getRecipeListSize() > 12;
	}
	
	protected int getHiddenRows()
	{
		return (this.container.getRecipeListSize() + 4 - 1) / 4 - 3;
	}
	
	/**
	 * Called every time this screen's container is changed (is marked as dirty).
	 */
	private void onInventoryUpdate()
	{
		this.hasItemsInInputSlot = this.container.hasItemsinInputSlot();
		if (!this.hasItemsInInputSlot)
		{
			this.sliderProgress = 0.0F;
			this.recipeIndexOffset = 0;
		}
	}
}
