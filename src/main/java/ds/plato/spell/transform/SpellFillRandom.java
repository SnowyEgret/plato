package ds.plato.spell.transform;

import java.util.Random;

import net.minecraft.block.BlockAir;
import ds.plato.IWorld;
import ds.plato.common.ITransformer;
import ds.plato.common.SlotDistribution;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellFillRandom extends AbstractSpellTransformer {

	public SpellFillRandom(IUndo undo, ISelect select, IPick pick, BlockAir b) {
		super(new Descriptor(), undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		transformSelections(world, new ITransformer() {
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

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_fill_random_name;
			description = Messages.spell_fill_random_description;
			picks = new PickDescriptor(Messages.spell_pick);
		}
	}
}
