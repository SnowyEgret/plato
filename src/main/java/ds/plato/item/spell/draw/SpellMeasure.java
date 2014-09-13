package ds.plato.item.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.HotbarSlot;
import ds.plato.geom.PointSet;
import ds.plato.item.spell.Modifier;
import ds.plato.pick.Pick;

public class SpellMeasure extends AbstractSpellDraw {

	public SpellMeasure(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.CTRL, Modifier.SHIFT, Modifier.ALT);
	}

	@Override
	public void invoke(IWorld world, HotbarSlot... slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3d p0 = picks[0].point3d();
		Point3d p1 = picks[1].point3d();
		message = String.format("Distance: %.1f", p0.distance(p1));
		PointSet points = new PointSet();
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			// TODO if point is on border between two blocks, draw both blocks
			points.addPoint(interpolate(p0, p1, .5d));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) { // Alt
			double d = 1 / 3d;
			for (int i = 1; i < 3; i++) {
				double dd = i * d;
				points.addPoint(interpolate(p0, p1, i * d));
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			points.addPoints(p0, p1);
		}
		if (!points.isEmpty()) {
			draw(points, world, slotEntries[0].block, slotEntries[0].metadata);
			selectionManager.clearSelections();
		}
		pickManager.clearPicks();
	}

	private Point3d interpolate(Point3d p0, Point3d p1, double d) {
		Point3d p = new Point3d();
		p.interpolate(p0, p1, d);
		return p;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
