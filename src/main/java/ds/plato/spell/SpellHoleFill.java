package ds.plato.spell;

import javax.vecmath.Point3i;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import ds.plato.Plato;
import ds.plato.common.UndoableSetBlock;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.EnumShell;
import ds.plato.spell.select.Shell;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellHoleFill extends Spell {

	public SpellHoleFill(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_hole_fill_name;
		d.description = Messages.spell_hole_fill_description;
		d.picks = new PickDescriptor(Messages.spell_pick_anywhere);
		d.modifiers = new ModifierDescriptor(Messages.spell_hole_fill_modifier_0, Messages.spell_hole_fill_modifier_1);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BTB", "   ", 'T', Items.ghast_tear, 'B', Items.water_bucket };
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			EnumShell type = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ? EnumShell.HORIZONTAL : EnumShell.BELLOW;
			Shell shell = new Shell(type, s.getPoint3i(), world);
			for (Point3i p : shell) {
				Block b = world.getBlock(p.x, p.y, p.z);
				if (b == Blocks.air || b == Blocks.water) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
						SlotEntry e = slotEntries[0];
						t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, e.block, e.metadata).set());
					} else {
						t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, s.block, s.metadata).set());
					}
				}
			}
			selectionManager.deselect(s);
		}
		t.commit();
	}
}
