package ds.plato.event;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.Plato;
import ds.plato.core.Direction;
import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.matrix.SpellCopy;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.staff.Staff;
import ds.plato.staff.StaffWood;
import ds.plato.undo.IUndo;

public class KeyInputEventHandler {

	private IUndo undoManager;
	private ISelect selectionManager;
	private IPick pickManager;
	Map<String, KeyBinding> keyBindings = new HashMap<>();


	public KeyInputEventHandler(IUndo undo, ISelect select, IPick pick) {
		this.undoManager = undo;
		this.selectionManager = select;
		this.pickManager = pick;
		// TODO internationalize these strings
		keyBindings.put("undo", registerKeyBinding("Undo", Keyboard.KEY_Z));
		keyBindings.put("redo", registerKeyBinding("Redo", Keyboard.KEY_Y));
		keyBindings.put("toggle", registerKeyBinding("Toggle", Keyboard.KEY_TAB));
		keyBindings.put("delete", registerKeyBinding("Delete", Keyboard.KEY_DELETE));
		keyBindings.put("lastSelection", registerKeyBinding("Last selection", Keyboard.KEY_L));
		keyBindings.put("left", registerKeyBinding("Move left", Keyboard.KEY_LEFT));
		keyBindings.put("right", registerKeyBinding("Move right", Keyboard.KEY_RIGHT));
		keyBindings.put("up", registerKeyBinding("Move up", Keyboard.KEY_UP));
		keyBindings.put("down", registerKeyBinding("Move down", Keyboard.KEY_DOWN));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		Player player = Player.getPlayer();
		IWorld w = player.getWorld();

		if (keyBindings.get("undo").isPressed()) {
			try {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
						undoManager.redo();
					} else {
						undoManager.undo();
					}
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
			ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
			if (stack != null) {
				Item i = stack.getItem();
				if (i instanceof Staff) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
						((Staff) i).previousSpell();
					} else {
						((Staff) i).nextSpell();
					}
				}
				if (i instanceof StaffWood) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
						((StaffWood) i).previousSpell(stack);
					} else {
						((StaffWood) i).nextSpell(stack);
					}
				}
			}
		}

		if (keyBindings.get("delete").isPressed()) {
			new SpellDelete(undoManager, selectionManager, pickManager).invoke(player);
		}

		if (keyBindings.get("lastSelection").isPressed()) {
			selectionManager.reselectLast();
		}

		if (keyBindings.get("left").isPressed()) {
			copy(player, w, -1, 0);
		}

		if (keyBindings.get("right").isPressed()) {
			copy(player, w, 1, 0);
		}

		if (keyBindings.get("up").isPressed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				copyVertical(player, w, 1);
			} else {
				copy(player, w, 0, -1);
			}
		}

		if (keyBindings.get("down").isPressed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				copyVertical(player, w, -1);
			} else {
				copy(player, w, 0, 1);
			}
		}

		if (event.isCancelable())
			event.setCanceled(true);
	}

	private void copy(Player player, IWorld w, int lr, int ud) {
		pickManager.clearPicks();
		pickManager.reset(2);
		pickManager.pick(w, 0, 0, 0, 0);
		Direction direction = player.getDirection();
		System.out.println("[KeyInputEventHandler.copy] direction=" + direction);
		switch (direction) {
		case NORTH:
			pickManager.pick(w, lr, 0, ud, 0);
			break;
		case SOUTH:
			pickManager.pick(w, -lr, 0, -ud, 0);
			break;
		case EAST:
			pickManager.pick(w, -ud, 0, lr, 0);
			break;
		case WEST:
			pickManager.pick(w, ud, 0, -lr, 0);
			break;
		}
		if (selectionManager.size() != 0) {
			new SpellCopy(undoManager, selectionManager, pickManager).invoke(player);
		}
		pickManager.clearPicks();
	}

	private void copyVertical(Player player, IWorld w, int d) {
		pickManager.clearPicks();
		pickManager.reset(2);
		pickManager.pick(w, 0, 0, 0, 0);
		pickManager.pick(w, 0, d, 0, 0);
		new SpellCopy(undoManager, selectionManager, pickManager).invoke(player);
		pickManager.clearPicks();
	}

	private KeyBinding registerKeyBinding(String name, int key) {
		KeyBinding b = new KeyBinding(name, key, Plato.NAME);
		ClientRegistry.registerKeyBinding(b);
		return b;
	}
}
