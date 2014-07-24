package ds.plato.gui;

import ds.plato.spell.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiStaffSlotSpell extends Slot {

	public GuiStaffSlotSpell(IInventory inventoryStaff, int index, int x, int y) {
		super(inventoryStaff, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		Item item = stack.getItem();
		System.out.println("[SpellSlot.isItemValid] item=" + item);
		return Spell.class.isAssignableFrom(item.getClass()) ? true : false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return true;
	}

}
