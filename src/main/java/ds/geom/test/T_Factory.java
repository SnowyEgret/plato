package ds.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

import org.junit.Test;

public class T_Factory {

	@Test
	public void d() {
		F.scale = 100;
		double sum = 0;
		int n = 1000;
		for (int i = 0; i < n; i++) {
			double d = F.d();
			System.out.println("[T_Factory.d] d=" + d);
			sum += d;
		}
		assertThat(sum/n, closeTo(0, F.scale/F.scale*2));
	}

}
