package ds.plato.common;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.undo.IUndo;
import ds.plato.undo.IUndoable;

public class KeyInputEventHandler {

	private IUndoable undoManager;

	public KeyInputEventHandler(IUndoable undoManager) {
		this.undoManager = undoManager;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		if (Plato.keyUndo.isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					undoManager.undo();
			} catch (Exception e) {
				
				// TODO Log to chat
				Plato.log.info(e.getMessage());
			}
		}

		if (Plato.keyRedo.isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					undoManager.redo();
			} catch (Exception e) {
				// TODO Log to chat
				Plato.log.info(e.getMessage());
			}
		}

		if (Plato.keyToggle.isPressed()) {
			Item i = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem();
			if (i instanceof IToggleable) {
				((IToggleable) i).toggle();
			}
		}

		if (Plato.keyDelete.isPressed()) {
			Plato.editStick.deleteSelections();
		}

		if (event.isCancelable())
			event.setCanceled(true);
	}
	
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent
//	public void onMouseInput(MouseInputEvent event) {
//		// Works
//		MOD.log.info("[KeyInputEventHandler.onMouseInput] event=" + event);
//	}
	

}
