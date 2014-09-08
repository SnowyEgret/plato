package ds.plato.item.spell.transform;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.select.Selection;

public class SpellFill extends AbstractSpellTransform {

	public SpellFill(IUndo undo, ISelect select, IPick pick) {
		super(undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry...slotEntries) {
		transformSelections(world, new ITransform() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selection list.
				// Use first (left-most) block in inventory
				SlotEntry e = slotEntries[0];
				return new Selection(s.x, s.y, s.z, e.block, e.metadata);
			}
		});
	}
}
