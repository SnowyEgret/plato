package ds.plato.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang3.Range;

public class Box extends Solid {


	public Box(Point3d origin, Point3d diagonalCorner) {
		super(origin);

		Vector3d d = new Vector3d();
		d.sub(diagonalCorner, origin);

		rT = Range.between(0d, d.x);
		rU = Range.between(0d, d.y);
		rV = Range.between(0d, d.z);
	}

	@Override
	public boolean contains(Point3d point) {
		//Point3d p = new Point3d(point);
		//p.sub(p0);
		return true;
		//return rangeT().contains(p.x) && rangeU().contains(p.y) && rangeV().contains(p.z);
	}

	@Override
	public String toString() {
		return "Box [rangeT=" + rT + ", rangeU=" + rU + ", rangeV=" + rV + ", p0=" + p0 + "]";
	}

}
