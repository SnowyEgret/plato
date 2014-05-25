package ds.geom.test;

import org.junit.Test;

import ds.geom.Tetrahedron;
import ds.geom.VoxelSet;

public class T_Tetrahedron extends GeomTest {

	@Test
	public void test() {
		scale = 25;
		Tetrahedron t = new Tetrahedron(p(), p());
		System.out.println("[T_Tetrahedron.test] t=" + t);
		VoxelSet voxels = t.voxelize();
		System.out.println("[T_Tetrahedron.test] voxels=" + voxels);
	}

}
