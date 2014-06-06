package ds.plato.core;

import java.util.Map;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.Plato;
import ds.plato.common.IToggleable;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.undo.IUndo;
import ds.plato.undo.IUndoable;

public class KeyInputEventHandler {

	private IUndoable undoManager;
	private Map<String, KeyBinding> keyBindings;
	private ISelect selectionManager;
	private IPick pickManager;
	private BlockAir blockAir;

	public KeyInputEventHandler(
			Map<String, KeyBinding> keyBindings,
			IUndoable undo,
			ISelect select,
			IPick pick,
			BlockAir blockAir) {
		this.undoManager = undo;
		this.selectionManager = select;
		this.pickManager = pick;
		this.keyBindings = keyBindings;
		this.blockAir = blockAir;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		World w = Minecraft.getMinecraft().thePlayer.worldObj;

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
				// TODO Log to overlay. Create info line in overlay
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
			// Plato.editStick.deleteSelections();
			// TODO pass world to invoke instead of constructor so that spells function in nether. Remove
			// Spell.setWorld() and Staff.setWorld()
			//new SpellDelete((IUndo) undoManager, selectionManager, pickManager, blockAir).invoke(new WorldWrapper(w));
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
