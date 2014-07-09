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

	public Point3d point3d() {
		return new Point3d(x, y, z);
	}

	public Point3i point3i() {
		return new Point3i(x, y, z);
	}
}
