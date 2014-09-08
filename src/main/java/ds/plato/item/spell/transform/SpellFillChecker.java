package ds.plato.item.spell.transform;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.undo.IUndo;

public class SpellFillChecker extends AbstractSpellTransform {

	public SpellFillChecker(IUndo undo,ISelect select, IPick pick) {
		super(undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry...slotEntries) {
		transformSelections(world, new ITransform() {
			@Override
			public Selection transform(Selection s) {
				int index = 0;
				if (((s.x & 1) == 0 && (s.z & 1) == 0) || ((s.x & 1) == 1 && (s.z & 1) == 1)) {
					index = ((s.y & 1) == 0) ? 0 : 1;
				} else {
					index = ((s.y & 1) == 0) ? 1 : 0;
				}
				SlotEntry entry = slotEntries[index];
				s.block = entry.block;
				s.metadata = entry.metadata;
				return s;
			}
		});
	}
}
