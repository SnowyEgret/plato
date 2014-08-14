package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyItem extends Item {

	InventoryBasic inventory;

	public MyItem() {
		inventory = new InventoryBasic("", false, 9);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player) {
		player.openGui(MyMod.instance, 0, w, 0, 0, 0);
		return stack;
	}
}
