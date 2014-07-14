package ds.plato.spell.matrix;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellMatrix extends Spell {

	public AbstractSpellMatrix(int numPicks, IUndo undo, ISelect select, IPick pick) {
		super(numPicks, undo, select, pick);
	}

	protected void transformSelections(Matrix4d matrix, IWorld world, boolean deleteInitialBlocks) {
		List<Point3d> newBlockLocations = new ArrayList<>();
		Transaction t = undoManager.newTransaction();
		Iterable<Selection> selections = selectionManager.getSelections();
		for (Selection s : selections) {
			Point3d p = s.point3d();
			matrix.transform(p);
			if (deleteInitialBlocks) {
				t.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, Blocks.air, 0).set());
			}
			newBlockLocations.add(p);
			t.add(new SetBlock(world, selectionManager, (int) p.x, (int) p.y, (int) p.z, s.block, s.metadata).set());
		}
		System.out.println("[AbstractSpellMatrixTransformation.transformSelections] t=" + t);
		t.commit();
		
		// TODO Optimize this. Expensive for large operations.
		// Test that we are not transforming onto oneself
		// Must be done before adding to transaction
		for (Selection s : selections) {
			if (!newBlockLocations.contains(s.point3d())) {
				System.out.println("[AbstractSpellMatrix.transformSelections] s=" + s);
				selectionManager.deselect(s);
			}
		}
	}

}
