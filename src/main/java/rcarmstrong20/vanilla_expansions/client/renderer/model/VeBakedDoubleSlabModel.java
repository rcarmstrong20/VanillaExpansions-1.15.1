package rcarmstrong20.vanilla_expansions.client.renderer.model;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import rcarmstrong20.vanilla_expansions.block.VeSlabBlock;

@OnlyIn(Dist.CLIENT)
public class VeBakedDoubleSlabModel implements IForgeBakedModel
{
	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData)
	{
		List<BakedQuad> quads = Lists.newArrayList();
		
		NonNullList<ItemStack> nonnulllist = extraData.getData(VeModelProperties.TILE_ENTITY_DOUBLE_SLAB).getInventory();
		
		ItemStack bottom_stack = nonnulllist.get(0);
		ItemStack top_stack = nonnulllist.get(1);
		
		BlockState bottom_slab = Block.getBlockFromItem(bottom_stack.getItem()).getDefaultState().with(VeSlabBlock.TYPE, SlabType.BOTTOM);
		BlockState top_slab = Block.getBlockFromItem(top_stack.getItem()).getDefaultState().with(VeSlabBlock.TYPE, SlabType.TOP);
		
		BakedQuad bottomQuad = (BakedQuad) Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(bottom_slab).getQuads(bottom_slab, side, rand, extraData);
		bottomQuad.func_187508_a();
		
		quads.add(bottomQuad);
		
		BakedQuad topQuad = (BakedQuad) Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(top_slab).getQuads(top_slab, side, rand, extraData);
		topQuad.func_187508_a();
		
		quads.add(topQuad);
		
		return quads;
	}
}
