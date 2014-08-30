package ds.plato.spell;

import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.core.SlotEntry;
import ds.plato.core.StringUtils;
import ds.plato.geom.solid.Box;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.SpellInfo;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable {

	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;
	protected String message;
	private int numPicks;
	protected SpellInfo info;

	protected String CTRL = "ctrl,";
	protected String ALT = "alt,";
	protected String SHIFT = "shift,";
	protected String X = "X,";
	protected String Y = "Y,";
	protected String Z = "Z,";

	protected IModelCustom model;
	private boolean hasModel = true;

	public Spell(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.numPicks = numPicks;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
		info = new SpellInfo(this);
	}

	public abstract Object[] getRecipe();

	public boolean hasRecipe() {
		return getRecipe() != null;
	}

	public abstract void invoke(IWorld world, final SlotEntry... slotEntries);

	public void invoke(Player player) {
		IWorld w = player.getWorld();
		SlotEntry[] entries = player.getSlotEntries();
		invoke(w, entries);
	}

	public String getMessage() {
		return message;
	}

	public int getNumPicks() {
		return numPicks;
	}

	@Override
	public void onMouseClickLeft(ItemStack stack, MovingObjectPosition e) {

		Player player = Player.getPlayer();
		IWorld w = player.getWorld();

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
	public void onMouseClickRight(ItemStack stack, MovingObjectPosition e) {
		Player player = Player.getPlayer();
		IWorld w = player.getWorld();
		int side = e.sideHit;
		pickManager.pick(w, e.blockX, e.blockY, e.blockZ, side);
		if (pickManager.isFinishedPicking()) {
			invoke(w, player.getSlotEntries());
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List rollOver, boolean par4) {
		rollOver.add(info.getDescription());
	}

	public boolean isPicking() {
		return pickManager.isPicking();
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		return builder.toString();
	}

	public SpellInfo getInfo() {
		return info;
	}

	public IModelCustom getModel() {
		if (hasModel && model == null) {
			try {
				return AdvancedModelLoader.loadModel(new ResourceLocation("plato", "models/"
						+ StringUtils.toCamelCase(getClass()) + ".obj"));
			} catch (Exception e) {
				System.out.println("[Spell.getModel] e=" + e);
				hasModel  = false;
				return null;
			}
		}
		return model;
	}
}
