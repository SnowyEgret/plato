package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.commons.lang3.Range;
import org.junit.Test;

import ds.plato.geom.VoxelSet;
import ds.plato.geom.solid.MengerSponge;

public class T_MengerSponge extends GeomTest {

	@Test
	public void test() {
		MengerSponge s = null;
		for (int i = 0; i < 1; i++) {
			// s = new MengerSponge(F.origin(), F.point());
			s = new MengerSponge(p(1), p(27));
			System.out.println("[T_MengerSponge.test] s=" + s);
			assertThat(isDivisibleBy(s.rangeT(), 3), equalTo(true));
			VoxelSet voxels = s.voxelize();
			assertThat(voxels.size(), equalTo(27 * 27 * 27 - 7 * 9 * 9 * 9));
			// System.out.println("[T_MengerSponge.test] voxels=" + voxels);
		}
	}

	private boolean isDivisibleBy(Range<Double> r, int i) {
		double d = r.getMaximum() - r.getMinimum() + 1;
		return d % 3 == 0;
	}

}
