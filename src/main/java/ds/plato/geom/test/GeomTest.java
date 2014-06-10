package ds.plato.geom.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3d;

import ds.plato.geom.PointSet;
import ds.plato.geom.Primitive;
import ds.plato.geom.curve.CircleXZ;
import ds.plato.geom.curve.InfiniteLine;
import ds.plato.geom.curve.Line;
import ds.plato.geom.solid.Ball;
import ds.plato.geom.solid.Box;
import ds.plato.geom.solid.Cube;
import ds.plato.geom.surface.EllipticParaboloid;
import ds.plato.geom.surface.InfinitePlane;
import ds.plato.geom.surface.Sphere;

public class GeomTest {

	Random rand = new Random();
	double scale = 200;
	protected double epsilon = .000000001;

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

	public CircleXZ circleXY(Point3d origin) {
		return new CircleXZ(origin, p());
	}

	double d() {
		return Math.round((rand.nextDouble() * scale) - scale / 2);
	}

	int i() {
		return (int) d();
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
