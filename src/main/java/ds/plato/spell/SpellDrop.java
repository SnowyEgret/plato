package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellDrop extends Spell {

	public SpellDrop(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_drop_name;
		d.description = Messages.spell_drop_description;
		d.picks = new PickDescriptor(Messages.spell_pick_anywhere);
		d.modifiers = new ModifierDescriptor(Messages.spell_modifier_deleteOriginal, Messages.spell_drop_modifier);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		boolean fill = Keyboard.isKeyDown(Keyboard.KEY_LMENU);
		boolean deleteOriginal = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		Transaction transaction = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Block b = world.getBlock(s.x, s.y - 1, s.z);
			int drop = 0;
			while (b == Blocks.air) {
				drop++;
				Block nextBlock = world.getBlock(s.x, s.y - drop - 1, s.z);
				if (fill || nextBlock != Blocks.air) {
					transaction.add(new SetBlock(world, selectionManager, s.x, s.y - drop, s.z, s.block, s.metadata).set());
				}
				b = nextBlock;
			}
			selectionManager.deselect(s);
			if (deleteOriginal) {
				transaction.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, Blocks.air, 0).set());
			}
		}
		transaction.commit();
	}

	@Override
	public Object[] getRecipe() {
		return null;
	}

}
