package ds.plato.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ds.plato.staff.InventoryStaff;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
		return new GuiStaffContainer(player.inventory, new InventoryStaff(player.inventory, player.inventory.currentItem));
	}

//	@Override
//	public Object getClientGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
//		return new GuiStaff(new GuiStaffContainer(player.inventory, new InventoryStaff(player.inventory, player.inventory.currentItem)));
//	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
		case -1:
			return new GuiDialog(player, "Ok", "Cancel");
		case 0:
			return new GuiTextInputDialog(player);
		case 1:
			return new GuiRestore(player);
		case 2:
			return new GuiSpellText(player);
		case 3:
			return new GuiStaff(new GuiStaffContainer(player.inventory, new InventoryStaff(player.inventory, player.inventory.currentItem)));
		default:
			throw new IllegalArgumentException("GUI id "+ id +" is undefined");
		}
	}
}
