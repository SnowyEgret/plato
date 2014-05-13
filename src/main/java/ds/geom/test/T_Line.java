package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.geom.Line;
import ds.geom.PointSet;
import ds.geom.Primitive;

public class T_Line {
	
	@Test
	public void test() {
		Primitive line = new Line(F.p(), F.p());
		PointSet points = line.pointSet();
		System.out.println("[T_Line.test] points.size=" + points.size());
		for (Point3d pt : points) {
			assertThat(line.contains(pt), equalTo(true));
		}
	}
}
