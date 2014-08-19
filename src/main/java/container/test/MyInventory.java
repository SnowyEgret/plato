package container.test;

import net.minecraft.inventory.InventoryBasic;

public class MyInventory extends InventoryBasic {

	public MyInventory() {
		super("", false, 9);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

}
