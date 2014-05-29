package ds.plato.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import ds.plato.common.CommonProxy;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;
	private Map<String, KeyBinding> keyBindings = new HashMap<>();

	public static void setCustomRenderers() {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer());
	}
	
	private KeyBinding registerKeyBinding(String name, int key, String modName) {
		KeyBinding b = new KeyBinding(name, key, modName);
		ClientRegistry.registerKeyBinding(b);
		return b;
	}

	@Override
	public Map<String, KeyBinding> registerKeyBindings(String modName) {
		// TODO get NLS properties these strings
		keyBindings.put("undo", registerKeyBinding("Undo", Keyboard.KEY_Z, modName));
		keyBindings.put("redo", registerKeyBinding("Redo", Keyboard.KEY_Y, modName));
		keyBindings.put("toggle", registerKeyBinding("Toggle", Keyboard.KEY_TAB, modName));
		keyBindings.put("delete", registerKeyBinding("Delete", Keyboard.KEY_DELETE, modName));
		return keyBindings;
	}

}
