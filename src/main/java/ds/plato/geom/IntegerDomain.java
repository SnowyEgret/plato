package ds.plato.geom;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import org.apache.commons.lang3.Range;

public class IntegerDomain {

	public Range<Integer> rx, ry, rz;
	public int dx, dy, dz;
	Point3i min, max;
	public int count;

	public IntegerDomain(Point3i min, Point3i max) {
		this.min = min;
		this.max = max;
		rx = Range.between(min.x, max.x);
		ry = Range.between(min.y, max.y);
		rz = Range.between(min.z, max.z);
		dx = Math.abs(rx.getMaximum() - rx.getMinimum());
		dy = Math.abs(ry.getMaximum() - ry.getMinimum());
		dz = Math.abs(rz.getMaximum() - rz.getMinimum());
	}

	public IntegerDomain(Range<Integer> rx, Range<Integer> ry, Range<Integer> rz) {
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
	}

	public Iterable<IntegerDomain> divide(int d) {
		List<Range<Integer>> subRangesX = divideRange(rx, d);
		List<Range<Integer>> subRangesY = divideRange(ry, d);
		List<Range<Integer>> subRangesZ = divideRange(rz, d);
		List<IntegerDomain> subDomains = new ArrayList();
		for (int x = 0; x < d; x++) {
			for (int y = 0; y < d; y++) {
				for (int z = 0; z < d; z++) {
					subDomains.add(new IntegerDomain(subRangesX.get(x), subRangesY.get(y), subRangesZ.get(z)));
				}
			}
		}
		return subDomains;
	}

	@Override
	public String toString() {
		return "IntegerDomain [rx=" + rx + ", ry=" + ry + ", rz=" + rz + ", dx=" + dx + ", dy=" + dy + ", dz=" + dz
				+ ", min=" + min + ", max=" + max + "]";
	}

	public static List<Range<Integer>> divideRange(Range<Integer> r, int numDivisions) {
		int size = r.getMaximum() - r.getMinimum();
		int min = r.getMinimum();
		int segment = size / numDivisions;
		List<Range<Integer>> subRanges = new ArrayList();
		for (int i = 0; i < numDivisions; i++) {
			subRanges.add(Range.between(min, min + segment));
			min += segment + 1;
		}
		return subRanges;
	}

	public boolean contains(Point3i p) {
		if (!rx.contains(p.x))
			return false;
		if (!ry.contains(p.y))
			return false;
		if (!rz.contains(p.z))
			return false;
		return true;
	}
}