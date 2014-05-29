package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import ds.plato.common.EnumShell;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellGrowAll extends AbstractSpellSelection {

	// TODO blockAir is not needed for selection spells. Maybe a static PlatoBlocks class which returns blockSelected,
	// blockPicked and BlockAir
	public SpellGrowAll(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(Pick[] picks, SlotEntry[] entries) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			shrinkSelections(EnumShell.ALL);
		} else {
			Block firstBlockSelected = selectionManager.getSelections().iterator().next().block;
			System.out.println("[GrowAllSpell.invoke] typeOfFirstBlockSelected=" + firstBlockSelected);
			growSelections(EnumShell.ALL, firstBlockSelected);
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
			name = "ALL";
			description = "grows selection in all directions";
			picks = new PickDescriptor("Pattern block");
			modifiers = new ModifierDescriptor(Pair.of("ctrl", "shrinks selection"), Pair.of("alt", "selects any block"));
		}
	}

}