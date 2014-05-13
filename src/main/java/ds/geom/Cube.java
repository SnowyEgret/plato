package ds.geom;

import javax.vecmath.Point3d;

public class Cube extends Box {

	public Cube(Point3d origin, Point3d diagonalCorner) {
		super(origin, clampDiagonalCorner(origin, diagonalCorner));
	}

	// public Cube(Point3d origin, Point3d diagonalCorner, int multiple) {
	// super(origin, diagonalCorner(origin, diagonalCorner, multiple));
	// }
	//
	// public Cube(Point3d origin, Point3d diagonalCorner, boolean hasOddDimension) {
	// // super(origin, diagonalCorner(origin, diagonalCorner, hasOddDimension ? -1 : 2));
	// this(origin, diagonalCorner, hasOddDimension ? -1 : 2);
	// }

	// public Cube(Point3d p, Point3d p2) {
	// this(p, p2, 1);
	// this(p, p2, 1);
	// }

	// private static Point3d diagonalCorner(Point3d p0, Point3d p2, int multiple) {
	private static Point3d clampDiagonalCorner(Point3d origin, Point3d diagonalCorner) {
		Point3d p = new Point3d();
		p.sub(diagonalCorner, origin);
		//System.out.println("[Cube.clampDiagonalCorner] p=" + p);

		double max = greatestAbsoluteComponentOf(p);
		//System.out.println("[Cube.clampDiagonalCorner] max=" + max);

		// double d = 0;
		// double[] t = new double[3];
		// p.get(t);
		// for (double n : t) {
		// d = (Math.abs(n) > Math.abs(d)) ? n : d;
		// }

		// if (multiple == -1) {
		// d = Math.round(d / 2) * 2;
		// d += 1;
		// } else {
		// d = Math.round(d / multiple) * multiple;
		// }

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

	// public double dimension() {
	// // return (int) (rT.getMaximum() - rT.getMinimum()) +1;
	// return rT.getMaximum() - rT.getMinimum();
	// }

}
