package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellRotate90 extends AbstractSpellMatrix {

	public SpellRotate90(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {

		Pick[] picks = pickManager.getPicks();
		Point3d center;
		if (Keyboard.isKeyDown(Keyboard.KEY_LMETA)) {
			Point3i c = selectionManager.voxelSet().centroid();
			center = new Point3d(c.x, c.y, c.z);
		} else {
			center = picks[0].point3d();
		}

		// TODO switch to RotationMatrix constructor with 2 vectors
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

		boolean deleteOriginal = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteOriginal);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = I18n.format("item.spellRotate90.name");
		d.description = I18n.format("item.spellRotate90.description");
		d.picks = new PickDescriptor(I18n.format("item.spellRotate90.pick.0"));
		d.modifiers = new ModifierDescriptor(CTRL + I18n.format("modifier.deleteOriginal"), ALT
				+ I18n.format("item.spellRotate90.modifier.0"), X + I18n.format("item.spellRotate90.modifier.1"), Y
				+ I18n.format("item.spellRotate90.modifier.2"), Z + I18n.format("item.spellRotate90.modifier.3"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
