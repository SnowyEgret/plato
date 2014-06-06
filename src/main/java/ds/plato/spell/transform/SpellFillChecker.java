package ds.plato.spell.transform;

import net.minecraft.block.BlockAir;
import ds.plato.IWorld;
import ds.plato.common.ITransformer;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellFillChecker extends AbstractSpellTransformer {

	public SpellFillChecker(IUndo undo,ISelect select, IPick pick, BlockAir b) {
		super(new Descriptor(), undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		transformSelections(world, new ITransformer() {
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

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_fill_checker_name;
			description = Messages.spell_fill_checker_description;
			picks = new PickDescriptor(Messages.spell_pick);
		}
	}
}
