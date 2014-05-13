package ds.geom;

import javax.vecmath.Point3d;

public interface Drawable {

	public VoxelSet voxelize();

	public PointSet pointSet();
	
	public Point3d getOrigin();
}
