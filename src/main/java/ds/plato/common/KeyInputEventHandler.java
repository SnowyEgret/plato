package ds.plato.common;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KeyInputEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		if (Plato.keyUndo.isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					Plato.undoManager.undo();
			} catch (Exception e) {
				// TODO Log to chat
				Plato.log.info(e.getMessage());
			}
		}

		if (Plato.keyRedo.isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
					Plato.undoManager.redo();
			} catch (Exception e) {
				// TODO Log to chat
				Plato.log.info(e.getMessage());
			}
		}

		if (Plato.keyToggle.isPressed()) {
			Item i = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem();
			if (i instanceof Stick) {
				((Stick) i).toggle();
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
