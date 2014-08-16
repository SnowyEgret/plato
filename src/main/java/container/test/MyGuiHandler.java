package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MyGuiHandler implements IGuiHandler {

	MyItem myItem;

	public MyGuiHandler(MyItem myItem) {
		this.myItem = myItem;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("[MyGuiHandler.getServerGuiElement] id=" + id);
		if (id == 0) {
			return new MyContainer(player.inventory, myItem.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("[MyGuiHandler.getClientGuiElement] id=" + id);
		new Throwable().printStackTrace();
		if (id == 0) {
			return new MyGui(player.inventory, myItem.inventory);
		}
		return null;
	}
}
