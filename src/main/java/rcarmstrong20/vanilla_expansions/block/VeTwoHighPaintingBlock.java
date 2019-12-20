package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class VeTwoHighPaintingBlock extends VeSinglePaintingBlock
{
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
	public VeTwoHighPaintingBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = state.get(HORIZONTAL_FACING);
		direction.getOpposite().getAxisDirection();
		BlockPos blockPosNeg = pos.offset(direction, AxisDirection.NEGATIVE.getOffset());
		direction.getOpposite().getAxisDirection();
		BlockPos blockPosPos = pos.offset(direction, AxisDirection.POSITIVE.getOffset());
		BlockState air = Blocks.AIR.getDefaultState();
		
		if(worldIn.getBlockState(blockPosPos) != air && worldIn.getBlockState(blockPosPos.up()) != air || worldIn.getBlockState(blockPosNeg) != air && worldIn.getBlockState(blockPosNeg.up()) != air)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		Direction direction = stateIn.get(HORIZONTAL_FACING);
		direction.getAxisDirection();
		BlockPos blockPosNeg = currentPos.offset(direction, AxisDirection.NEGATIVE.getOffset());
		direction.getAxisDirection();
		BlockPos blockPosPos = currentPos.offset(direction, AxisDirection.POSITIVE.getOffset());
		BlockState blockStatePos = worldIn.getBlockState(blockPosPos);
		BlockState blockStatePosUp = worldIn.getBlockState(blockPosPos.up());
		BlockState blockStateNeg = worldIn.getBlockState(blockPosNeg);
		BlockState blockStateNegUp = worldIn.getBlockState(blockPosNeg.up());
		BlockState block = this.getDefaultState();
		BlockState air = Blocks.AIR.getDefaultState();
		
		if(blockStatePos == block || blockStatePosUp == block || blockStateNeg == block || blockStateNegUp == block)
		{
			if(blockStatePos == air || blockStatePosUp == air || blockStateNeg == air || blockStateNegUp == air)
			{
				return Blocks.AIR.getDefaultState();
			}
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		if(state.has(HALF))
		{
			if(state.get(HALF) == DoubleBlockHalf.UPPER)
			{
				worldIn.destroyBlock(pos.down(), false);
			}
			else if(state.get(HALF) == DoubleBlockHalf.LOWER)
			{
				worldIn.destroyBlock(pos.up(), false);
			}
			worldIn.destroyBlock(pos, false);
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(HALF, HORIZONTAL_FACING);
	}
}
