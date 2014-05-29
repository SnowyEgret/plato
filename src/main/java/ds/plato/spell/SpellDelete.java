package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.SlotEntry;
import ds.plato.common.ITransformer;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellDelete extends AbstractTransformerSpell {

	private Block blockAir;

	public SpellDelete(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
		this.blockAir = blockAir;
	}

	@Override
	public void invoke(Pick[] picks, SlotEntry[] slotEntries) {
		assert picks.length == getNumPicks();
		transformSelections(new ITransformer() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selectionManager's selection list.
				return new Selection(s.x, s.y, s.z, blockAir, 0);
			}
		});
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellDelete [getNumPicks()=");
		builder.append(getNumPicks());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int getNumPicks() {
		return 1;
	}
	
	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_delete_name;
			description = Messages.spell_delete_description;
			picks = new PickDescriptor(Messages.spell_delete_pick);
		}
	}
}