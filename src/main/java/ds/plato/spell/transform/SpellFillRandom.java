package ds.plato.spell.transform;

import java.util.Random;

import net.minecraft.block.BlockAir;
import ds.plato.core.IWorld;
import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
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

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_fill_random_name;
		d.description = Messages.spell_fill_random_description;
		d.picks = new PickDescriptor(Messages.spell_pick_anywhere);
		return d;
	}
}
