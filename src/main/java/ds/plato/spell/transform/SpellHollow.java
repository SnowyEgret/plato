package ds.plato.spell.transform;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.init.Blocks;
import ds.plato.Plato;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.VoxelSet;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellHollow extends AbstractSpellTransform {

	public SpellHollow(IUndo undo, ISelect select, IPick pick) {
		super(undo, select, pick);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_hollow_name;
		d.description = Messages.spell_hollow_description;
		d.picks = new PickDescriptor(Messages.spell_pick_anywhere);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		transformSelections(world, new ITransform() {
			VoxelSet selections = selectionManager.voxelSet();

			@Override
			public Selection transform(Selection s) {
				List surroundingPoints = new ArrayList();
				surroundingPoints.add(new Point3i(s.x + 1, s.y, s.z));
				surroundingPoints.add(new Point3i(s.x - 1, s.y, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y + 1, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y - 1, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y, s.z + 1));
				surroundingPoints.add(new Point3i(s.x, s.y, s.z - 1));
				if (selections.containsAll(surroundingPoints)) {
					s.block = Blocks.air;
				}
				return s;
			}
		});
	}

}
