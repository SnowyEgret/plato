package ds.plato.geom.solid;

import org.junit.Test;

import ds.plato.geom.VoxelSet;
import ds.plato.geom.test.GeomTest;

public class TetrahedronTest extends GeomTest {

	@Test
	public void test() {
		scale = 25;
		Tetrahedron t = new Tetrahedron(p(), p());
		System.out.println("[T_Tetrahedron.test] t=" + t);
		VoxelSet voxels = t.voxelize();
		System.out.println("[T_Tetrahedron.test] voxels=" + voxels);
	}

}
