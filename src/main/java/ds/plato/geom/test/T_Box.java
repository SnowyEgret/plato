package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.plato.geom.PointSet;
import ds.plato.geom.Primitive;
import ds.plato.geom.solid.Box;

public class T_Box extends GeomTest {

	@Test
	public void test() {
		Primitive p = new Box(p(), p());
		System.out.println("[T_Box.test] p=" + p);
		PointSet points = p.pointSet();
		System.out.println("[T_Box.test] points.size()=" + points.size());
		//new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}
}
