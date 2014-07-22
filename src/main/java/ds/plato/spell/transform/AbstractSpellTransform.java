package ds.plato.spell.transform;

import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellTransform extends Spell {

	public AbstractSpellTransform(IUndo undo, ISelect select, IPick pick) {
		super(1, undo, select, pick);
	}

	protected void transformSelections(IWorld world, ITransform transformer) {
		if (selectionManager.getSelectionList().size() != 0) {
			Transaction t = undoManager.newTransaction();
			for (Selection s : selectionManager.getSelections()) {
				t.add(new SetBlock(world, selectionManager, transformer.transform(s)).set());
			}
			t.commit();
			selectionManager.clearSelections();
		}
		pickManager.clearPicks();
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
