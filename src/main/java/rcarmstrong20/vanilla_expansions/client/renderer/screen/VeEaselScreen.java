package rcarmstrong20.vanilla_expansions.client.renderer.screen;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;
import rcarmstrong20.vanilla_expansions.inventory.container.VeEaselContainer;
import rcarmstrong20.vanilla_expansions.item.crafting.VeEaselRecipe;

/**
 *
 * @author Ryan
 *
 */
@OnlyIn(Dist.CLIENT)
public class VeEaselScreen extends ContainerScreen<VeEaselContainer>
{
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(VanillaExpansions.MOD_ID,
            "textures/gui/container/easel.png");
    private float sliderProgress;
    /** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
    private boolean clickedOnScroll;
    /**
     * The index of the first recipe to display. The number of recipes displayed at
     * any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down
     * one row, this value would be 4 (representing the index of the first slot on
     * the second row).
     */
    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;

    public VeEaselScreen(VeEaselContainer containerIn, PlayerInventory playerInv, ITextComponent titleIn)
    {
        super(containerIn, playerInv, titleIn);
        containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, this.ySize - 96 + 2,
                4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startXPos = this.guiLeft;
        int startYPos = this.guiTop;
        this.blit(startXPos, startYPos, 0, 0, this.xSize, this.ySize); // Background
        int sliderProgressPos = (int) (41.0F * this.sliderProgress);
        this.blit(startXPos + 119, startYPos + 13 + sliderProgressPos, 232 + (this.canScroll() ? 0 : 12), 0, 12, 15); // Scroll
                                                                                                                      // wheel
        int l = this.guiLeft + 52;
        int i1 = this.guiTop + 14;
        int j1 = this.recipeIndexOffset + 12;
        this.drawRecipesBackground(mouseX, mouseY, l, i1, j1);
        this.drawRecipesItems(l, i1, j1);
    }

    private void drawRecipesBackground(int mouseX, int mouseY, int left, int top, int recipeIndexOffsetMax)
    {
        int guiXPos = this.guiLeft;
        int guiYPos = this.guiTop;

        Slot paperSlot = this.container.getPaperInventorySlot();
        Slot dyeSlot1 = this.container.getDyeInventorySlot();
        Slot dyeSlot2 = this.container.getDyeInventorySlot2();

        // Draw the paper icon when the slot is empty
        if (!paperSlot.getHasStack())
        {
            this.blit(guiXPos + paperSlot.xPos, guiYPos + paperSlot.yPos, this.xSize, 0, 16, 16);
        }

        // Draw the first dye slot icon when the slot is empty
        if (!dyeSlot1.getHasStack())
        {
            this.blit(guiXPos + dyeSlot1.xPos, guiYPos + dyeSlot1.yPos, this.xSize + 16, 0, 16, 16);
        }

        // Draw the second dye slot icon when the slot is empty
        if (!dyeSlot2.getHasStack())
        {
            this.blit(guiXPos + dyeSlot2.xPos, guiYPos + dyeSlot2.yPos, this.xSize + 16, 0, 16, 16);
        }

        int i2 = this.guiLeft + 60;
        int k2 = this.guiTop + 13;

        // Draw the recipe buttons
        for (int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i)
        {
            int j = i - this.recipeIndexOffset;
            int j1 = i2 + j % 4 * 14;
            int k1 = k2 + j / 4 * 14;
            int l1 = this.ySize;
            if (i == this.container.getSelectedRecipe())
            {
                l1 += 14;
            }
            else if (mouseX >= j1 && mouseY >= k1 && mouseX < j1 + 14 && mouseY < k1 + 14)
            {
                l1 += 28;
            }
            this.blit(j1, k1, 0, l1, 14, 14);
        }
    }

    private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax)
    {
        List<VeEaselRecipe> list = this.container.getRecipeList();

        int i2 = this.guiLeft + 60;
        int k2 = this.guiTop + 13;

        for (int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i)
        {
            int j = i - this.recipeIndexOffset;
            int j1 = i2 + j % 4 * 14;
            int k1 = k2 + j / 4 * 14;

            this.getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), j1 - 1,
                    k1 - 1);
        }
    }

    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_)
    {
        this.clickedOnScroll = false;
        if (this.hasItemsInInputSlot)
        {
            int i = this.guiLeft + 60;
            int j = this.guiTop + 13;
            int k = this.recipeIndexOffset + 16;

            for (int l = this.recipeIndexOffset; l < k; ++l)
            {
                int i1 = l - this.recipeIndexOffset;
                double d0 = p_mouseClicked_1_ - (i + i1 % 4 * 14);
                double d1 = p_mouseClicked_3_ - (j + i1 / 4 * 14);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 14.0D && d1 < 14.0D
                        && this.container.enchantItem(this.minecraft.player, l))
                {
                    Minecraft.getInstance().getSoundHandler()
                            .play(SimpleSound.master(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
                    this.minecraft.playerController.sendEnchantPacket((this.container).windowId, l);
                    return true;
                }
            }

            i = this.guiLeft + 119;
            j = this.guiTop + 9;
            if (p_mouseClicked_1_ >= i && p_mouseClicked_1_ < i + 12 && p_mouseClicked_3_ >= j
                    && p_mouseClicked_3_ < j + 54)
            {
                this.clickedOnScroll = true;
            }
        }
        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_,
            double p_mouseDragged_6_, double p_mouseDragged_8_)
    {
        if (this.clickedOnScroll && this.canScroll())
        {
            int i = this.guiTop + 14;
            int j = i + 54;
            this.sliderProgress = ((float) p_mouseDragged_3_ - i - 7.5F) / (j - i - 15.0F);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) (this.sliderProgress * this.getHiddenRows() + 0.5D) * 4;
            return true;
        }
        else
        {
            return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_,
                    p_mouseDragged_8_);
        }
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_)
    {
        if (this.canScroll())
        {
            int i = this.getHiddenRows();
            this.sliderProgress = (float) (this.sliderProgress - p_mouseScrolled_5_ / i);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int) (this.sliderProgress * i + 0.5D) * 4;
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
        this.hasItemsInInputSlot = this.getContainer().hasItemsinInputSlot();
        if (!this.hasItemsInInputSlot)
        {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
    }
}
