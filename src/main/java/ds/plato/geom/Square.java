package ds.plato.geom;

import javax.vecmath.Point3d;

public class Square extends Rectangle {

	public Square(Point3d p0, Point3d p2) {
		super(p0, squareOf(p0, p2));
	}

	private static Point3d squareOf(Point3d p0, Point3d p2) {
		double dx = p2.x - p0.x;
		double dz = p2.z - p0.z;
		double d = dz - dx;
		if (d <= 0) { // dx is greater
			p2.z -= d;
		} else {
			p2.x -= d;
		}
		return p2;
	}

}
