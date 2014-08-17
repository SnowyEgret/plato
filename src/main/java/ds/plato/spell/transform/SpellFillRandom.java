package ds.plato.spell.transform;

import ds.plato.core.IWorld;
import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.undo.IUndo;

public class SpellFillRandom extends AbstractSpellTransform {

	public SpellFillRandom(IUndo undo, ISelect select, IPick pick) {
		super(undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry...slotEntries) {
		transformSelections(world, new ITransform() {
			@Override
			public Selection transform(Selection s) {
				SlotDistribution d = new SlotDistribution(slotEntries);
				SlotEntry entry = d.randomEntry();
				s.block = entry.block;
				s.metadata = entry.metadata;
				return s;
			}
		});
	}
}
