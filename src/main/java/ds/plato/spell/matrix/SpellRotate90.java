package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.BlockAir;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellRotate90 extends AbstractSpellMatrix {

	public SpellRotate90(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		
		Pick[] picks = pickManager.getPicks();
		Point3d center;
		if (Keyboard.isKeyDown(Keyboard.KEY_LMETA)) {
			Point3i c = selectionManager.voxelSet().centroid();
			center = new Point3d(c.x, c.y, c.z);
		} else {
			center = picks[0].point3d();
		}
		
		//TODO switch to RotationMatrix constructor with 2 vectors
		Matrix4d matrix;
		switch (Keyboard.getEventKey()) {
		case (Keyboard.KEY_X):
			matrix = GeomUtil.newRotX90Matrix(center);
		case (Keyboard.KEY_Y):
			matrix = GeomUtil.newRotY90Matrix(center);
		case (Keyboard.KEY_Z):
			matrix = GeomUtil.newRotZ90Matrix(center);
		default:
			matrix = GeomUtil.newRotY90Matrix(center);
		}
		
		boolean deleteInitialBlocks = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteInitialBlocks);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_rotate_90_name;
		d.description = Messages.spell_rotate_90_description;
		d.picks = new PickDescriptor(Messages.spell_rotate_90_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_modifier_deleteOriginal,
				Messages.spell_rotate_90_modifier_0, Messages.spell_rotate_90_modifier_1,
				Messages.spell_rotate_90_modifier_2, Messages.spell_rotate_90_modifier_3);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
