package ds.geom;

import javax.vecmath.Point3d;

public class Cube extends Box {

	public Cube(Point3d origin, Point3d diagonalCorner) {
		super(origin, clampDiagonalCorner(origin, diagonalCorner));
	}

	private static Point3d clampDiagonalCorner(Point3d origin, Point3d diagonalCorner) {
		Point3d p = new Point3d();
		p.sub(diagonalCorner, origin);
		double max = greatestAbsoluteComponentOf(p);
		p.x = (p.x >= 0d) ? max : -max;
		p.y = (p.y >= 0d) ? max : -max;
		p.z = (p.z >= 0d) ? max : -max;
		//System.out.println("[Cube.clampDiagonalCorner] p after clamping=" + p);
		p.add(origin);
		//System.out.println("[Cube.clampDiagonalCorner] p after adding origin=" + p);
		return p;
	}

	private static double greatestAbsoluteComponentOf(Point3d p) {
		double max = (Math.abs(p.x));
		if (Math.abs(p.y) > max)
			max = p.y;
		if (Math.abs(p.z) > max)
			max = p.z;
		return max;
	}

	@Override
	public String toString() {
		return "Cube [rT=" + rT + ", rU=" + rU + ", rV=" + rV + ", p0=" + p0 + "]";
	}

}
