package ds.plato.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ds.plato.common.CommonProxy;
import ds.plato.common.ForgeEventHandler;
import ds.plato.common.KeyInputEventHandler;
import ds.plato.common.Plato;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;
	public static int blockPickedRenderId;

	public static void setCustomRenderers(ISelect selectionManager, IPick pickManager) {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		blockPickedRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer(blockSelectedRenderId, selectionManager));
		RenderingRegistry.registerBlockHandler(new BlockPickedRenderer(blockPickedRenderId, pickManager));
	}
	
	@Override
	public void registerEventHandlers(Plato plato, ISelect select, IUndo undo, IPick pick, BlockAir air) {
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler(plato, select, undo));
		Map<String, KeyBinding> keyBindings = new HashMap<>();
		// TODO get NLS properties these strings
		keyBindings.put("undo", registerKeyBinding("Undo", Keyboard.KEY_Z, plato.NAME));
		keyBindings.put("redo", registerKeyBinding("Redo", Keyboard.KEY_Y, plato.NAME));
		keyBindings.put("toggle", registerKeyBinding("Toggle", Keyboard.KEY_TAB, plato.NAME));
		keyBindings.put("delete", registerKeyBinding("Delete", Keyboard.KEY_DELETE, plato.NAME));
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler(keyBindings, undo, select, pick, air));
	}

	private KeyBinding registerKeyBinding(String name, int key, String modName) {
		KeyBinding b = new KeyBinding(name, key, modName);
		ClientRegistry.registerKeyBinding(b);
		return b;
	}
}
