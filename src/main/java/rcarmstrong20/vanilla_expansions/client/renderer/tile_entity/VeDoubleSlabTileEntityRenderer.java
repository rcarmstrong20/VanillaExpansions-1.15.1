package rcarmstrong20.vanilla_expansions.client.renderer.tile_entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
		ItemStack itemStack = nonnulllist.get(0);
		ItemStack itemStack1 = nonnulllist.get(1);
		
		//Render the top slab on top of the bottom slab from the inventory.
		if(itemStack != ItemStack.EMPTY && itemStack1 != ItemStack.EMPTY)
		{
			matrixStackIn.push();
			
			//PerspectiveMapWrapper.handlePerspective((IBakedModel) new VeBakedDoubleSlabModel(), PerspectiveMapWrapper.getTransforms(transforms), TransformType.FIXED, matrixStackIn);
			
			//BlockState state = Block.getBlockFromItem(itemStack.getItem()).getDefaultState().with(VeSlabBlock.TYPE, SlabType.TOP);
			
			//dispatcher.getBlockModelRenderer().renderModel(matrixStackIn, null, VeBlocks.double_slab.getDefaultState(), new VeBakedDoubleSlabModel().getBakedModel(), 0F, 0F, 0F, combinedLightIn, combinedOverlayIn, new VeBakedDoubleSlabModel().getModelData(tileEntityIn.getWorld(), tileEntityIn.getPos(), VeBlocks.double_slab.getDefaultState(), tileEntityIn.getModelData()));
			
			dispatcher.renderBlock(Block.getBlockFromItem(itemStack.getItem()).getDefaultState(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn.getModelData());
			dispatcher.renderBlock(Block.getBlockFromItem(itemStack1.getItem()).getDefaultState(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, tileEntityIn.getModelData());
			
			matrixStackIn.pop();
		}
	}
}
