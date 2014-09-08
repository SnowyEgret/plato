package ds.plato.item.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.item.spell.descriptor.Modifier;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellRotate90 extends AbstractSpellMatrix {

	public SpellRotate90(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.ALT, Modifier.X, Modifier.Y, Modifier.Z);
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
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
