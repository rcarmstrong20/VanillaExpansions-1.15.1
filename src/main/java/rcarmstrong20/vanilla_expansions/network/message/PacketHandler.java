package rcarmstrong20.vanilla_expansions.network.message;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import rcarmstrong20.vanilla_expansions.VanillaExpansions;

public class PacketHandler
{
	public static final String PROTOCOL_VERSION = "1";
	public static SimpleChannel instance;
	private static int nextId = 0;
	
	public static void init()
	{
		instance = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(VanillaExpansions.MOD_ID, "network")).networkProtocolVersion(() -> "1").clientAcceptedVersions("1"::equals).serverAcceptedVersions("1"::equals).simpleChannel();
		register(MessageUpdateRender.class, (IMessage<MessageUpdateRender>)new MessageUpdateRender());
	}
	
	private static <T> void register(Class<T> clazz, IMessage<T> message)
	{
		instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
	}
}
