package ds.plato.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ds.plato.Plato;
import ds.plato.block.BlockModelRenderer;
import ds.plato.block.BlockPickedRenderer;
import ds.plato.block.BlockSelectedRenderer;
import ds.plato.event.ForgeEventHandler;
import ds.plato.event.KeyInputEventHandler;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.staff.StaffWood;
import ds.plato.staff.StaffWoodRenderer;
import ds.plato.undo.IUndo;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;
	public static int blockPickedRenderId;
	public static int blockModelRenderId;

	@Override
	public void setCustomRenderers(ISelect selectionManager, IPick pickManager, StaffWood staffWood) {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockPickedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockModelRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer(blockSelectedRenderId, selectionManager));
		RenderingRegistry.registerBlockHandler(new BlockPickedRenderer(blockPickedRenderId, selectionManager,
				pickManager));
		RenderingRegistry.registerBlockHandler(new BlockModelRenderer(blockModelRenderId));
		
		MinecraftForgeClient.registerItemRenderer(staffWood, new StaffWoodRenderer());
	}

	@Override
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
		Overlay overlay = new Overlay(select);
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler(undo, select, pick, overlay));
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler(undo, select, pick));
	}
}
