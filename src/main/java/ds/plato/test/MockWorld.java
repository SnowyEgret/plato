package ds.plato.test;

import java.util.HashMap;
import java.util.Map;

import ds.plato.IWorld;
import net.minecraft.block.Block;

public class MockWorld implements IWorld {

	Map<String, Block> blocks = new HashMap<>();
	Map<String, Integer> metadata = new HashMap<>();

	@Override
	public void setBlock(int x, int y, int z, Block block, int metadata, int mode) {
		blocks.put(encode(x, y, z), block);
		this.metadata.put(encode(x, y, z), Integer.valueOf(metadata));
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return blocks.get(encode(x, y, z));
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
		return "MockWorld" + Integer.toHexString(hashCode()) + " [blocks=" + blocks + ", metadata=" + metadata + "]";
	}

	private String encode(int x, int y, int z) {
		return "" + x + ":" + y + ":" + z;
	}

}
