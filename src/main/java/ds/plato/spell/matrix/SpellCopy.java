package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.matrix.TranslationMatrix;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellCopy extends AbstractSpellMatrix {

	public SpellCopy(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry... slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3d from = picks[0].point3d();
		Point3d to = picks[1].point3d();
		Vector3d v = new Vector3d();
		v.sub(to, from);
		Matrix4d matrix = new TranslationMatrix(v);
		boolean deleteInitialBlocks = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteInitialBlocks);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = I18n.format("item.spellCopy.name");
		d.description = I18n.format("item.spellCopy.description");
		d.picks = new PickDescriptor(I18n.format("pick.from"), I18n.format("pick.to"));
		d.modifiers = new ModifierDescriptor(CTRL + I18n.format("modifier.deleteOriginal"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
