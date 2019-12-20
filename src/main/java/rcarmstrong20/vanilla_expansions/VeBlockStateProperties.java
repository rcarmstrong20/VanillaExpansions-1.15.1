package rcarmstrong20.vanilla_expansions;

import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import rcarmstrong20.vanilla_expansions.enums.VeFourTilePainting;

public class VeBlockStateProperties 
{
	public static final EnumProperty<VeFourTilePainting> MULTI_PART_PAINTING = EnumProperty.create("multi_part_painting", VeFourTilePainting.class);
	public static final IntegerProperty PLUSH_STACK_1_3 = IntegerProperty.create("plush_stack", 1, 3);
}
