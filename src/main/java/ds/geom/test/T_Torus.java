package ds.geom.test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.geom.PointSet;
import ds.geom.Torus;

public class T_Torus {

	@Test
	public void test() {
		Torus t = new Torus(F.p(), new Point3d(100, 0, 0), new Point3d(50, 0, 0));
		PointSet points = t.pointSet();
		new Viewer(points);
		for (Point3d p : points) {
			assertThat(t.contains(p), equalTo(true));
		}
	}

}
