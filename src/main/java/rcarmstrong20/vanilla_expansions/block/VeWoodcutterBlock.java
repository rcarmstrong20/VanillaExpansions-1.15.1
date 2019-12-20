package rcarmstrong20.vanilla_expansions.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import rcarmstrong20.vanilla_expansions.inventory.container.VeWoodcutterContainer;

public class VeWoodcutterBlock extends StonecutterBlock
{
	private static final TranslationTextComponent name = new TranslationTextComponent("container.woodcutter");
	
	public VeWoodcutterBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) ->
		{
			return new VeWoodcutterContainer(p_220283_2_, p_220283_3_, IWorldPosCallable.of(worldIn, pos));
		}, name);
	}
}
