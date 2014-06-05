package ds.plato.geom;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang3.Range;

public class Line extends Curve {

	Vector3d dir;

	public Line(Point3d origin, Point3d endPoint) {
		super(origin);
		dir = new Vector3d();
		dir.sub(endPoint, origin);
		rT = Range.between(epsilon, 1d); // Unit test fails with origin at 0;
		//rangeT = Range.between(0d, 1d);
	}

	//https://ca.answers.yahoo.com/question/index?qid=20080830195656AA3aEBr
	@Override
	public Point3d pointAtParameter(double t) {
		Point3d p = new Point3d();
		p.x = p0.x + dir.x * t;
		p.y = p0.y + dir.y * t;
		p.z = p0.z + dir.z * t;
		return p;
	}

	@Override
	public boolean contains(Point3d p) {
		Vector3d vP = new Vector3d();
		//vP.sub(p0, p);
		vP.sub(p, p0);
		vP.normalize();
		Vector3d vD = new Vector3d(dir);
		vD.normalize();
		double d = Math.abs(vP.dot(vD)) - 1;
		//System.out.println("[Line2.contains] d=" + d);
		return (d < epsilon);
	}

	@Override
	public String toString() {
		return "Line2 [dir=" + dir + ", rangeT=" + rT + ", p0=" + p0 + "]";
	}
	
}
