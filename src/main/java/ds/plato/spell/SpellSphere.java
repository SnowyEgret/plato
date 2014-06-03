package ds.plato.spell;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import ds.geom.Drawable;
import ds.geom.Sphere;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public class SpellSphere extends AbstractSpellDraw {

	public SpellSphere(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir air) {
		super(new Descriptor(), undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Drawable d = new Sphere(picks[0].toDouble(), picks[1].toDouble());
		draw(d, slotEntries[0].block, slotEntries[0].metadata, false);
	}

	@Override
	public int getNumPicks() {
		return 2;
	}

	private static class Descriptor extends AbstractSpellDescriptor {
		public Descriptor() {
			name = "SPHERE";
			description = "Creates a sphere";
			picks = new PickDescriptor("Center", "Point on sphere");
			modifiers = new ModifierDescriptor(Pair.of("ctrl", "Deletes originial"));
		}
	}
}
