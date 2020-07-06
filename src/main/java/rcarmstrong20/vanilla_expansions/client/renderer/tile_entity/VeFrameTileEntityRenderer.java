package rcarmstrong20.vanilla_expansions.client.renderer.tile_entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import rcarmstrong20.vanilla_expansions.block.VeFrameBlock;
import rcarmstrong20.vanilla_expansions.tile_entity.VeFrameTileEntity;

public class VeFrameTileEntityRenderer extends TileEntityRenderer<VeFrameTileEntity>
{

	public VeFrameTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	@Override
	public void render(VeFrameTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		Direction direction = tileEntityIn.getBlockState().get(VeFrameBlock.FACING);
		NonNullList<ItemStack> nonnulllist = tileEntityIn.getInventory();
		ItemStack itemStack = nonnulllist.get(0);
		
		if(itemStack != ItemStack.EMPTY)
		{
			if(direction == Direction.NORTH)
			{
				renderPainting(0.5D, 0.5D, 0.9D, 0F, itemStack, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			}
			else if(direction == Direction.SOUTH)
			{
				renderPainting(0.5D, 0.5D, 0.1D, 0F, itemStack, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			}
			else if(direction == Direction.WEST)
			{
				renderPainting(0.9D, 0.5D, 0.5D, 270F, itemStack, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			}
			else
			{
				renderPainting(0.1D, 0.5D, 0.5D, 270F, itemStack, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			}
		}
	}
	
	/*
	 * A method to render the paintings
	 */
	private static void renderPainting(double x, double y, double z, float rotation, ItemStack itemStack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		matrixStackIn.push();
		matrixStackIn.translate(x, y, z);
		//matrixStackIn.scale(0.874F, 0.874F, 0.874F);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));

		Minecraft.getInstance().getItemRenderer().renderItem(itemStack, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
		
		matrixStackIn.pop();
	}
}
