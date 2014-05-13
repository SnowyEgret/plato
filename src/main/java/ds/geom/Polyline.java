package ds.geom;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

public class Polyline extends Primitive {

	List<Line> lines = new ArrayList<>();
	
	protected Polyline(Point3d origin) {
		super(origin);
	}

//	public Polyline(List<Point3d> points) {
//		super(points.get(0));
//		for(int ordinal = 0; ordinal<points.size(); ordinal+=2) {
//			lines.add(new Line(points.get(ordinal), points.get(ordinal+1)));
//		}
//	}

	// public Polyline(List<Line> lines) {
	// super(lines.get(0).getOrigin());
	// this.lines = lines;
	// }

	@Override
	public boolean contains(Point3d p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PointSet pointSet() {
		PointSet points = new PointSet();
		for (Line line : lines) {
			points.addPoints(line.pointSet());
		}
		return points;
	}

}
