package ds.plato.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ds.plato.Plato;
import ds.plato.block.BlockModelRenderer;
import ds.plato.block.BlockPickedRenderer;
import ds.plato.block.BlockSelectedRenderer;
import ds.plato.event.ForgeEventHandler;
import ds.plato.event.KeyHandler;
import ds.plato.event.MouseHandler;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.staff.StaffWood;
import ds.plato.staff.StaffWoodRenderer;
import ds.plato.undo.IUndo;

public class ClientProxy extends CommonProxy {

	@Override
	public void setCustomRenderers(ISelect select, IPick pick, StaffWood staffWood) {
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer(select));
		RenderingRegistry.registerBlockHandler(new BlockPickedRenderer(select, pick));
		RenderingRegistry.registerBlockHandler(new BlockModelRenderer());
		MinecraftForgeClient.registerItemRenderer(staffWood, new StaffWoodRenderer());
	}

	@Override
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
		Overlay overlay = new Overlay(select);
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler(select, pick, overlay));
		MinecraftForge.EVENT_BUS.register(new MouseHandler(undo, select, pick));
		FMLCommonHandler.instance().bus().register(new KeyHandler(undo, select, pick));
	}
}
