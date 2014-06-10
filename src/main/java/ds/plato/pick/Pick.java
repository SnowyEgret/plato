package ds.plato.pick;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public class Pick extends Point3i {
	
	public Block block;
	public int metadata;

	public Pick(int x, int y, int z, Block block, int metadata) {
		super(x, y, z);
		this.block = block;
		this.metadata = metadata;
	}

	public Point3d toPoint3d() {
		return new Point3d(x, y, z);
	}
}
