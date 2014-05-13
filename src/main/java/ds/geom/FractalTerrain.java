package ds.geom;

import javax.vecmath.Point3d;

import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.terrain.heightmap.MidpointDisplacementHeightMap;

public class FractalTerrain extends Terrain {

	public FractalTerrain(Point3d origin, int sizeAsPowerOfTwo, double range) {
		super(origin, sizeAsPowerOfTwo, range);
		init();
	}
	
	@Override
	protected HeightMap createHeightMap() {
		try {
			return new MidpointDisplacementHeightMap((int) Math.pow(2, sizeAsPowerOfTwo) + 1, (float) range, .5f);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not create height map");
		}
	}

	@Override
	public String toString() {
		return "FractalTerrain [rU=" + rU + ", rV=" + rV + ", p0=" + p0 + "]";
	}

}
