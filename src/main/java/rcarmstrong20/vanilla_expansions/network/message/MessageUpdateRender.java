package rcarmstrong20.vanilla_expansions.network.message;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import rcarmstrong20.vanilla_expansions.util.RenderUtil;

public class MessageUpdateRender implements IMessage<MessageUpdateRender>
{
	private BlockPos pos;
	
	public MessageUpdateRender() {}
	
	public MessageUpdateRender(BlockPos pos)
	{
		this.pos = pos;
	}
	
	public void encode(MessageUpdateRender message, PacketBuffer buffer)
	{
		buffer.writeBlockPos(message.pos);
	}
	
	@Override
	public MessageUpdateRender decode(PacketBuffer buffer)
	{
		return new MessageUpdateRender(buffer.readBlockPos());
	}
	
	public void handle(MessageUpdateRender message, Supplier<NetworkEvent.Context> supplier)
	{
		((NetworkEvent.Context)supplier.get()).enqueueWork(() -> RenderUtil.causeRenderUpdate(message.pos));
		((NetworkEvent.Context)supplier.get()).setPacketHandled(true);
	}
}

