package rcarmstrong20.vanilla_expansions.network.message;


import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IMessage<T>
{
	void encode(T paramT, PacketBuffer paramPacketBuffer);
	
	T decode(PacketBuffer paramPacketBuffer);
	
	void handle(T paramT, Supplier<NetworkEvent.Context> paramSupplier);
}
