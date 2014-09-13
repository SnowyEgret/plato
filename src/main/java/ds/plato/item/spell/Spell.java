package ds.plato.item.spell;

import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import ds.plato.api.IPick;
import ds.plato.api.IPlayer;
import ds.plato.api.ISelect;
import ds.plato.api.ISpell;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.Player;
import ds.plato.core.HotbarSlot;
import ds.plato.core.WorldWrapper;
import ds.plato.geom.solid.Box;
import ds.plato.item.ItemBase;
import ds.plato.select.Selection;

public abstract class Spell extends ItemBase implements ISpell {

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

	// private final String modelPath = "models/" + StringUtils.toCamelCase(getClass());
	// private final ResourceLocation modelLocation = new ResourceLocation(Plato.ID, modelPath + ".obj");
	// private final ResourceLocation modelTextureLocation = new ResourceLocation(Plato.ID, modelPath + ".png");
	// protected IModelCustom model;

	// private boolean hasModel = true;

	public Spell(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.numPicks = numPicks;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
		info = new SpellInfo(this);
		// try {
		// model = AdvancedModelLoader.loadModel(modelLocation);
		// } catch (Exception e) {
		// System.out.print("(No model found at resource location " + modelLocation+")");
		// }
	}

	// public IModelCustom getModel() {
	// return model;
	// }

	// public ResourceLocation getModelTextureResourceLocation() {
	// return modelTextureLocation;
	// }

	// @Override
	// public int getSpriteNumber() {
	// return model == null ? 1 : 0;
	// }

	// public abstract Object[] getRecipe();

	// public boolean hasRecipe() {
	// return getRecipe() != null;
	// }

	@Override
	public void onMouseClickLeft(ItemStack stack, int x, int y, int z, int side) {

		IPlayer player = Player.getPlayer();
		IWorld w = player.getWorld();

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && selectionManager.size() != 0) {
			// Standard selection behavior. Shift replaces the current selection set with a region.
			Point3d lastPointSelected = selectionManager.lastSelection().point3d();
			selectionManager.clearSelections();
			Box b = new Box(lastPointSelected, new Point3d(x, y, z), false);
			for (Point3i p : b.voxelize()) {
				selectionManager.select(w, p.x, p.y, p.z);
			}

		} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			// Control adds or subtracts a selection to the current selection set
			Selection s = selectionManager.selectionAt(x, y, z);
			System.out.println("[Spell.onMouseClickLeft] s=" + s);
			if (s == null) {
				selectionManager.select(w, x, y, z);
			} else {
				selectionManager.deselect(s);
			}

		} else {
			// Replaces the current selection set with a selection
			selectionManager.clearSelections();
			selectionManager.select(w, x, y, z);
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float sx, float sy, float sz) {
		if (!world.isRemote) {
			IWorld w = new WorldWrapper(world);
			pickManager.pick(w, x, y, z, side);
			if (pickManager.isFinishedPicking()) {
				invoke(w, Player.getPlayer().getHotbarSlots());
			}
			return true;
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List rollOver, boolean par4) {
		rollOver.add(info.getDescription());
	}

	// ISpell --------------------------------------------

	@Override
	public abstract void invoke(IWorld world, final HotbarSlot... slotEntries);

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public SpellInfo getInfo() {
		return info;
	}

	@Override
	public int getNumPicks() {
		return numPicks;
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

	// Object -------------------------------------------------------

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
}
