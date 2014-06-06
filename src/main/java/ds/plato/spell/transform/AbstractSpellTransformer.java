package ds.plato.spell.transform;

import ds.plato.IWorld;
import ds.plato.common.ITransformer;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellTransformer extends Spell {
	

	public AbstractSpellTransformer(AbstractSpellDescriptor descriptor, IUndo undo, ISelect select, IPick pick) {
		super(descriptor, undo, select, pick);
	}

	protected void transformSelections(IWorld world, ITransformer transformer) {
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
