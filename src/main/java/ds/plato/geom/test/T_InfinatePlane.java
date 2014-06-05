package ds.plato.geom.test;

import static ds.plato.geom.test.CloseToTuple3d.closeToTuple3d;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.junit.Test;

import ds.plato.geom.surface.InfinitePlane;

public class T_InfinatePlane extends GeomTest {

	@Test
	public void XY() {
		InfinitePlane p = InfinitePlane.XY();
		assertThat(p.normal(), closeToTuple3d(new Vector3d(0, 0, 1)));
	}

	@Test
	public void XZ() {
		InfinitePlane p = InfinitePlane.XZ();
		assertThat(p.normal(), closeToTuple3d(new Vector3d(0, 1, 0)));
	}

	@Test
	public void YZ() {
		InfinitePlane p = InfinitePlane.YZ();
		assertThat(p.normal(), closeToTuple3d(new Vector3d(1, 0, 0)));
	}
	
	@Test
	public void pointAtParameters() {
		InfinitePlane infinitePlane = plane();
		Point3d p = infinitePlane.pointAtParameters(d(), d());
		assertThat("Point from plane equation is contained by plane", infinitePlane.contains(p), is(true));
	}
	

}
