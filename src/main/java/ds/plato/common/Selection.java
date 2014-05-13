package ds.plato.common;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

//public class Selection extends Point3i {
public class Selection {

	public int x, y, z;
	public Block block;
	public int metadata;

	public Selection(int x, int y, int z, Block block, int metadata) {
		// super(x, y, z);
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

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + x;
//		result = prime * result + y;
//		result = prime * result + z;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Selection other = (Selection) obj;
//		if (x != other.x)
//			return false;
//		if (y != other.y)
//			return false;
//		if (z != other.z)
//			return false;
//		return true;
//	}

}
