package ds.plato.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;

public class SetBlockMessage implements IMessage {

	public int x;
	public int y;
	public int z;
	public Block block;
	public int metadata;
	
	public SetBlockMessage() {
	}

	public SetBlockMessage(int x, int y, int z, Block block, int metadata) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.metadata = metadata;
		this.block = block;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = ByteBufUtils.readVarInt(buf, 2);
		y = ByteBufUtils.readVarInt(buf, 2);
		z = ByteBufUtils.readVarInt(buf, 2);
		int blockId = ByteBufUtils.readVarInt(buf, 2);
		System.out.println("[SetBlockMessage.fromBytes] blockId=" + blockId);
		block = Block.getBlockById(blockId);
		System.out.println("[SetBlockMessage.fromBytes] block=" + block);
		metadata = ByteBufUtils.readVarInt(buf, 1);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, x, 2);
		ByteBufUtils.writeVarInt(buf, y, 2);
		ByteBufUtils.writeVarInt(buf, z, 2);
		ByteBufUtils.writeVarInt(buf, Block.getIdFromBlock(block), 2);
		ByteBufUtils.writeVarInt(buf, metadata, 1);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetBlockMessage [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append(", block=");
		builder.append(block);
		builder.append(", metadata=");
		builder.append(metadata);
		builder.append("]");
		return builder.toString();
	}

}
