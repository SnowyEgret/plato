package ds.plato.spell;

import ds.geom.Drawable;
import ds.geom.Sphere;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SphereSpell extends AbstractDrawSpell {

	
	public SphereSpell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(Pick[] picks, SlotEntry[] slotEntries) {
		assert picks.length == getNumPicks();
		Drawable d = new Sphere(picks[0].toDouble(), picks[1].toDouble());
		draw(d, slotEntries[0].block, slotEntries[0].metadata, false);
	}

	@Override
	public int getNumPicks() {
		return 2;
	}

}