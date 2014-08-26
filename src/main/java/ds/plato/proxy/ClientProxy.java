package ds.plato.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ds.plato.Plato;
import ds.plato.block.BlockModelRenderer;
import ds.plato.block.BlockPickedRenderer;
import ds.plato.block.BlockSelectedRenderer;
import ds.plato.core.ForgeEventHandler;
import ds.plato.core.KeyInputEventHandler;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;
	public static int blockPickedRenderId;
	public static int blockModelRenderId;

	// @Override
	// public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	// switch (id) {
	// case -1:
	// return new GuiDialog(player, "Ok", "Cancel");
	// case 0:
	// return new GuiTextInputDialog(player);
	// case 1:
	// return new GuiRestore(player);
	// case 2:
	// return new GuiSpellText(player);
	// case 3:
	// //return new GuiStaff(player.inventory, (IInventory) Player.client().getHeldItem());
	// return new GuiStaff(player.inventory, (IInventory) player.getHeldItem().getItem());
	// default:
	// throw new IllegalArgumentException("GUI id "+ id +" is undefined");
	// }
	// }

	@Override
	public void setCustomRenderers(ISelect selectionManager, IPick pickManager) {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockPickedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockModelRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer(blockSelectedRenderId, selectionManager));
		RenderingRegistry.registerBlockHandler(new BlockPickedRenderer(blockPickedRenderId, selectionManager,
				pickManager));
		RenderingRegistry.registerBlockHandler(new BlockModelRenderer(blockModelRenderId));
	}

	@Override
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick) {
		Overlay overlay = new Overlay(select);
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler(undo, select, pick, overlay));
		Map<String, KeyBinding> keyBindings = new HashMap<>();
		// TODO get NLS properties these strings
		keyBindings.put("undo", registerKeyBinding("Undo", Keyboard.KEY_Z, Plato.NAME));
		keyBindings.put("redo", registerKeyBinding("Redo", Keyboard.KEY_Y, Plato.NAME));
		keyBindings.put("toggle", registerKeyBinding("Toggle", Keyboard.KEY_TAB, Plato.NAME));
		keyBindings.put("delete", registerKeyBinding("Delete", Keyboard.KEY_DELETE, Plato.NAME));
		keyBindings.put("lastSelection", registerKeyBinding("Last selection", Keyboard.KEY_L, Plato.NAME));
		keyBindings.put("left", registerKeyBinding("Move left", Keyboard.KEY_LEFT, Plato.NAME));
		keyBindings.put("right", registerKeyBinding("Move right", Keyboard.KEY_RIGHT, Plato.NAME));
		keyBindings.put("up", registerKeyBinding("Move up", Keyboard.KEY_UP, Plato.NAME));
		keyBindings.put("down", registerKeyBinding("Move down", Keyboard.KEY_DOWN, Plato.NAME));
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler(keyBindings, undo, select, pick));
	}

	private KeyBinding registerKeyBinding(String name, int key, String modName) {
		KeyBinding b = new KeyBinding(name, key, modName);
		ClientRegistry.registerKeyBinding(b);
		return b;
	}
}
