package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import ds.geom.ImageBasedTerrain;
import ds.geom.Terrain;
import ds.geom.VoxelSet;

public class T_ImageBasedTerrain extends F {

	@Test
	public void test() throws Exception {
		int size = 9;
		int range = 100;
		Terrain t = new ImageBasedTerrain(p(), size, range, "mountains512.png");
		System.out.println("[T_ImageBasedTerrain.test] t=" + t);
		VoxelSet voxels = t.voxelize();
		System.out.println("[T_ImageBasedTerrain.test] voxels=" + voxels);
		int root = (int) Math.pow(2, size); //not + 1 this time!
		assertThat(voxels.size(), equalTo(root * root));
		System.out.println("[T_ImageBasedTerrain.test] voxels.getDomain()=" + voxels.getDomain());
	}

}
