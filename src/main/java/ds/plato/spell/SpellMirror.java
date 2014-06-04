package ds.plato.spell;


import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.BlockAir;

import org.lwjgl.input.Keyboard;

import ds.geom.GeomUtil;
import ds.geom.ReflectionMatrix;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellMirror extends AbstractSpellMatrixTransformation {

	public SpellMirror(
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager,
			BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager, blockAir);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Point3i c = selectionManager.voxelSet().centroid();
		//Matrix4d matrix = GeomUtil.newReflectionMatrix(new Point3d(c.x, c.y, c.z), picks[0].toDouble());
		Matrix4d matrix = new ReflectionMatrix(picks[0].toDouble(), picks[1].toDouble(), picks[2].toDouble());
		boolean deleteInitialBlocks = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteInitialBlocks);
}

	@Override
	public int getNumPicks() {
		return 3;
	}


	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_mirror_name;
			description = Messages.spell_mirror_description;
			picks = new PickDescriptor(Messages.spell_mirror_picks);
			modifiers = new ModifierDescriptor(Messages.spell_modifier_deleteOriginal);
		}
	}
}
