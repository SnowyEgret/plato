package ds.plato.item.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.solid.RectangularPyramid;
import ds.plato.item.spell.descriptor.Modifier;
import ds.plato.pick.Pick;

public class SpellPyramid extends AbstractSpellDraw {

	public SpellPyramid(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.CTRL, Modifier.SHIFT);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		selectionManager.clearSelections();
		Pick[] picks = pickManager.getPicks();
		Point3d p0 = picks[0].point3d();
		Point3d p1 = picks[1].point3d();
		// Pyramid sits on ground like cube
		// p0.y += 1;
		// p1.y += 1;
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
