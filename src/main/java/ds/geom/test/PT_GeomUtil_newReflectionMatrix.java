package ds.geom.test;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Point3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PT_GeomUtil_newReflectionMatrix {

	Point3d p1, p2, p3, p4;
	double epsilon = .000001;

	public PT_GeomUtil_newReflectionMatrix(Point3d p1, Point3d p2, Point3d p3, Point3d p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}

	@Parameterized.Parameters
	public static Collection randomPoints() {
		return Arrays.asList(F.points(4));
	}

	@Test
	public void test() {

		//TODO replace with ds.vecmath.geom.Plane
//		Point3D _p1 = new Point3D(p1.x, p1.y, p1.z);
//		Point3D _p2 = new Point3D(p2.x, p2.y, p2.z);
//		Point3D _p3 = new Point3D(p3.x, p3.y, p3.z);
//		Point3D _p4 = new Point3D(p4.x, p4.y, p4.z);
//
//		Vector3D vp1 = new Vector3D(_p1, _p2);
//		Vector3D vp2 = new Vector3D(_p1, _p3);
//
//		Plane3D plane = new Plane3D(_p1, vp1, vp2);
//		double d = plane.distance(_p4);
//
//		Vector3D v = new Vector3D(_p4, plane.projectPoint(_p4));
//		assertThat("Vector to projection point should be orthogonal to vector in plane.",
//				Vector3D.isOrthogonal(v, vp1), equalTo(true));
//
//		Matrix4d m = GeomUtil.newReflectionMatrix(p1, p2, p3);
//		m.transform(p4);
//
//		_p4 = new Point3D(p4.x, p4.y, p4.z);
//
//		assertThat("Distance to plane should be same in reflected point", d, closeTo(plane.distance(_p4), epsilon));
//		assertThat("Vector to projection point should be opposite to vector to projection point from reflected point",
//				v.opposite(), equalTo(new Vector3D(_p4, plane.projectPoint(_p4))));
		
//		m.invert();
//		m.transform(p4);
//		
//		Point3D _p4inv = new Point3D(p4.x, p4.y, p4.z);		
//		assertThat("Inverted transformation lies on same point", _p4inv, equalTo(_p4));		
	}
}

// //@Parameterized.Parameters
// public static Collection points() {
// Object[][] params = new Object[][] {
// {new Point3d(0, 2, 0), new Point3d(1, 2, 0), new Point3d(0, 2, 1), new Point3d(0, 4, 0)},
// {new Point3d(0, 3, 0), new Point3d(1, 3, 0), new Point3d(0, 3, 1), new Point3d(0, 4, 0)},
// {new Point3d(0, 3, 0), new Point3d(1, 3, 0), new Point3d(0, 3, 9), new Point3d(0, 4, 0)},
// {new Point3d(0, 3, 0), new Point3d(1, 3, 0), new Point3d(0, 3, 9), new Point3d(0, -4, 0)},
// {new Point3d(0, 3, 0), new Point3d(1, 3, 0), new Point3d(0, 2, 9), new Point3d(0, 4, 0)},
// };
// return Arrays.asList(params);
// }

