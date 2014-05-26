package ds.plato.spell;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractMatrixTransformationSpell extends Spell {

	private Block blockAir;

	public AbstractMatrixTransformationSpell(AbstractSpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager, Block blockAir) {
		super(descriptor, undoManager, selectionManager, pickManager);
		this.blockAir = blockAir;
	}
	
	protected void transformSelections(Matrix4d matrix, boolean deleteInitialBlocks) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Point3d p = s.getPoint3d();
			matrix.transform(p);
			if (deleteInitialBlocks) { 
				t.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, blockAir, 0).set());
				selectionManager.removeSelection(s);
			}
			t.add(new SetBlock(world, selectionManager, (int) p.x, (int) p.y, (int) p.z, s.block, s.metadata).set());		
		}
		t.commit();
	}

}
