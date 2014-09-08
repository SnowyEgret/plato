package ds.plato.item.spell.transform;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.select.Selection;

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
