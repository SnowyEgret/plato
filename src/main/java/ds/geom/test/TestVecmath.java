package ds.geom.test;

import static org.junit.Assert.assertArrayEquals;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//http://hub.jmonkeyengine.org/wiki/doku.php/jme3:math_for_dummies
public class TestVecmath {
	
	static double epsilon = .000001;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Point3d p = new Point3d(1, 2, 3);
		// Vector3d v = new Vector3d(5,6,7);
		assertArrayEquals(new double[] { 1, 2, 3 }, toArray(p), epsilon);
		Matrix4d m = new Matrix4d();
		m.setTranslation(new Vector3d(3, 3, 3));
		m.transform(p);
		assertArrayEquals(new double[] { 3, 3, 3 }, toArray(p), epsilon);
	}

	@Test
	public void testVectorAddition() {
		Vector3d v1 = new Vector3d(0, 1, 0);
		Vector3d v2 = new Vector3d(1, 0, 0);
		v1.add(v2);
		assertArrayEquals(new double[] { 1, 1, 0 }, toArray(v1), epsilon);
	}

	@Test
	public void testVectorSubstaction() {
		Vector3d v1 = new Vector3d(0, 1, 0);
		Vector3d v2 = new Vector3d(1, 0, 0);
		v1.sub(v2);
		assertArrayEquals(new double[] { -1, 1, 0 }, toArray(v1), epsilon);
	}

	@Test
	public void testVectorMultiplication() {
		Vector3d v = new Vector3d(1, 1, 0);
		v.scale(1.5);
		assertArrayEquals(new double[] { 1.5, 1.5, 0 }, toArray(v), epsilon);
	}

	@Test
	public void testVectorNormalization() {
		Vector3d v = new Vector3d(1, 1, 0);
		v.normalize();
		assertArrayEquals(new double[] { .7, .7, 0 }, toArray(v), epsilon);
	}
	
	@Test
	public void testVectorInterpolation() {
		Vector3d v1 = new Vector3d(0, 1, 0);
		Vector3d v2 = new Vector3d(1, 0, 0);
		v1.interpolate(v2, .5);
		assertArrayEquals(new double[] { .5, .5, 0 }, toArray(v1), epsilon);
	}

	@Test
	public void testVectorInterpolation2() {
		Vector3d v1 = new Vector3d(0, 1, 0);
		Vector3d v2 = new Vector3d(1, 0, 0);
		v1.interpolate(v2, .75);
		assertArrayEquals(new double[] { .75, .25, 0 }, toArray(v1), epsilon);
	}

	//https://www.mail-archive.com/java3d-interest@java.sun.com/msg06245.html
	@Test
	public void testQuaternionRotation() {
		Vector3d v = new Vector3d(0, 1, 0);
		Quat4d p = new Quat4d(v.x, v.y, v.z, 0);
		
		Quat4d q = new Quat4d();
		q.set(new AxisAngle4d(new Vector3d(0,0,1), -Math.PI/4));
		
		Quat4d q_1 = new Quat4d(q);
		q_1.inverse();
		
		q.mul(p);
		q.mul(q_1);

	    System.out.println("[TestVecmath.testQuaternionRotation] q="+q);
		assertArrayEquals(new double[] { .7, .7, 0 }, toArray(q), epsilon);
	}
	
	@Test	
	public void testMulVecQuat() {
		Vector3d v1 = new Vector3d(0, 1, 0);
		
		Quat4d q = new Quat4d();
		q.set(new AxisAngle4d(new Vector3d(0,0,1), -Math.PI/4));
		
		Vector3d result = mulVecQuat(v1, q);
		assertArrayEquals(new double[] { .7, .7, 0 }, toArray(result), epsilon);
	}
	
	@Test
	public void testMatrixRotation() {
		Vector3d v = new Vector3d(0, 1, 0);
		Matrix4d m = new Matrix4d();
		m.set(new AxisAngle4d(new Vector3d(0,0,1), -Math.PI/4));
		m.transform(v);
		System.out.println("[TestVecmath.testMatrixRotation] v="+v);
		assertArrayEquals(new double[] { .7, .7, 0 }, toArray(v), epsilon);
	}

	@Test
	public void testTranslation() {
		Vector3d v = new Vector3d(0, 1, 0);
		Vector3d vt = new Vector3d(3, 3, 3);		
		v.add(vt);
		System.out.println("[TestVecmath.testTranslation] v="+v);
		assertArrayEquals(new double[] { 3, 4, 3 }, toArray(v), epsilon);
	}

	private double[] toArray(Point3d p) {
		return new double[] {p.x, p.y, p.z};
	}

	private double[] toArray(Vector3d v) {
		return new double[] {v.x, v.y, v.z};
	}

	private double[] toArray(Quat4d q) {
		return new double[] {q.x, q.y, q.z};
	}
	
	//http://searchcode.com/codesearch/view/56085386
	public static final Vector3d mulVecQuat(Vector3d vec, Quat4d rot) {
		Quat4d q = new Quat4d(rot);
		Quat4d q_1 = new Quat4d(rot);
		q_1.inverse();
		q.mul(new Quat4d(vec.x, vec.y, vec.z, 0));
		q.mul(q_1);
		Vector3d vec2 = new Vector3d(q.x, q.y, q.z);
		vec2.scale(vec.length());
		return vec2;
	}

}
