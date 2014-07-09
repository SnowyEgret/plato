package ds.plato.select;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public class Selection {

	public int x, y, z;
	public Block block;
	public int metadata;

	public Selection(int x, int y, int z, Block block, int metadata) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.metadata = metadata;
	}

	@Deprecated
	public Selection(Point3i p, Block block, int metadata) {
		this(p.x, p.y, p.z, block, metadata);
	}

	public Point3d point3d() {
		return new Point3d(x, y, z);
	}

	public Point3i point3i() {
		return new Point3i(x, y, z);
		
	}

	@Override
	public String toString() {
		return "Selection [x=" + x + ", y=" + y + ", z=" + z + ", block=" + block + ", metadata=" + metadata + "]";
	}
}
