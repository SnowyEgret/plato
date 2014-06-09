package ds.plato.proxy;

import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ds.plato.Plato;
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
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
