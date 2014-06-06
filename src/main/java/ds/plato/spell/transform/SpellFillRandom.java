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

	public SpellFillRandom(IUndo undo, ISelect select, IPick pick, BlockAir b) {
		super(undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		transformSelections(world, new ITransform() {
			@Override
			public Selection transform(Selection s) {
				// TODO remove static reference. Maybe SlotDistribution is passed to invoke, or a SlotDistribution
				// is constructed from slot entries
				//SlotDistribution d = Plato.slotDistribution;
				SlotDistribution d = new SlotDistribution(slotEntries);
				System.out.println("[SpellFillRandom.invoke(...).new ITransformer() {...}.transform] d=" + d);
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
		d.picks = new PickDescriptor(Messages.spell_pick);
		return d;
	}
}
