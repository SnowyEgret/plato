package ds.plato.spell.select;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import ds.plato.IWorld;
import ds.plato.common.EnumShell;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowAll extends AbstractSpellSelect {

	// TODO blockAir is not needed for selection spells. Maybe a static PlatoBlocks class which returns blockSelected,
	// blockPicked and BlockAir
	public SpellGrowAll(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		//TODO test for case no selections. Should pick block.
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			shrinkSelections(EnumShell.ALL, world);
		} else {
			//Is this really the first block? getSelections gets the values from a map.
			Block firstBlockSelected = selectionManager.getSelections().iterator().next().block;
			growSelections(EnumShell.ALL, world, firstBlockSelected);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellGrowAll [getNumPicks()=");
		builder.append(getNumPicks());
		builder.append("]");
		return builder.toString();
	}

	private static class Descriptor extends AbstractSpellDescriptor {

		public Descriptor() {
			name = Messages.spell_grow_all_name;
			description = Messages.spell_grow_all_description;
			picks = new PickDescriptor(Messages.spell_grow_all_picks);
			modifiers = new ModifierDescriptor(Messages.spell_grow_all_modifier_0, Messages.spell_grow_all_modifier_1);
		}
	}

}
