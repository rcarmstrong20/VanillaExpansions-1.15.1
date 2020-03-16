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
		slabs.add(VeBlocks.PRISMARINE_SLAB);
		slabs.add(VeBlocks.PRISMARINE_BRICK_SLAB);
		slabs.add(VeBlocks.DARK_PRISMARINE_SLAB);
		slabs.add(VeBlocks.OAK_SLAB);
		slabs.add(VeBlocks.SPRUCE_SLAB);
		slabs.add(VeBlocks.BIRCH_SLAB);
		slabs.add(VeBlocks.JUNGLE_SLAB);
		slabs.add(VeBlocks.ACACIA_SLAB);
		slabs.add(VeBlocks.DARK_OAK_SLAB);
		slabs.add(VeBlocks.STONE_SLAB);
		slabs.add(VeBlocks.SMOOTH_STONE_SLAB);
		slabs.add(VeBlocks.SANDSTONE_SLAB);
		slabs.add(VeBlocks.CUT_SANDSTONE_SLAB);
		slabs.add(VeBlocks.PETRIFIED_OAK_SLAB);
		slabs.add(VeBlocks.COBBLESTONE_SLAB);
		slabs.add(VeBlocks.BRICK_SLAB);
		slabs.add(VeBlocks.STONE_BRICK_SLAB);
		slabs.add(VeBlocks.NETHER_BRICK_SLAB);
		slabs.add(VeBlocks.QUARTZ_SLAB);
		slabs.add(VeBlocks.RED_SANDSTONE_SLAB);
		slabs.add(VeBlocks.CUT_RED_SANDSTONE_SLAB);
		slabs.add(VeBlocks.PURPUR_SLAB);
		slabs.add(VeBlocks.POLISHED_GRANITE_SLAB);
		slabs.add(VeBlocks.SMOOTH_RED_SANDSTONE_SLAB);
		slabs.add(VeBlocks.MOSSY_STONE_BRICK_SLAB);
		slabs.add(VeBlocks.POLISHED_DIORITE_SLAB);
		slabs.add(VeBlocks.MOSSY_COBBLESTONE_SLAB);
		slabs.add(VeBlocks.END_STONE_BRICK_SLAB);
		slabs.add(VeBlocks.SMOOTH_SANDSTONE_SLAB);
		slabs.add(VeBlocks.SMOOTH_QUARTZ_SLAB);
		slabs.add(VeBlocks.GRANITE_SLAB);
		slabs.add(VeBlocks.ANDESITE_SLAB);
		slabs.add(VeBlocks.RED_NETHER_BRICK_SLAB);
		slabs.add(VeBlocks.POLISHED_ANDESITE_SLAB);
		slabs.add(VeBlocks.DIORITE_SLAB);
		return slabs;
	}
}