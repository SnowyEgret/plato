package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.plato.geom.PointSet;
import ds.plato.geom.Primitive;
import ds.plato.geom.surface.Sphere;

public class T_Sphere extends GeomTest {

	@Test
	public void test() {
		Primitive p = new Sphere(o(), p(), false);
		PointSet points = p.pointSet();
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}
}
