package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;

import javax.vecmath.Point3d;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ds.geom.InfiniteLine;

@RunWith(Parameterized.class)
public class PT_Line_containsPoint { 

	InfiniteLine infiniteLine;
	Point3d pOnLine;
	Point3d pNotOnLine;

	public PT_Line_containsPoint(InfiniteLine infiniteLine, Point3d pOnLine, Point3d pNotOnLine) {
		this.infiniteLine = infiniteLine;
		this.pOnLine = pOnLine;
		this.pNotOnLine = pNotOnLine;
	}

	@Parameterized.Parameters
	public static Collection randomParams() throws Exception {
		return Arrays.asList(F.line_PointOnLine_PointNotOnLine());
	}

	@Test
	public void test() {
		assertThat("Point on line is contained by line", infiniteLine.contains(pOnLine), equalTo(true));
		assertThat("Point not on line is not contained by line", infiniteLine.contains(pNotOnLine), equalTo(false));
	}
}
