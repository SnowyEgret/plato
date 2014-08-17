package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class MyContainer extends Container {

	public MyContainer(InventoryPlayer playerInventory, IInventory inventory) {

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 18));
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 49 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 107));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
