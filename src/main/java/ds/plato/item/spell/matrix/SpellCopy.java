package ds.plato.item.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.lwjgl.input.Keyboard;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.matrix.TranslationMatrix;
import ds.plato.item.spell.Modifier;
import ds.plato.pick.Pick;

public class SpellCopy extends AbstractSpellMatrix {

	public SpellCopy(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.CTRL);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry... slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3d from = picks[0].point3d();
		Point3d to = picks[1].point3d();
		Vector3d v = new Vector3d();
		v.sub(to, from);
		Matrix4d matrix = new TranslationMatrix(v);
		boolean deleteOriginal = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteOriginal);
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
