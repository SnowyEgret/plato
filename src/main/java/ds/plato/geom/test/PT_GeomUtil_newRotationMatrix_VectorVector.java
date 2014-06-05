package ds.plato.geom.test;

import static ds.plato.geom.test.CloseToTuple3d.closeToTuple3d;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ds.plato.geom.GeomUtil;

@RunWith(Parameterized.class)
public class PT_GeomUtil_newRotationMatrix_VectorVector extends GeomTestParamaterized {

	Vector3d v1, v2;

	public PT_GeomUtil_newRotationMatrix_VectorVector(Vector3d v1, Vector3d v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	@Parameterized.Parameters
	public static Collection params() {
		return Arrays.asList(vectors(10, 2));
	}

	@Test
	public void test() {
		Matrix4d m = GeomUtil.newRotationMatrix(v1, v2);
		m.transform(v1);
		// Only interested in direction
		v1.normalize();
		v2.normalize();
		assertThat("Direction of vectors should be equal after rotation", v1, closeToTuple3d(v2));
	}

}
