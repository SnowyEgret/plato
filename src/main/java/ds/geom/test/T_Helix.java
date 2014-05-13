package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.geom.Helix;
import ds.geom.PointSet;
import ds.geom.Primitive;

public class T_Helix {

	@Test
	public void test() {
		Primitive p = new Helix(F.o(), new Point3d(0, 200, 0), new Point3d(0, 0, 200), 5);
		System.out.println("[T_Helix.test] p=" + p);
		PointSet points = p.pointSet();
		System.out.println("[T_Helix.test] points.size()=" + points.size());
		new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}

}
