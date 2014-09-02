package ds.plato.proxy;

import ds.plato.Plato;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Spell;
import ds.plato.staff.StaffWood;
import ds.plato.undo.IUndo;

public class CommonProxy {

	public void setCustomRenderers(ISelect selectionManager, IPick pickManager, Iterable<StaffWood> staffs, Iterable<Spell> spells) {
	}

	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
	}

	// @Override
	// public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	// switch (ID) {
	// case 0:
	// return null;
	// case 3:
	// //From http://www.minecraftforge.net/wiki/Containers_and_GUIs:
	// //Note that the client returns a Gui while the server returns a Container
	// return new GuiStaffContainer(player.inventory, (IInventory) player.getHeldItem().getItem());
	// default:
	// return null;
	// }
	// }
	//
	// @Override
	// public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	// return null;
	// }

}
