package ds.plato.spell.transform;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellDelete extends AbstractSpellTransform {

	private Block blockAir;

	public SpellDelete(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
		this.blockAir = blockAir;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		transformSelections(world, new ITransform() {
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
			picks = new PickDescriptor(Messages.spell_pick);
		}
	}

}
