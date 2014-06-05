package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import ds.plato.geom.Cube;

public class T_Cube extends GeomTest {

	@Test
	public void constructor() {
		scale = 30;
		for (int i = 0; i < 100; i++) {
			double a = d();
			double b = d();
			Cube c = new Cube(p(a), p(b));
			//System.out.println("[T_Cube.constructor] c=" + c);
			assertThat(c.voxelize().size(), equalTo((int)Math.round(Math.pow(Math.abs(b-a)+1, 3))));
		}
	}
	
	@Test
	public void constructor_equalDimensions() {
		Cube c = new Cube(p(0), p(1, 1, 2));
		System.out.println("[T_Cube.constructor_equalDimensions] c=" + c);
		assertThat(c.voxelize().size(), equalTo(27));
	}
	
	@Test
	public void constructor_negatives() {
		Cube c = new Cube(p(0), p(1, -1, 2));
		System.out.println("[T_Cube.constructor_negatives] c=" + c);
		assertThat(c.voxelize().size(), equalTo(27));
	}
	
//	@Test
//	public void constructor_odd() {
//		Cube c = new Cube(F.p(), F.p(), true);
//		int dimension = c.dimension();
//		System.out.println("[T_Cube.constructor_odd] dimension=" + dimension);
//		assertThat("Range is odd", (dimension & 1) == 1, equalTo(true));
//	}

//	@Test
//	public void constructor_divisor() {
//		for (int ordinal = 2; ordinal < 10; ordinal++) {
//			Cube c = new Cube(F.p(), F.p(), ordinal);
//			System.out.println("[T_Cube.constructor_divisor] c=" + c);
//			assertThat("Range is divisible by " + ordinal, (c.rangeT().getMaximum() - c.rangeT().getMinimum()) % ordinal,
//					closeTo(0, .000001));
//		}
//	}

}
