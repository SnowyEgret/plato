package ds.plato.geom;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

public class MengerSponge extends Cube {

	int level = 0;
	VoxelSet finalSet;

	public MengerSponge(Point3d origin, Point3d diagonalCorner) {
		super(origin, diagonalCorner);
	}

	@Override
	public VoxelSet voxelize() {
		finalSet = super.voxelize();
		transform(super.voxelize());
		return finalSet;
	}

	private void transform(VoxelSet voxels) {
		// Run through this set testing for containment in each sub domain. Depending on which domain it is contained
		// by, either add it to the new set or not. Recurse on each sub voxel set.
		level++;
		System.out.println("[MengerSponge.transform] level=" + level);
		Iterable<IntegerDomain> domains = voxels.divideDomain(3);
		System.out.println("[MengerSponge.transform] domains=" + domains);
		List<Integer> domainsToDelete = Arrays.asList(4, 10, 12, 13, 14, 16, 22);
		for (Point3i v : voxels) {
			IntegerDomain domain = null;
			int i = 0;
			for (IntegerDomain d : domains) {
				if (d.contains(v)) {
					domain = d;
					domain.count = i;
					break;
				}
				i++;
			}
			if (domainsToDelete.contains(domain.count)) {
				finalSet.remove(v);
			}
		}
		
		Iterable<VoxelSet> voxelSets = voxels.divide(3);
		for (VoxelSet voxelSet : voxelSets) {
			if (voxelSet.size() >= 9) {
				transform(voxelSet);
			}
		}
		
		level--;
		System.out.println("[MengerSponge.transform] level=" + level);
		//return finalSet;

	}

	@Override
	public String toString() {
		return "MengerSponge [rT=" + rT + ", rU=" + rU + ", rV=" + rV + ", p0=" + p0 + "]";
	}
}
