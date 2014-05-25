package ds.plato.pick;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public class Pick extends Point3i {
	public Block block;
	public int metatdata;

	//TODO add parameter for metadata
	public Pick(int x, int y, int z, Block block) {
		super(x, y, z);
		this.block = block;
		//this.metadata = metadata;
	}

	public Point3d toDouble() {
		// Solved problem of end of line not being drawn.
		//return new Point3d(x + .5, y + .5, z + .5);
		return new Point3d(x, y, z);
	}
}
