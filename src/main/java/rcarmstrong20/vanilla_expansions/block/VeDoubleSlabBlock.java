package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.registries.ForgeRegistries;

public class VeDoubleSlabBlock extends Block
{
	public VeDoubleSlabBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		String double_slab_name = state.getBlock().getRegistryName().toString().substring(state.getBlock().getRegistryName().toString().indexOf(":") + 1);
		String top_slab_registry_name = "";
		String bottom_slab_registry_name = "";
		
		//Find the appropriate slabs for the block by going through every in the slabs list. The pattern for the double slabs is bottom slab + _ + top slab.
		
		for(Block bottom_slab_block : VeSlabBlock.slabList())
		{
			for(Block top_slab_block : VeSlabBlock.slabList())
			{
				String bottom_slab_name = bottom_slab_block.getRegistryName().toString().substring(bottom_slab_block.getRegistryName().toString().indexOf(":") + 1).replace("_slab", "");
				String top_slab_name = top_slab_block.getRegistryName().toString().substring(top_slab_block.getRegistryName().toString().indexOf(":") + 1).replace("_slab", "");
				String bottom_slab_and_top_slab_name = bottom_slab_name + "_" + top_slab_name + "_double_slab";
				
				if(bottom_slab_and_top_slab_name.matches(double_slab_name))
				{
					bottom_slab_registry_name = bottom_slab_block.getRegistryName().toString();
					top_slab_registry_name = top_slab_block.getRegistryName().toString();
				}
			}
		}
		
		//Check if you are targeting the top or bottom half of the block and return the corresponding slab.
		
		if(target.getHitVec().y - pos.getY() > 0.5D)
		{
			return new ItemStack(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(top_slab_registry_name)));
		}
		return new ItemStack(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(bottom_slab_registry_name)));
	}
}
