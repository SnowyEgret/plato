package ds.plato.spell.draw;

import javax.vecmath.Point3d;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.PointSet;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellMeasure extends AbstractSpellDraw {

	public SpellMeasure(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_measure_name;
		d.description = Messages.spell_measure_description;
		d.picks = new PickDescriptor(Messages.spell_measure_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_measure_modifier_0, Messages.spell_measure_modifier_1, Messages.spell_measure_modifier_2);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicks();
		Point3d p0 = picks[0].point3d();
		Point3d p1 = picks[1].point3d();
		message = String.format("Distance: %.1f", p0.distance(p1));
		PointSet points = new PointSet();
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			// TODO if point is on border between two blocks, draw both blocks
			points.addPoint(interpolate(p0, p1, .5d));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) { //Alt
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
