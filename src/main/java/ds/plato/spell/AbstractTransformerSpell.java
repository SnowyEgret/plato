package ds.plato.spell;

import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.Transformer;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractTransformerSpell extends AbstractSpell {
	

	public AbstractTransformerSpell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	protected void transformSelections(Transformer transformer) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			t.add(new SetBlock(world, selectionManager, transformer.transform(s)).set());
		}
		t.commit();
	}

}
