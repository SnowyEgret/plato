package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyItem extends Item implements IInvBasic {

	InventoryBasic inventory;

	public MyItem() {
		inventory = new InventoryBasic("", false, 9);
		// inventory.func_110134_a(this);
	}

//	@Override
//	public boolean onItemUse(ItemStack stack, EntityPlayer p, World w, int x, int y, int z, int s, float px, float py,
//			float pz) {
//		p.openGui(MyMod.instance, 0, w, x, y, z);
//		return true;
//	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p) {
		p.openGui(MyMod.instance, 0, w, (int) p.posX, (int) p.posY, (int) p.posZ);
		return stack;
	}

	@Override
	public void onInventoryChanged(InventoryBasic inventory) {
		System.out.println("[MyItem.onInventoryChanged] this.inventory=" + this.inventory);
		System.out.println("[MyItem.onInventoryChanged] inventory=" + inventory);
	}
}
