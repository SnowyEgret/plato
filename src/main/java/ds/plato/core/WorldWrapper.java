package ds.plato.core;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldWrapper implements IWorld {

	private World world;

	public WorldWrapper(World world) {
		this.world = world;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		//TODO Test for WorldClient and send a packet to server
		return world.getBlock(x, y, z);
	}

	@Override
	public int getMetadata(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public void setBlock(int x, int y, int z, Block block, int metadata) {
		world.setBlock(x, y, z, block, metadata, 3);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorldWrapper [world=");
		builder.append(world);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public World getWorld() {
		return world;
	}

}
