package rcarmstrong20.vanilla_expansions.enums;

import net.minecraft.util.IStringSerializable;

public enum VeFourTilePainting implements IStringSerializable
{
	MAIN,
	TOP,
	LEFT,
	TOP_LEFT;
	
	public String toString()
	{
		return this.getName();
	}
	
	@Override
	public String getName()
	{
		if(this == MAIN)
		{
			return "main";
		}
		else if(this == TOP)
		{
			return "top";
		}
		else if(this == LEFT)
		{
			return "left";
		}
		else
		{
			return "top_left";
		}
	}
}
