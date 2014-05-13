package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.geom.PointSet;
import ds.geom.Primitive;

public class T_Ball {

	@Test
	public void test() {
		F.scale = 100;
		Primitive p = F.ball(F.p());
		System.out.println("[T_Ball.test] p=" + p);
		PointSet points = p.pointSet();
		System.out.println("[T_Ball.test] points.size=" + points.size());
		new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}
}
