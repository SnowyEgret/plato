package ds.plato.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ds.plato.Plato;
import ds.plato.gui.GuiStaffContainer;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class CommonProxy implements IGuiHandler {

	public void setCustomRenderers(ISelect selectionManager, IPick pickManager) {
	}
	
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return null;
		case 3:
			//From http://www.minecraftforge.net/wiki/Containers_and_GUIs:
			//Note that the client returns a Gui while the server returns a Container
			return new GuiStaffContainer(player.inventory, (IInventory) player.getHeldItem().getItem());
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
