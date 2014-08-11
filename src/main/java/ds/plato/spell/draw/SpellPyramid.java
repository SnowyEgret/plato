package ds.plato.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.solid.RectangularPyramid;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellPyramid extends AbstractSpellDraw {

	public SpellPyramid(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_pyramid_name;
		d.description = Messages.spell_pyramid_description;
		d.picks = new PickDescriptor(Messages.spell_pyramid_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_pyramid_modifier, Messages.spell_modifier_isHollow);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		selectionManager.clearSelections();
		Pick[] picks = pickManager.getPicks();
		Point3d p0 = picks[0].point3d();
		Point3d p1 = picks[1].point3d();
		//Pyramid sits on ground like cube
//		p0.y += 1;
//		p1.y += 1;
		boolean isSquare = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		IDrawable d = new RectangularPyramid(p0, p1, isSquare);
		boolean isHollow = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		draw(d, world, slotEntries[0].block, slotEntries[0].metadata, isHollow);
		pickManager.clearPicks();
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
