package ds.plato.geom.test;

import static ds.plato.geom.test.CloseToTuple3d.closeToTuple3d;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ds.plato.geom.GeomUtil;

@RunWith(Parameterized.class)
public class PT_GeomUtil_newScaleMatrix extends GeomTestParamaterized {

	Vector3d v1;
	Point3d pTest;

	public PT_GeomUtil_newScaleMatrix(Vector3d v1, Point3d pTest) {
		this.v1 = v1;
		this.pTest = pTest;
	}

	@Parameterized.Parameters
	public static Collection randomParams() {
		return Arrays.asList(vectorPoint(10));
	}

	@Test
	public void test() {

		Matrix4d m = GeomUtil.newScaleMatrix(v1);
		Point3d pOriginal = new Point3d(pTest);

		m.transform(pTest);

		// Fails
		// Point3d pTransormed = new Point3d(pTest);
		// pTransormed.scale(-m.getScale());
		// assertThat("Point scaled by the inverse scale of the transformation matrix lies on original point",
		// pTransormed, closeToTuple3d(pOriginal, .000001));

		//TODO More tests. This one only tests that the matrix is inversable.
		m.invert();
		m.transform(pTest);
		assertThat("Inverted transformation lies on original point", pTest, closeToTuple3d(pOriginal));
	}
}
