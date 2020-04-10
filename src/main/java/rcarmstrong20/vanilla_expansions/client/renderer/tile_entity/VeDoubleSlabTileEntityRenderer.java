package rcarmstrong20.vanilla_expansions.client.renderer.tile_entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.NonNullList;
import rcarmstrong20.vanilla_expansions.block.VeSlabBlock;
import rcarmstrong20.vanilla_expansions.tile_entity.VeDoubleSlabTileEntity;

public class VeDoubleSlabTileEntityRenderer extends TileEntityRenderer<VeDoubleSlabTileEntity>
{

	public VeDoubleSlabTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(VeDoubleSlabTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		NonNullList<ItemStack> nonnulllist = tileEntityIn.getInventory();
		ItemStack itemstack = nonnulllist.get(0);
		ItemStack itemstack1 = nonnulllist.get(1);
		
		//Render the top slab on top of the bottom slab from the inventory.
		if(itemstack != ItemStack.EMPTY && itemstack1 != ItemStack.EMPTY)
		{
			matrixStackIn.push();
			dispatcher.renderBlock(Block.getBlockFromItem(itemstack.getItem()).getDefaultState().with(VeSlabBlock.TYPE, SlabType.TOP), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, null);
			dispatcher.renderBlock(Block.getBlockFromItem(itemstack1.getItem()).getDefaultState(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, null);
			matrixStackIn.pop();
		}
	}
}
