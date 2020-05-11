package rcarmstrong20.vanilla_expansions.util;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.fml.network.PacketDistributor;
import rcarmstrong20.vanilla_expansions.network.message.MessageUpdateRender;
import rcarmstrong20.vanilla_expansions.network.message.PacketHandler;

public class RenderUtil
{
	@OnlyIn(Dist.CLIENT)
	public static void causeRenderUpdate(BlockPos pos)
	{
		//(Minecraft.getInstance()).gameRenderer.blit(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
		TileEntity tileEntity = (Minecraft.getInstance()).world.getTileEntity(pos);
		if (tileEntity != null)
		{
			ModelDataManager.requestModelDataRefresh(tileEntity);
		}
	}
	
	public static void sendRenderUpdate(World world, BlockPos pos)
	{
		if (world instanceof ServerWorld)
		{
			ServerWorld server = (ServerWorld)world;
			PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> (Chunk)server.getWorld().getChunk(pos)), new MessageUpdateRender(pos));
		}
	}
}