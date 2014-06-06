package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.BlockAir;

import org.lwjgl.input.Keyboard;

import ds.plato.IWorld;
import ds.plato.common.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellRotate90 extends AbstractSpellMatrix {

	public SpellRotate90(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir blockAir) {
		super(new Descriptor(), undoManager, selectionManager, pickManager, blockAir);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Matrix4d matrix;
		Point3d p;
		if (Keyboard.isKeyDown(Keyboard.KEY_LMETA)) {
			Point3i c = selectionManager.voxelSet().centroid();
			p = new Point3d(c.x, c.y, c.z);
		} else {
			p = picks[0].toDouble();
		}
		switch (Keyboard.getEventKey()) {
		case (Keyboard.KEY_X):
			matrix = GeomUtil.newRotX90Matrix(p);
		case (Keyboard.KEY_Y):
			matrix = GeomUtil.newRotY90Matrix(p);
		case (Keyboard.KEY_Z):
			matrix = GeomUtil.newRotZ90Matrix(p);
		default:
			matrix = GeomUtil.newRotY90Matrix(p);
		}
		boolean deleteInitialBlocks = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteInitialBlocks);
	}

	@Override
	public int getNumPicks() {
		return 1;
	}

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_rotate_90_name;
			description = Messages.spell_rotate_90_description;
			picks = new PickDescriptor(Messages.spell_rotate_90_picks);
			modifiers = new ModifierDescriptor(Messages.spell_modifier_deleteOriginal, Messages.spell_rotate_90_modifier_0,
					Messages.spell_rotate_90_modifier_1, Messages.spell_rotate_90_modifier_2,
					Messages.spell_rotate_90_modifier_3);
		}
	}

}
