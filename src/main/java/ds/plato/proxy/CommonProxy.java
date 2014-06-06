package ds.plato.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.BlockAir;
import ds.plato.Plato;
import ds.plato.block.BlockPickedRenderer;
import ds.plato.block.BlockSelectedRenderer;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class CommonProxy {

	public void setCustomRenderers(ISelect selectionManager, IPick pickManager) {
	}
	
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick, BlockAir air) {
	}

}
