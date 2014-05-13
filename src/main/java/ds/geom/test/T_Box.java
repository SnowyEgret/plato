package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.geom.Box;
import ds.geom.PointSet;
import ds.geom.Primitive;

public class T_Box {

	@Test
	public void test() {
		//Primitive p = new Box(Factory.newOrigin(), Factory.randomPoint());
		Primitive p = new Box(F.p(), F.p());
		System.out.println("[T_Box.test] p=" + p);
		PointSet points = p.pointSet();
		System.out.println("[T_Box.test] points.size()=" + points.size());
		new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}
}
