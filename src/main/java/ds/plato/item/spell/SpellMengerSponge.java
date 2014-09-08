package ds.plato.item.spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IntegerDomain;
import ds.plato.geom.VoxelSet;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellMengerSponge extends Spell {

	int level = 0;
	List<Point3i> pointsToDelete = new ArrayList<>();

	public SpellMengerSponge(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		//TODO use enclosing cube
		recursivelySubtract(selectionManager.voxelSet());
		System.out.println("[SpellMengerSponge.invoke] pointsToDelete=" + pointsToDelete);
		selectionManager.clearSelections();
		pickManager.clearPicks();
		Transaction t = undoManager.newTransaction();
		for (Point3i v : pointsToDelete) {
			t.add(new SetBlock(world, selectionManager, v.x, v.y, v.z, Blocks.air, 0).set());
		}
		t.commit();
	}

	private void recursivelySubtract(VoxelSet voxels) {
		
		// Run through this set testing for containment in each sub domain. Depending on which domain it is contained
		// by, add it to the set of points to be set to air. Recurse on each sub voxel set.
		level++;
		System.out.println("[SpellMengerSponge.recursivelySubtract] level=" + level);
		Iterable<IntegerDomain> domains = voxels.divideDomain(3);
		System.out.println("[SpellMengerSponge.recursivelySubtract] domains=" + domains);
		List<Integer> domainsToDelete = Arrays.asList(4, 10, 12, 13, 14, 16, 22);
		for (Point3i p : voxels) {
			IntegerDomain domain = null;
			int i = 0;
			for (IntegerDomain d : domains) {
				if (d.contains(p)) {
					domain = d;
					domain.count = i;
					break;
				}
				i++;
			}
			if (domainsToDelete.contains(domain.count)) {
				pointsToDelete.add(p);
			}
		}
		
		for (VoxelSet set : voxels.divide(3)) {
			if (set.size() >= 9) {
				recursivelySubtract(set);
			}
		}
		
		level--;
		System.out.println("[SpellMengerSponge.recursivelySubtract] level=" + level);
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
