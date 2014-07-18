package ds.plato.pick;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public class Pick extends Point3i {
	
	public Block block;
	public int metadata;
	public int side;

	public Pick(int x, int y, int z, Block block, int metadata, int side) {
		super(x, y, z);
		this.block = block;
		this.metadata = metadata;
		this.side = side;
	}

	public Point3d point3d() {
		return new Point3d(x, y, z);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pick [block=");
		builder.append(block);
		builder.append(", metadata=");
		builder.append(metadata);
		builder.append(", side=");
		builder.append(side);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append("]");
		return builder.toString();
	}

	public Point3i point3i() {
		return new Point3i(x, y, z);
	}
}
