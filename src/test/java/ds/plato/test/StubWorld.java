package ds.plato.test;

import java.util.HashMap;
import java.util.Map;

import ds.plato.api.IWorld;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class StubWorld implements IWorld {

	Map<String, Block> blocks = new HashMap<>();
	Map<String, Integer> metadata = new HashMap<>();
	Block dirt;
	
	public StubWorld(Block dirt) {
		this.dirt = dirt;
	}

	@Override
	public void setBlock(int x, int y, int z, Block block, int metadata) {
		blocks.put(encode(x, y, z), block);
		this.metadata.put(encode(x, y, z), Integer.valueOf(metadata));
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		Block b =  blocks.get(encode(x, y, z));
		if (b == null) {
			return dirt;
		} else {
			return b;
		}
	}

	@Override
	public int getMetadata(int x, int y, int z) {
		Integer m = metadata.get(encode(x, y, z));
		if (m == null) {
			return 0;
		} else {
			return m;
		}
	}

	@Override
	public String toString() {
		return "StubWorld" + Integer.toHexString(hashCode()) + " [blocks=" + blocks + ", metadata=" + metadata + "]";
	}

	private String encode(int x, int y, int z) {
		return "" + x + ":" + y + ":" + z;
	}

	@Override
	public World getWorld() {
		return null;
	}

}
