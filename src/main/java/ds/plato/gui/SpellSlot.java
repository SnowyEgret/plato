package ds.plato.gui;

import ds.plato.spell.Spell;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpellSlot extends Slot {

	public SpellSlot(IInventory inventoryStaff, int index, int x, int y) {
		super(inventoryStaff, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		Item item = stack.getItem();
		System.out.println("[SpellSlot.isItemValid] item=" + item);
		return Spell.class.isAssignableFrom(item.getClass()) ? true : false;
	}

}
