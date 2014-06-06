package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellMatrix extends Spell {

	private Block blockAir;

	public AbstractSpellMatrix(IUndo undo, ISelect select, IPick pick, Block blockAir) {
		super(undo, select, pick);
		this.blockAir = blockAir;
	}

	protected void transformSelections(Matrix4d matrix, IWorld world, boolean deleteInitialBlocks) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Point3d p = s.getPoint3d();
			matrix.transform(p);
			// TODO Optimize this. Expensive for large operations.
			// Test that we are not transforming onto oneself
			// Must be done before setting the block
			if (!t.contains(s)) {
				selectionManager.deselect(s);
			}
			if (deleteInitialBlocks) {
				System.out.println("[AbstractSpellMatrixTransformation.transformSelections] deleteInitialBlocks="
						+ deleteInitialBlocks);
				t.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, blockAir, 0).set());
			}
			t.add(new SetBlock(world, selectionManager, (int) p.x, (int) p.y, (int) p.z, s.block, s.metadata).set());
		}
		System.out.println("[AbstractSpellMatrixTransformation.transformSelections] t=" + t);
		t.commit();
	}

}
