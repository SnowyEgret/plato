package ds.geom;

import javax.vecmath.Point3d;

import org.apache.commons.lang3.Range;

import com.jme3.terrain.heightmap.HeightMap;

public abstract class Terrain extends Surface {

	protected float[] heights;
	protected int heightsArrayIndex = 0;
	protected final int sizeAsPowerOfTwo;
	protected double range;

	protected Terrain(Point3d origin, int sizeAsPowerOfTwo, double range) {
		super(origin);
		this.sizeAsPowerOfTwo = sizeAsPowerOfTwo;
		this.range = range;
	}

	// To allow subclasses to initialize fields before completing the initialization this superclass
	protected void init() {
		HeightMap heightMap = createHeightMap();
		System.out.println("[AbstractTerrain.AbstractTerrain] heightMap=" + heightMap);
		heights = heightMap.getHeightMap();
		System.out.println("[AbstractTerrain.AbstractTerrain] heights=" + heights);
		double s = Math.sqrt(heights.length);
		rU = Range.between(0d, s);
		rV = Range.between(0d, s);
		// This way the number of points equals the number of heights
		inc = 1d;
		System.out.println("[Terrain.Terrain] heightData.length=" + heights.length);
		System.out.println("[Terrain.Terrain] s*s=" + s * s);
	}

	protected abstract HeightMap createHeightMap();

	@Override
	public Point3d pointAtParameters(double u, double v) {
		//Using y axis is vertical
		Point3d p = new Point3d(u, heights[heightsArrayIndex] * range / 255, v);
		p.add(p0);
		heightsArrayIndex++;
		return p;
	}

	@Override
	public boolean contains(Point3d p) {
		throw new UnsupportedOperationException("Method 'Terrain.contains' not yet implemented");
	}
}
