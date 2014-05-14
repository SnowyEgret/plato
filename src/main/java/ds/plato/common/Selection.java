package ds.plato.common;

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

	public Selection(Point3i p, Block id, int metadata) {
		this(p.x, p.y, p.z, id, metadata);
	}

	public Selection(Point3d p, Block block, int metadata) {
		this((int) p.x, (int) p.y, (int) p.z, block, metadata);
	}

	public Point3d getPoint3d() {
		//Created problem with move. Because pick.toDouble() adds .5
		//return new Point3d(x + .5, y + .5, z + .5);
		return new Point3d(x, y, z);
	}

	public Point3i getPoint3i() {
		return new Point3i(x, y, z);
	}

	@Override
	public String toString() {
		return "Selection [x=" + x + ", y=" + y + ", z=" + z + ", block="
				+ block + ", metadata=" + metadata + "]";
	}
}
