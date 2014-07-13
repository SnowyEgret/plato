package ds.plato.geom.curve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.plato.geom.PointSet;
import ds.plato.geom.Primitive;
import ds.plato.geom.test.GeomTest;

public class LineTest extends GeomTest {
	
	@Test
	public void test() {
		Primitive line = new Line(p(), p());
		PointSet points = line.pointSet();
		System.out.println("[T_Line.test] points.size=" + points.size());
		for (Point3d pt : points) {
			assertThat(line.contains(pt), equalTo(true));
		}
	}
}
