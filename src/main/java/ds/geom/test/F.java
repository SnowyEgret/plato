package ds.geom.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3d;

import ds.geom.Ball;
import ds.geom.Box;
import ds.geom.CircleXY;
import ds.geom.Cube;
import ds.geom.EllipticParaboloid;
import ds.geom.InfiniteLine;
import ds.geom.InfinitePlane;
import ds.geom.Line;
import ds.geom.PointSet;
import ds.geom.Primitive;
import ds.geom.Sphere;

public class F {

	static Random rand = new Random();
	static double scale = 200;
	static int rows = 20;

	public static Collection params(int rows, int columns, Callable<InfinitePlane> callable) throws Exception {
		Object[][] params = new Object[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				params[i][j] = callable.call();
			}
		}
		return Arrays.asList(params);
	}

	public static Object[][] vectorPoint(int rows) {
		Object[][] params = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
			params[i][0] = vector();
			params[i][1] = p();
		}
		return params;
	}

	public static Object[][] linePlane() {
		Object[][] params = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
			params[i][0] = line();
			params[i][1] = plane();
		}
		return params;
	}

	public static Object[][] planePoint() {
		Object[][] params = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
			params[i][0] = plane();
			params[i][1] = p();
		}
		return params;
	}

	public static Object[][] plane_PointOnPlane_PointNotOnPlane() {
		Object[][] params = new Object[rows][3];
		for (int i = 0; i < rows; i++) {
			InfinitePlane infinitePlane = plane();
			params[i][0] = infinitePlane;
			params[i][1] = infinitePlane.p1;
			params[i][2] = p();
		}
		return params;
	}

	public static Object[][] plane_PointAbovePlane(double distanceAbovePlane) {
		Object[][] params = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
			InfinitePlane infinitePlane = plane();
			params[i][0] = infinitePlane;

			Vector3d n = infinitePlane.normal();
			n.scale(distanceAbovePlane);
			Point3d p = infinitePlane.getOrigin();
			p.add(n);
			params[i][1] = p;
		}
		return params;
	}

	public static Object[][] line_PointOnLine_PointNotOnLine() {
		Object[][] params = new Object[rows][3];
		for (int i = 0; i < rows; i++) {
			InfiniteLine infiniteLine = line();
			params[i][0] = infiniteLine;
			params[i][1] = infiniteLine.p1;
			params[i][2] = p();
		}
		return params;
	}

	// public static Object[][] randomLinePointBesideLine(double perpDistanceFromLine) {
	// Object[][] params = new Object[rows][2];
	// for (int ordinal = 0; ordinal < rows; ordinal++) {
	// Line l = randomLine();
	// params[ordinal][0] = l;
	//
	// Point3d p = l.origin();
	// Vector3d d = l.direction().anyOrthogonalUnitVector();
	// d.scale(perpDistanceFromLine);
	// p.add(d);
	// params[ordinal][1] = p;
	// }
	// return params;
	// }

	public static Object[][] points(int columns) {
		Object[][] params = new Object[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				params[i][j] = p();
			}
		}
		return params;
	}

	public static Object[][] vectors(int rows, int columns) {
		Object[][] params = new Object[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				params[i][j] = vector();
			}
		}
		return params;
	}

	public static Object[][] planes(int rows, int columns) {
		Object[][] params = new Object[rows][columns];
		for (int i = 0; i < rows; i++) {
			params[i][0] = plane();
		}
		return params;
	}

	public static Point3d p() {
		return new Point3d(d(), d(), d());
	}

	public static Point3i p3i() {
		return new Point3i(i(), i(), i());
	}

	public Point3i p3i(int n) {
		return new Point3i(n, n, n);
	}

	public Point3d p(int x, int y, int z) {
		return new Point3d(x, y, z);
	}

	public static Vector3d vector() {
		return new Vector3d(d(), d(), d());
	}

	public static InfiniteLine line() {
		return new InfiniteLine(p(), p());
	}

	public static InfinitePlane plane() {
		return new InfinitePlane(p(), p(), p());
	}

	public static Line lineSegment() {
		return new Line(p(), p());
	}

	public static PointSet pointSet(int size) {
		PointSet s = new PointSet();
		for (int i = 0; i < size; i++) {
			s.addPoint(p());
		}
		return s;
	}

	public static CircleXY circleXY(Point3d origin) {
		return new CircleXY(origin, p());
	}

	static double d() {
		return Math.round((rand.nextDouble() * scale) - scale / 2);
	}

	static int i() {
		return (int) d();
	}

	public static Sphere sphere(Point3d origin) {
		return new Sphere(origin, p());
	}

	public static Ball ball(Point3d origin) {
		return new Ball(origin, p());
	}

	public static Primitive box(Point3d o) {
		return new Box(o, p());
	}

	public static Primitive box() {
		return new Box(o(), p());
	}

	public static Point3d o() {
		return new Point3d(0, 0, 0);
	}

	public int randInt(Random rand, int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}

	public static Primitive elipticParaboloid(Point3d origin) {
		return new EllipticParaboloid(origin, p(), p());
	}

	// public static Primitive cube(int ordinal) {
	// return new Cube(p(), p(), 3);
	// }

	public static Point3d p(double d) {
		return new Point3d(d, d, d);
	}

	public static Primitive cube() {
		return new Cube(p(), p());
	}

}
