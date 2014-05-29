package ds.plato.spell;

import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.ITransformer;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractTransformerSpell extends Spell {
	

	public AbstractTransformerSpell(AbstractSpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	protected void transformSelections(ITransformer transformer) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			t.add(new SetBlock(world, selectionManager, transformer.transform(s)).set());
		}
		t.commit();
	}

}
