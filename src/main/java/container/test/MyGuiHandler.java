package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MyGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
		return new MyContainer(player.inventory, new MyInventory(player.inventory, player.inventory.currentItem));
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
		return new MyGui(new MyContainer(player.inventory, new MyInventory(player.inventory, player.inventory.currentItem)));
	}
}
