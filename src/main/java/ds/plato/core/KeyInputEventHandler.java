package ds.plato.core;

import java.util.Map;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.Plato;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.undo.IUndo;
import ds.plato.undo.IUndoable;

public class KeyInputEventHandler {

	private IUndo undoManager;
	private Map<String, KeyBinding> keyBindings;
	private ISelect selectionManager;
	private IPick pickManager;

	public KeyInputEventHandler(Map<String, KeyBinding> keyBindings, IUndo undo, ISelect select, IPick pick) {
		this.undoManager = undo;
		this.selectionManager = select;
		this.pickManager = pick;
		this.keyBindings = keyBindings;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		
		//World w = Minecraft.getMinecraft().thePlayer.worldObj;
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		World w = Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(player.dimension);

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
			ItemStack is = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
			if (is != null) {
				Item i = is.getItem();
				if (i instanceof IToggleable) {
					((IToggleable) i).toggle();
				}
			}
		}

		if (keyBindings.get("delete").isPressed()) {
			new SpellDelete(undoManager, selectionManager, pickManager).invoke(new WorldWrapper(w), null);
		}

		if (keyBindings.get("lastSelection").isPressed()) {
			selectionManager.reselectLast();
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
