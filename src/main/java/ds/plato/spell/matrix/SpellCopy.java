package ds.plato.spell.matrix;

import javax.vecmath.Matrix4d;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
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

public class SpellCopy extends AbstractSpellMatrix {

	public SpellCopy(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Matrix4d matrix = GeomUtil.newTranslationMatrix(picks[0].toPoint3d(), picks[1].toPoint3d());
		transformSelections(matrix, world, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
	}

	@Override
	public int getNumPicks() {
		return 2;
	}

//	private static class Descriptor extends SpellDescriptor {
//		public Descriptor() {
//			name = Messages.spell_copy_name;
//			description = Messages.spell_copy_description;
//			picks = new PickDescriptor(Messages.spell_copy_picks);
//			modifiers = new ModifierDescriptor(Messages.spell_copy_modifier);
//		}
//	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_copy_name;
		d.description = Messages.spell_copy_description;
		d.picks = new PickDescriptor(Messages.spell_copy_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_copy_modifier);
		return d;
	}
}
