package ds.plato.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.curve.Line;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellLine extends AbstractSpellDraw {

	public SpellLine(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_line_name;
		d.description = Messages.spell_line_description;
		d.picks = new PickDescriptor(Messages.spell_line_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_line_modifier);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		IDrawable d = new Line(picks[0].toPoint3d(), picks[1].toPoint3d());
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
			pickManager.clearPicks();
			pickManager.reset(2);
			pickManager.pick(picks[1].x, picks[1].y, picks[1].z);
		} else {
			selectionManager.clearSelections();
			draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
			pickManager.clearPicks();
		}
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
