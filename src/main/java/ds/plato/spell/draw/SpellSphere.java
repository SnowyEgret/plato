package ds.plato.spell.draw;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;

import ds.plato.common.SlotEntry;
import ds.plato.core.IWorld;
import ds.plato.geom.IDrawable;
import ds.plato.geom.surface.Sphere;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellSphere extends AbstractSpellDraw {

	public SpellSphere(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir air) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		IDrawable d = new Sphere(picks[0].toDouble(), picks[1].toDouble());
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
