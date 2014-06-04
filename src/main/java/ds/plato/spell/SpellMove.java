package ds.plato.spell;

import javax.vecmath.Matrix4d;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import ds.geom.GeomUtil;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellMove extends AbstractSpellMatrixTransformation {

	public SpellMove(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager, blockAir);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Matrix4d matrix = GeomUtil.newTranslationMatrix(picks[0].toDouble(), picks[1].toDouble());
		transformSelections(matrix, world, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
	}

	@Override
	public int getNumPicks() {
		return 2;
	}

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = "MOVE";
			description = "Moves selections";
			picks = new PickDescriptor("From", "To");
			modifiers = new ModifierDescriptor(Pair.of("ctrl", "Deletes originial"));
		}
	}
}
