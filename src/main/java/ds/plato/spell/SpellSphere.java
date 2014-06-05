package ds.plato.spell;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.geom.Drawable;
import ds.plato.geom.Sphere;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellSphere extends AbstractSpellDraw {

	public SpellSphere(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir air) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Drawable d = new Sphere(picks[0].toDouble(), picks[1].toDouble());
		draw(d, world, slotEntries[0].block, slotEntries[0].metadata, false);
	}

	@Override
	public int getNumPicks() {
		return 2;
	}

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = Messages.spell_sphere_name;
			description = Messages.spell_sphere_description;
			picks = new PickDescriptor(Messages.spell_sphere_picks);
		}
	}
}
