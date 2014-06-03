package ds.plato.spell;

import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.ITransformer;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellTransformer extends Spell {
	

	public AbstractSpellTransformer(AbstractSpellDescriptor descriptor, IUndo undo, ISelect select, IPick pick) {
		super(descriptor, undo, select, pick);
	}

	protected void transformSelections(ITransformer transformer) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			t.add(new SetBlock(world, selectionManager, transformer.transform(s)).set());
		}
		t.commit();
	}

	@Override
	public int getNumPicks() {
		return 1;
	}
}
