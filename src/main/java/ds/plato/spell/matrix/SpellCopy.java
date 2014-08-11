package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.geom.matrix.TranslationMatrix;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellCopy extends AbstractSpellMatrix {

	public SpellCopy(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry...slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3d from = picks[0].point3d();
		Point3d to = picks[1].point3d();
		Vector3d v = new Vector3d();
		v.sub(to, from);
		Matrix4d matrix = new TranslationMatrix(v);
		transformSelections(matrix, world, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_copy_name;
		d.description = Messages.spell_copy_description;
		d.picks = new PickDescriptor(Messages.spell_copy_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_copy_modifier);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
