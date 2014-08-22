package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyItem extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int s, float sx,
			float sy, float sz) {
		if (!world.isRemote) {
			player.openGui(MyMod.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
}
