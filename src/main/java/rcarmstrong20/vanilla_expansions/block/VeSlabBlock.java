package rcarmstrong20.vanilla_expansions.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import rcarmstrong20.vanilla_expansions.core.VeBlocks;

public class VeSlabBlock extends SlabBlock
{
	public VeSlabBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		ItemStack itemstack = context.getItem();
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		
		/*
		 * Go Through all the slabs in the list looking up the appropriate double slab using their registry names.
		 */
		
		for(Block block : slabList())
		{
			if(this != block)
			{
				String slab_name = block.getRegistryName().toString();
				String slab_name_2 = this.getRegistryName().toString();
				slab_name = slab_name.replaceFirst("slab", "");
				slab_name_2 = slab_name_2.replaceFirst("slab", "");
				String bottom_slab_name_2 = slab_name_2.substring(slab_name_2.indexOf(":") + 1);
				String top_slab_name = slab_name.substring(slab_name.indexOf(":") + 1);
				slab_name = slab_name.replace("minecraft", "ve");
				slab_name_2 = slab_name_2.replace("minecraft", "ve");
				String bottom_double_slab = slab_name + bottom_slab_name_2 + "double_slab";
				String top_double_slab = slab_name_2 + top_slab_name + "double_slab";
				
				if(blockstate.getBlock() == block && itemstack.getItem() == this.asItem())
				{
					if(blockstate.get(TYPE) == SlabType.BOTTOM)
					{
						return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(bottom_double_slab)).getDefaultState();
					}
					else
					{
						return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(top_double_slab)).getDefaultState();
					}
				}
			}
		}
		return super.getStateForPlacement(context);
	}
	
	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		ItemStack itemstack = useContext.getItem();
		SlabType slabtype = state.get(TYPE);
		
		for(Block block : slabList())
		{
			if (itemstack.getItem() == block.asItem())
			{
				if (useContext.replacingClickedOnBlock())
				{
					boolean flag = useContext.getHitVec().y - (double)useContext.getPos().getY() > 0.5D;
					Direction direction = useContext.getFace();
					if (slabtype == SlabType.BOTTOM)
					{
						return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
					}
					else
					{
						return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
					}
				}
				else
				{
					return true;
				}
			}
		}
		return super.isReplaceable(state, useContext);
	}
	
	public static List<Block> slabList()
	{
		List<Block> slabs = new ArrayList<Block>();
		slabs.add(VeBlocks.smoky_quartz_slab);
		slabs.add(VeBlocks.smooth_smoky_quartz_slab);
		slabs.add(VeBlocks.cracked_stone_brick_slab);
		slabs.add(VeBlocks.dirt_slab);
		slabs.add(VeBlocks.coarse_dirt_slab);
		slabs.add(VeBlocks.snow_brick_slab);
		slabs.add(VeBlocks.packed_snow_slab);
		slabs.add(VeBlocks.prismarine_slab);
		slabs.add(VeBlocks.prismarine_brick_slab);
		slabs.add(VeBlocks.dark_prismarine_slab);
		slabs.add(VeBlocks.oak_slab);
		slabs.add(VeBlocks.spruce_slab);
		slabs.add(VeBlocks.birch_slab);
		slabs.add(VeBlocks.jungle_slab);
		slabs.add(VeBlocks.acacia_slab);
		slabs.add(VeBlocks.dark_oak_slab);
		slabs.add(VeBlocks.stone_slab);
		slabs.add(VeBlocks.smooth_stone_slab);
		slabs.add(VeBlocks.sandstone_slab);
		slabs.add(VeBlocks.cut_sandstone_slab);
		slabs.add(VeBlocks.petrified_oak_slab);
		slabs.add(VeBlocks.cobblestone_slab);
		slabs.add(VeBlocks.brick_slab);
		slabs.add(VeBlocks.stone_brick_slab);
		slabs.add(VeBlocks.nether_brick_slab);
		slabs.add(VeBlocks.quartz_slab);
		slabs.add(VeBlocks.red_sandstone_slab);
		slabs.add(VeBlocks.cut_red_sandstone_slab);
		slabs.add(VeBlocks.purpur_slab);
		slabs.add(VeBlocks.polished_granite_slab);
		slabs.add(VeBlocks.smooth_red_sandstone_slab);
		slabs.add(VeBlocks.mossy_stone_brick_slab);
		slabs.add(VeBlocks.polished_diorite_slab);
		slabs.add(VeBlocks.mossy_cobblestone_slab);
		slabs.add(VeBlocks.end_stone_brick_slab);
		slabs.add(VeBlocks.smooth_sandstone_slab);
		slabs.add(VeBlocks.smooth_quartz_slab);
		slabs.add(VeBlocks.granite_slab);
		slabs.add(VeBlocks.andesite_slab);
		slabs.add(VeBlocks.red_nether_brick_slab);
		slabs.add(VeBlocks.polished_andesite_slab);
		slabs.add(VeBlocks.diorite_slab);
		return slabs;
	}
}