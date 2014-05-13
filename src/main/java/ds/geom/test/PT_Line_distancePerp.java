package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Point3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ds.geom.InfiniteLine;
import ds.geom.InfinitePlane;

@RunWith(Parameterized.class)
public class PT_Line_distancePerp {

	InfiniteLine infiniteLine;
	Point3d point;
	static double perpDistanceToLine = 10;
	double epsilon = .00000001;

	public PT_Line_distancePerp(InfiniteLine infiniteLine, Point3d point) {
		this.infiniteLine = infiniteLine;
		this.point = point;
	}

	@Parameterized.Parameters
//	public static Collection randomParams() throws Exception {
//		return Arrays.asList(Factory.randomLinePointBesideLine(perpDistanceToLine));
//	}
	
	@Test
	public void test() {
		System.out.println("line=" + infiniteLine);		
		System.out.println("point=" + point);
		double d = infiniteLine.distancePerp(point);
		assertThat("Perpendicular distance equals", d, closeTo(perpDistanceToLine, epsilon));
	}
}
