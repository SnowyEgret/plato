package ds.plato.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.solid.Box;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.Modifier;
import ds.plato.undo.IUndo;

public class SpellBox extends AbstractSpellDraw {

	public SpellBox(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.CTRL, Modifier.SHIFT, Modifier.ALT);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		selectionManager.clearSelections();
		Pick[] picks = pickManager.getPicks();
		boolean isCube = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		Point3d p0 = picks[0].point3d();
		Point3d p1 = picks[1].point3d();
		boolean onSurface = Keyboard.isKeyDown(Keyboard.KEY_LMENU);
		if (onSurface) {
			p0.y += 1;
			p1.y += 1;
		}
		IDrawable d = new Box(p0, p1, isCube);
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
