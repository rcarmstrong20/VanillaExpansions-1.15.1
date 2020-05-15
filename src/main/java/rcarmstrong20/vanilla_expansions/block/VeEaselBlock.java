package rcarmstrong20.vanilla_expansions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.inventory.container.VeEaselContainer;

public class VeEaselBlock extends HorizontalBlock
{
	private static final TranslationTextComponent EASEL_CONTAINER_NAME = new TranslationTextComponent("container.easel");
	
	public VeEaselBlock(Properties properties)
	{
		super(properties);
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (worldIn.isRemote)
		{
			return ActionResultType.SUCCESS;
		}
		else
		{
			player.openContainer(state.getContainer(worldIn, pos));
			return ActionResultType.SUCCESS;
		}
	}
	
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos)
	{
		return new SimpleNamedContainerProvider((p_220254_2_, p_220254_3_, p_220254_4_) ->
		{
			return new VeEaselContainer(p_220254_2_, p_220254_3_, IWorldPosCallable.of(worldIn, pos));
		}, EASEL_CONTAINER_NAME);
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING);
	}
}
