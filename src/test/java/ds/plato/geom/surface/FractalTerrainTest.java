package ds.plato.geom.surface;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import ds.plato.geom.VoxelSet;
import ds.plato.geom.test.GeomTest;

public class FractalTerrainTest extends GeomTest {

	@Test
	public void test() throws Exception {
		int size = 8;
		int range = 20;
		Terrain t = new FractalTerrain(p(), size, range);
		System.out.println("[T_FractalTerrain.test] t=" + t);
		VoxelSet voxels = t.voxelize();
		System.out.println("[T_FractalTerrain.test] voxels=" + voxels);
		int root = (int) Math.pow(2, size) + 1;
		assertThat(voxels.size(), equalTo(root * root));
		System.out.println("[T_FractalTerrain.test] voxels.getDomain()=" + voxels.getDomain());
		//assertThat(voxels.getDomain().dz, equalTo(20));
		
		// for (Point3i v : voxels) {
		// System.out.println("[T_FractalTerrain.test] v=" + v);
		// }
	}

}
