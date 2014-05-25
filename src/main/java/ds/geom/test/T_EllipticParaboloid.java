package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import ds.geom.PointSet;
import ds.geom.Primitive;

public class T_EllipticParaboloid extends GeomTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Primitive p = elipticParaboloid(p());
		PointSet points = p.pointSet();
		//new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}

}
