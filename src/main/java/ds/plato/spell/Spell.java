package ds.plato.spell;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.core.SlotEntry;
import ds.plato.geom.solid.Box;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable, IHoldable {

	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;
	protected String message;
	private int numPicks;

	public Spell(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.numPicks = numPicks;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	public abstract Object[] getRecipe();

	public abstract void invoke(IWorld world, final SlotEntry[] slotEntries);

	public void invoke(Player player) {
		IWorld w = player.getWorldServer();
		SlotEntry[] entries = player.getSlotEntries();
		invoke(w, entries);
	}

	@Override
	public abstract SpellDescriptor getDescriptor();

	@Override
	public Spell getSpell() {
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition e) {

//		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
//		IWorld w = getWorldServer(player);
		Player player = Player.client();
		IWorld w = player.getWorldServer();

		// Standard selection behavior. Shift replaces the current selection set with a region.
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && selectionManager.size() != 0) {
			Point3d lastPointSelected = selectionManager.lastSelection().point3d();
			selectionManager.clearSelections();
			Box b = new Box(lastPointSelected, new Point3d(e.blockX, e.blockY, e.blockZ), false);
			for (Point3i p : b.voxelize()) {
				selectionManager.select(w, p.x, p.y, p.z);
			}

			// Control adds or subtracts a selection to the current selection set
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			Selection s = selectionManager.selectionAt(e.blockX, e.blockY, e.blockZ);
			System.out.println("[Spell.onMouseClickLeft] s=" + s);
			if (s == null) {
				selectionManager.select(w, e.blockX, e.blockY, e.blockZ);
			} else {
				selectionManager.deselect(s);
			}

			// Replaces the current selection set with a selection
		} else {
			selectionManager.clearSelections();
			selectionManager.select(w, e.blockX, e.blockY, e.blockZ);
		}
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition e) {

		// EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		// IWorld w = getWorldServer(player);
		Player player = Player.client();
		IWorld w = player.getWorldServer();
		pickManager.pick(w, e.blockX, e.blockY, e.blockZ);
		if (pickManager.isFinishedPicking()) {
			// SlotEntry[] entries = getSlotEntries(player);
			SlotEntry[] entries = player.getSlotEntries();
			invoke(w, entries);
		}
	}

	@Override
	public boolean isPicking() {
		return pickManager.isPicking();
	}

	@Override
	public void reset() {
		// System.out.println("[Spell.reset] resetting");
		pickManager.clearPicks();
		pickManager.reset(numPicks);
		message = null;
	}

	// For Staff.addSpell(). Only one spell of each type on a staff
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
			return true;
		return false;
	}

	public int getNumPicks() {
		return numPicks;
	}

}
