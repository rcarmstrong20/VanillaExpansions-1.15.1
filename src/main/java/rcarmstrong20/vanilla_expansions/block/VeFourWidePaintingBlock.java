/*
package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.VeBlockStateProperties;
import rcarmstrong20.vanilla_expansions.enums.VeFourTilePainting;

public class VeFourWidePaintingBlock extends VeSinglePaintingBlock
{
	public static final EnumProperty<VeFourTilePainting> PAINTING_PART = VeBlockStateProperties.MULTI_PART_PAINTING;
	
	public VeFourWidePaintingBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(PAINTING_PART, VeFourTilePainting.MAIN).with(HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState air = Blocks.AIR.getDefaultState();
		if(worldIn.getBlockState(pos.up()) == air && worldIn.getBlockState(pos.west()) == air && worldIn.getBlockState(pos.up().west()) == air && super.isValidPosition(state, worldIn, pos))
		{
			return true;
		}
		return false;
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if(this.isValidPosition(state, worldIn, pos))
		{
			if(this.getDefaultState().get(HORIZONTAL_FACING) == Direction.NORTH.getOpposite())
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PAINTING_PART, VeFourTilePainting.TOP).with(HORIZONTAL_FACING, Direction.NORTH.getOpposite()), 3);
				worldIn.setBlockState(pos.west(), this.getDefaultState().with(PAINTING_PART, VeFourTilePainting.LEFT).with(HORIZONTAL_FACING, Direction.NORTH.getOpposite()), 3);
				worldIn.setBlockState(pos.up().west(), this.getDefaultState().with(PAINTING_PART, VeFourTilePainting.TOP_LEFT).with(HORIZONTAL_FACING, Direction.NORTH.getOpposite()), 3);
			}
			
			else if(this.getDefaultState().get(HORIZONTAL_FACING) == Direction.NORTH)
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.west(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.up().west(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP_LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
			}
			
			else if(this.getDefaultState().get(HORIZONTAL_FACING) == Direction.WEST)
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.south(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.up().south(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP_LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
			}
			else if(this.getDefaultState().get(HORIZONTAL_FACING) == Direction.EAST)
			{
				worldIn.setBlockState(pos.up(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.south(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
				worldIn.setBlockState(pos.up().south(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP_LEFT).with(HORIZONTAL_FACING, Direction.SOUTH), 3);
			}
			
		}
	}
	
	public void placeAt(IWorld worldIn, BlockPos pos, int flags)
	{
		worldIn.setBlockState(pos, this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.MAIN), flags);
		worldIn.setBlockState(pos.up(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP), flags);
		worldIn.setBlockState(pos.west(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.LEFT), flags);
		worldIn.setBlockState(pos.up().west(), this.getDefaultState().with(PAINTING_PART, VeMultiPartPainting.TOP_LEFT), flags);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(PAINTING_PART, HORIZONTAL_FACING);
	}
}
*/