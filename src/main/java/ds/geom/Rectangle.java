package ds.geom;

import javax.vecmath.Point3d;

public class Rectangle extends Polyline {

	public Rectangle(Point3d p0, Point3d p2) {
		super(p0);
		p2.y = p0.y;
		Point3d p1 = new Point3d(p0.x, p2.y, p2.z);
		Point3d p3 = new Point3d(p2.x, p2.y, p0.z);
		lines.add(new Line(p0, p1));
		lines.add(new Line(p1, p2));
		lines.add(new Line(p2, p3));
		lines.add(new Line(p3, p0));
	}
}
