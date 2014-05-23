package ds.plato;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldWrapper implements IWorld {

	private World world;

	public WorldWrapper(World world) {
		this.world = world;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return world.getBlock(x, y, z);
	}

	@Override
	public int getMetadata(int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public void setBlock(int x, int y, int z, Block block, int metadata, int mode) {
		world.setBlock(x,  y,  x, block);
		world.setBlockMetadataWithNotify(x, y, z, metadata, mode);
	}

}
