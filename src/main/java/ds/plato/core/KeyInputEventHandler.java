package ds.plato.core;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.Plato;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Spell;
import ds.plato.spell.matrix.SpellCopy;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.undo.IUndo;

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

		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		IWorld w = Spell.getWorldServer(player);

		if (keyBindings.get("undo").isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					undoManager.undo();
				}
			} catch (Exception e) {
				// TODO Log to chat
				Plato.log.info("[KeyInputEventHandler.onKeyInput]" + e.getMessage());
			}
		}

		if (keyBindings.get("redo").isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					undoManager.redo();
				}
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
			new SpellDelete(undoManager, selectionManager, pickManager).invoke(w, null);
		}

		if (keyBindings.get("lastSelection").isPressed()) {
			selectionManager.reselectLast();
		}

		if (keyBindings.get("left").isPressed()) {
			copyHorizontal(player, w, 2);
		}

		if (keyBindings.get("right").isPressed()) {
			copyHorizontal(player, w, 0);
		}

		if (keyBindings.get("up").isPressed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				copyVertical(player, w, 1);
			} else {
				copyHorizontal(player, w, 1);
			}
		}

		if (keyBindings.get("down").isPressed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				copyVertical(player, w, -1);
			} else {
				copyHorizontal(player, w, 3);
			}
		}

		if (event.isCancelable())
			event.setCanceled(true);
	}

	// FIXME
	private void copyHorizontal(EntityClientPlayerMP player, IWorld w, int d) {
		pickManager.clearPicks();
		pickManager.reset(2);
		pickManager.pick(w, 0, 0, 0);
		//int yaw = (int) player.rotationYawHead;
		int yaw = (int) Math.abs(player.rotationYawHead);
		System.out.println("[KeyInputEventHandler.copyHorizontal] yaw=" + yaw);
		//d *= 90;
		//int y = (yaw += (yaw >= 45) ? d + 45 : -d - 45) / 90;
		int y = (yaw += 45) / 90;
		y += d;
		System.out.println("[KeyInputEventHandler.copyHorizontal] y=" + y);
		switch (Math.abs(y % 4)) {
		case 0:
			pickManager.pick(w, -1, 0, 0);
		case 1:
			pickManager.pick(w, 0, 0, 1);
		case 2:
			pickManager.pick(w, 1, 0, 0);
		case 3:
			pickManager.pick(w, 0, 0, -1);
		}
		new SpellCopy(undoManager, selectionManager, pickManager).invoke(w, null);
		pickManager.clearPicks();
	}

	private void copyVertical(EntityClientPlayerMP player, IWorld w, int d) {
		pickManager.clearPicks();
		pickManager.reset(2);
		pickManager.pick(w, 0, 0, 0);
		pickManager.pick(w, 0, d, 0);
		new SpellCopy(undoManager, selectionManager, pickManager).invoke(w, null);
		pickManager.clearPicks();
	}
}
