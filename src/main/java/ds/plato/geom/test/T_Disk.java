package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;

import org.junit.Test;

import ds.plato.geom.PointSet;
import ds.plato.geom.Primitive;
import ds.plato.geom.surface.DiskXZ;

public class T_Disk extends GeomTest {

	@Test
	public void constructor() {
		Point3d p0 = p();
		Point3d pEdge = p();
		Primitive p = new DiskXZ(p0, pEdge);
		PointSet points = p.pointSet();
		//new Viewer(points);
		for (Point3d pt : points) {
			assertThat(p.contains(pt), equalTo(true));
		}
	}

}
