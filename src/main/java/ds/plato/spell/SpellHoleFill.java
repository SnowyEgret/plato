package ds.plato.spell;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
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
		d.name = I18n.format("item.spellHoleFill.name");
		d.description = I18n.format("item.spellHoleFill.description");
		d.picks = new PickDescriptor(I18n.format("pick.anywhere"));
		d.modifiers = new ModifierDescriptor("ctrl," + I18n.format("item.spellHoleFill.modifier.0"), "shift,"
				+ I18n.format("item.spellHoleFill.modifier.1"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BTB", "   ", 'T', Items.ghast_tear, 'B', Items.water_bucket };
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Shell.Type type = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ? Shell.Type.HORIZONTAL : Shell.Type.BELLOW;
			Shell shell = new Shell(type, s.point3i(), world);
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
