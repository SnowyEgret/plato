package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3i;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.matrix.ReflectionMatrix;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellMirror extends AbstractSpellMatrix {

	public SpellMirror(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(3, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3i c = selectionManager.voxelSet().centroid();
		// FIXME
		Matrix4d matrix = new ReflectionMatrix(picks[0].point3d(), picks[1].point3d(), picks[2].point3d());
		boolean deleteInitialBlocks = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		transformSelections(matrix, world, deleteInitialBlocks);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = I18n.format("item.spellMirror.name");
		d.description = I18n.format("item.spellMirror.description");
		d.picks = new PickDescriptor(I18n.format("item.spellMirror.pick.0"), I18n.format("item.spellMirror.pick.1"), I18n.format("item.spellMirror.pick.2"));
		d.modifiers = new ModifierDescriptor(CTRL+I18n.format("modifier.deleteOriginal"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
