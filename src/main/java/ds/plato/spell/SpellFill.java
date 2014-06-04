package ds.plato.spell;

import net.minecraft.block.BlockAir;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.ITransformer;
import ds.plato.common.Selection;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public class SpellFill extends AbstractSpellTransformer {

	public SpellFill(IUndo undo, ISelect select, IPick pick, BlockAir blockAir) {
		super(new Descriptor(), undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		transformSelections(world, new ITransformer() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selection list.
				// Use first (left-most) block in inventory
				SlotEntry e = slotEntries[0];
				return new Selection(s.x, s.y, s.z, e.block, e.metadata);
			}
		});
	}

	@Override
	public int getNumPicks() {
		return 1;
	}

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_fill_name;
			description = Messages.spell_fill_description;
			picks = new PickDescriptor(Messages.spell_pick);
		}
	}
}
