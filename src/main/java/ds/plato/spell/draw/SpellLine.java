package ds.plato.spell.draw;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.curve.Line;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
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
		d.name = I18n.format("item.spellLine.name");
		d.description = I18n.format("item.spellLine.description");
		d.picks = new PickDescriptor(I18n.format("pick.from"), I18n.format("pick.to"));
		d.modifiers = new ModifierDescriptor(CTRL+ I18n.format("item.spellLine.modifier.0"));
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		Pick[] picks = pickManager.getPicks();
		IDrawable d = new Line(picks[0].point3d(), picks[1].point3d());
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
			pickManager.clearPicks();
			pickManager.reset(2);
			pickManager.pick(world, picks[1].x, picks[1].y, picks[1].z, 0);
		} else {
			selectionManager.clearSelections();
			// Best in this order
			pickManager.clearPicks();
			draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
		}
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
