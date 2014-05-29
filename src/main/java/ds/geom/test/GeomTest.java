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

public class GeomTest {

	Random rand = new Random();
	double scale = 200;

	public Point3d p() {
		return new Point3d(d(), d(), d());
	}

	public Point3i p3i() {
		return new Point3i(i(), i(), i());
	}

	public Point3i p3i(int n) {
		return new Point3i(n, n, n);
	}

	public Point3d p(int x, int y, int z) {
		return new Point3d(x, y, z);
	}

	public Vector3d vector() {
		return new Vector3d(d(), d(), d());
	}

	public InfiniteLine line() {
		return new InfiniteLine(p(), p());
	}

	public InfinitePlane plane() {
		return new InfinitePlane(p(), p(), p());
	}

	public Line lineSegment() {
		return new Line(p(), p());
	}

	public PointSet pointSet(int size) {
		PointSet s = new PointSet();
		for (int i = 0; i < size; i++) {
			s.addPoint(p());
		}
		return s;
	}

	public CircleXY circleXY(Point3d origin) {
		return new CircleXY(origin, p());
	}

	double d() {
		return Math.round((rand.nextDouble() * scale) - scale / 2);
	}

	int i() {
		return (int) d();
	}

	public Sphere sphere(Point3d origin) {
		return new Sphere(origin, p());
	}

	public Ball ball(Point3d origin) {
		return new Ball(origin, p());
	}

	public Primitive box(Point3d o) {
		return new Box(o, p());
	}

	public Primitive box() {
		return new Box(o(), p());
	}

	public Point3d o() {
		return new Point3d(0, 0, 0);
	}

	public int randInt(Random rand, int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}

	public Primitive elipticParaboloid(Point3d origin) {
		return new EllipticParaboloid(origin, p(), p());
	}

	public Point3d p(double d) {
		return new Point3d(d, d, d);
	}

	public Primitive cube() {
		return new Cube(p(), p());
	}

}