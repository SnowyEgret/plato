package ds.geom;

import javax.vecmath.Point3d;

import org.apache.commons.lang3.Range;

public abstract class Curve extends Primitive {

	protected Range<Double> rT;
	
	public Curve(Point3d origin) {
		super(origin);
	}

	public abstract Point3d pointAtParameter(double t);

	@Override
	public PointSet pointSet() {
		PointSet points = new PointSet();
		double inc = .01;
		for (double u = rT.getMinimum(); u <= rT.getMaximum(); u += inc) {
			Point3d p = pointAtParameter(u);
			//p.sub(new Point3d(.5, .5, .5));
			points.addPoint(p);
		}
		return points;
	}

}
