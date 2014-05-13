package ds.geom.test;

import static ds.geom.test.CloseToTuple3d.closeToTuple3d;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ds.geom.GeomUtil;

@RunWith(Parameterized.class)
public class PT_GeomUtil_newTranslationMatrix {

	Point3d p1, p2, pTest;

	public PT_GeomUtil_newTranslationMatrix(Point3d p1, Point3d p2, Point3d pTest) {
		this.p1 = p1;
		this.p2 = p2;
		this.pTest = pTest;
	}

	@Parameterized.Parameters
	public static Collection params() {
		return Arrays.asList(F.points(3));
	}

	//@Parameterized.Parameters
	public static Collection points() {
		Object[][] params = new Object[][] {
				{new Point3d(1, 1, 1), new Point3d(2, 2, 2), new Point3d(0, 0, 0) },
		};
		return Arrays.asList(params);
	}

	@Test
	public void test() {
		System.out.println("p1=" + p1);
		System.out.println("p2=" + p2);
		System.out.println("pTest=" + pTest);
		Matrix4d m = GeomUtil.newTranslationMatrix(p1, p2);
		Point3d pExpected = new Point3d(pTest);
		m.transform(pTest);
		m.invert();
		m.transform(pTest);
		assertThat(pTest, closeToTuple3d(pExpected));
	}
}
