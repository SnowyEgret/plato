package ds.plato.common;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.undo.IUndo;
import ds.plato.undo.IUndoable;

public class KeyInputEventHandler {

	private IUndoable undoManager;
	private Map<String, KeyBinding> keyBindings;

	public KeyInputEventHandler(Map<String, KeyBinding> keyBindings, IUndoable undoManager) {
		this.undoManager = undoManager;
		this.keyBindings = keyBindings;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		// if (Plato.keyUndo.isPressed()) {
		if (keyBindings.get("undo").isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					undoManager.undo();
			} catch (Exception e) {

				// TODO Log to chat
				Plato.log.info("[KeyInputEventHandler.onKeyInput]" + e.getMessage());
			}
		}

		if (keyBindings.get("redo").isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					undoManager.redo();
			} catch (Exception e) {
				// TODO Log to chat
				Plato.log.info("[KeyInputEventHandler.onKeyInput]" + e.getMessage());
			}
		}

		if (keyBindings.get("toggle").isPressed()) {
			Item i = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem();
			if (i instanceof IToggleable) {
				((IToggleable) i).toggle();
			}
		}

		if (keyBindings.get("delete").isPressed()) {
			Plato.editStick.deleteSelections();
		}

		if (event.isCancelable())
			event.setCanceled(true);
	}

	// @SideOnly(Side.CLIENT)
	// @SubscribeEvent
	// public void onMouseInput(MouseInputEvent event) {
	// // Works
	// MOD.log.info("[KeyInputEventHandler.onMouseInput] event=" + event);
	// }

}
