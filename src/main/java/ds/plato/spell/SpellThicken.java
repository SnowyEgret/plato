package ds.plato.spell;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import org.lwjgl.input.Keyboard;

import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;
import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.geom.GeomUtil;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.EnumShell;
import ds.plato.spell.select.Shell;
import ds.plato.spell.transform.AbstractSpellTransform;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellThicken extends AbstractSpellTransform {

	public SpellThicken(IUndo undo, ISelect select, IPick pick) {
		super(undo, select, pick);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_thicken_name;
		d.description = Messages.spell_thicken_description;
		d.picks = new PickDescriptor(Messages.spell_pick_anywhere);
		d.modifiers = new ModifierDescriptor(Messages.spell_thicken_modifier_0, Messages.spell_thicken_modifier_1);
		return d;
	}

	@Override
	public void invoke(final IWorld world, SlotEntry[] slotEntries) {
		Transaction t = undoManager.newTransaction();
		final Point3d c = GeomUtil.toPoint3d(selectionManager.voxelSet().centroid());
		for (Selection s : selectionManager.getSelections()) {
			double d = s.getPoint3d().distance(c);
			Shell shell = new Shell(EnumShell.ALL, s.getPoint3i(), world);
			for (Point3i p : shell) {
				double dd = GeomUtil.toPoint3d(p).distance(c);
				boolean in = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
				boolean out = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
				if ((in && dd < d) || (out && dd > d) || (!in && !out)) {
					t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, s.block, s.metadata).set());
				}
			}
		}
		t.commit();
	}

}
