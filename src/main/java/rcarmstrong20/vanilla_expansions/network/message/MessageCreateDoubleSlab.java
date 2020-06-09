package rcarmstrong20.vanilla_expansions.network.message;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import rcarmstrong20.vanilla_expansions.tile_entity.VeDoubleSlabTileEntity;

public class MessageCreateDoubleSlab implements IMessage<MessageCreateDoubleSlab>
{
	private BlockPos pos;
	
	public MessageCreateDoubleSlab(BlockPos pos)
	{
		this.pos = pos;
	}
	
	public MessageCreateDoubleSlab() {}
	
	public void encode(MessageCreateDoubleSlab message, PacketBuffer buffer)
	{
		buffer.writeBlockPos(message.pos);
	}
	
	public MessageCreateDoubleSlab decode(PacketBuffer buffer)
	{
		return new MessageCreateDoubleSlab(buffer.readBlockPos());
	}
	/*
	public void handle(MessageCreateDoubleSlab message, Supplier<NetworkEvent.Context> supplier)
	{
		((NetworkEvent.Context)supplier.get()).enqueueWork(() ->
		{
			try
			{
				ServerPlayerEntity player = ((NetworkEvent.Context)supplier.get()).getSender();
				if (player != null)
				{
					if (player..func_77973_b() != ModItems.HAMMER)
						return; 
					double reachDistance = player.func_110148_a(PlayerEntity.REACH_DISTANCE).func_111126_e();
					if (!message.pos.func_218137_a((IPosition)player.func_174824_e(0.0F), reachDistance + 0.5D))
					{
						return;
					}
					ServerWorld world = player.func_71121_q();
					if (!world.isAreaLoaded(message.pos, 0))
						return; 
					BlockState state = world.func_180495_p(message.pos);
					if (state.isAir((IBlockReader)world, message.pos))
						return; 
					if (!((Boolean)Config.COMMON.allowAllBlocks.get()).booleanValue() && !state.func_177230_c().func_203417_a(Tags.Blocks.SLOPES)) {
						return;
					}
					ItemStack stack = state.func_177230_c().func_185473_a((IBlockReader)world, message.pos, state);
					if (!stack.func_190926_b())
					{
						SlopeState slopeState = (SlopeState)Slopes.getStates().get(Pair.of(message.direction, message.edge));
						if (slopeState != null)
						{
							BlockState newState = (BlockState)((BlockState)((BlockState)ModBlocks.SLOPE.func_176223_P().func_206870_a((IProperty)SlopeBlock.field_185512_D, (Comparable)slopeState.getDirection())).func_206870_a((IProperty)SlopeBlock.FLIPPED, Boolean.valueOf(slopeState.isFlipped()))).func_206870_a((IProperty)SlopeBlock.SIDEWAYS, Boolean.valueOf(slopeState.isSideways()));
							world.func_180501_a(message.pos, newState, 2);
							
							SlopeTileEntity slopeTileEntity = new SlopeTileEntity();
							slopeTileEntity.func_145829_t();
							slopeTileEntity.setStack(stack);
							world.func_175690_a(message.pos, (TileEntity)slopeTileEntity);
							
							world.func_184138_a(message.pos, newState, newState, 2);
							
							SoundType soundType = state.func_177230_c().getSoundType(state, (IWorldReader)world, message.pos, (Entity)player);
							world.func_184148_a(null, message.pos.func_177958_n() + 0.5D, message.pos.func_177956_o() + 0.5D, message.pos.func_177952_p() + 0.5D, soundType.func_185845_c(), SoundCategory.BLOCKS, 1.0F, 1.0F);
		
							if (!player.func_184812_l_())
							{
								player.func_184614_ca().func_222118_a(1, (LivingEntity)player, ());
							}
							
							PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(()), new MessageParticle(message.pos, Block.func_196246_j(state), Block.func_196246_j(newState)));
						}
						
					} 
				} 
			}
			catch (Exception e)
			{
				e.printStackTrace();
			} 
		});
		((NetworkEvent.Context)supplier.get()).setPacketHandled(true);
	}
	*/

	@Override
	public void handle(MessageCreateDoubleSlab paramT, Supplier<Context> paramSupplier)
	{
		TileEntity tileEntity = paramSupplier.get().getSender().getEntityWorld().getTileEntity(this.pos);
		
		if(tileEntity instanceof VeDoubleSlabTileEntity)
		{
			//VeDoubleSlabTileEntity doubleSlabTileEntity = (VeDoubleSlabTileEntity) tileEntity;
			
			//ItemStack itemStack = doubleSlabTileEntity.getInventory().get(0);
			//ItemStack itemStack1 = doubleSlabTileEntity.getInventory().get(1);
			
			
		}
	}
}
