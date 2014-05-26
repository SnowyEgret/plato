package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import org.lwjgl.input.Keyboard;

import ds.plato.common.EnumShell;
import ds.plato.common.ISelect;
import ds.plato.common.Plato;
import ds.plato.common.Shell;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class GrowAllSpell extends AbstractSelectionSpell {

	//TODO remove descriptor from constructor and update other spells and class SpellLoader
	public GrowAllSpell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(Pick[] picks, SlotEntry[] entries) {
		growSelections(EnumShell.ALL, picks[0].block);
	}

	private static class Descriptor extends SpellDescriptor {
		public Descriptor() {
			name = "ALL";
			description = "grows selection in all directions";
			picks = new PickDescriptor("Pattern block");
			modifiers = new ModifierDescriptor("ctrl", "shrinks selection");
		}
	}

}
