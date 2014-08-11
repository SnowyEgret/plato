package ds.plato.spell.transform;

import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellDelete extends AbstractSpellTransform {


	public SpellDelete(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		transformSelections(world, new ITransform() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selectionManager's selection list.
				return new Selection(s.x, s.y, s.z, Blocks.air, 0);
			}
		});
	}

	@Override
	public int getNumPicks() {
		return 1;
	}
	
	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_delete_name;
		d.description = Messages.spell_delete_description;
		d.picks = new PickDescriptor(Messages.spell_restore_pick);
		return d;
	}

}
