package ds.plato.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ds.plato.Plato;
import ds.plato.block.BlockPickedRenderer;
import ds.plato.block.BlockSelectedRenderer;
import ds.plato.core.ForgeEventHandler;
import ds.plato.core.KeyInputEventHandler;
import ds.plato.gui.GuiRestore;
import ds.plato.gui.GuiSave;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;
	public static int blockPickedRenderId;

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new GuiSave(player);
		case 1:
			return new GuiRestore(player);
		default:
			return null;
		}
	}

	public void setCustomRenderers(ISelect selectionManager, IPick pickManager) {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockPickedRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer(blockSelectedRenderId, selectionManager));
		RenderingRegistry.registerBlockHandler(new BlockPickedRenderer(blockPickedRenderId, pickManager));
	}

	@Override
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
		Overlay overlay = new Overlay(select);
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler(undo, select, pick, overlay));
		Map<String, KeyBinding> keyBindings = new HashMap<>();
		// TODO get NLS properties these strings
		keyBindings.put("undo", registerKeyBinding("Undo", Keyboard.KEY_Z, plato.NAME));
		keyBindings.put("redo", registerKeyBinding("Redo", Keyboard.KEY_Y, plato.NAME));
		keyBindings.put("toggle", registerKeyBinding("Toggle", Keyboard.KEY_TAB, plato.NAME));
		keyBindings.put("delete", registerKeyBinding("Delete", Keyboard.KEY_DELETE, plato.NAME));
		keyBindings.put("lastSelection", registerKeyBinding("Last selection", Keyboard.KEY_L, plato.NAME));
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler(keyBindings, undo, select, pick));
	}

	private KeyBinding registerKeyBinding(String name, int key, String modName) {
		KeyBinding b = new KeyBinding(name, key, modName);
		ClientRegistry.registerKeyBinding(b);
		return b;
	}
}
