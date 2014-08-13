package foo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoo extends Item {

	InventoryBasic inventory;

	public ItemFoo() {
		inventory = new InventoryBasic("foo", false, 9);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player) {
		player.openGui(ModFoo.instance, 0, w, 0, 0, 0);
		return stack;
	}
}
