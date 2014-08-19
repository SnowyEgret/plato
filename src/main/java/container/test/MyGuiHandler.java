package container.test;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MyGuiHandler implements IGuiHandler {

	IInventory inventory;

	public MyGuiHandler(IInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("[MyGuiHandler.getServerGuiElement] id=" + id);
		if (id == 0) {
			return new MyContainer(player.inventory, inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("[MyGuiHandler.getClientGuiElement] id=" + id);
		if (id == 0) {
			return new MyGui(new MyContainer(player.inventory, inventory));
		}
		return null;
	}
}
