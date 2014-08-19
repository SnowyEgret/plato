package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyItem extends Item {

	///InventoryBasic inventory = new InventoryBasic("", false, 9);
	IInventory inventory = new MyInventory();

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer p, World w, int x, int y, int z, int s, float sx, float sy,
			float sz) {
		// if (!w.isRemote) {
		p.openGui(MyMod.instance, 0, w, x, y, z);
		return true;
		// }
		// return false;
	}
}
