package ds.plato.common;

import java.util.List;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.geom.IDrawable;
import ds.plato.geom.GeomUtil;
import ds.plato.geom.VoxelSet;
import ds.plato.geom.curve.Line;
import ds.plato.geom.curve.Rectangle;
import ds.plato.geom.solid.Solid;
import ds.plato.geom.surface.Terrain;
import ds.plato.pick.Pick;
import ds.plato.pick.PickManager;
import ds.plato.select.Selection;
import ds.plato.undo.Transaction;

@Deprecated
public abstract class Stick extends Item implements IToggleable {

	private World world;
	private final PickManager pickManager;
	Property initialState;
	EnumToggler state;

	public Stick(int numPicks, Property initialState,
			Class<? extends Enum> enumClass) {
		pickManager = new PickManager();
		pickManager.reset(numPicks);
		this.initialState = initialState;
		state = new EnumToggler(enumClass, initialState.getInt());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		String s = Plato.ID + ":" + getUnlocalizedName().substring(5); // Removes
																		// "tile."
		this.itemIcon = registry.registerIcon(s);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		if (!w.isRemote) {
			MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
			if (position.typeOfHit == MovingObjectType.MISS) {
				clearPicks();
				Plato.solidStick.firstPour = true;
			}
		}
		return is;
	}

	public void clearPicks() {
		for (Pick p : pickManager.getPicks()) {
			Block block = getWorld().getBlock(p.x, p.y, p.z);
			// if (block instanceof BlockPick || block instanceof BlockPick1 ||
			// block instanceof BlockPick2)
			if (block == Plato.blockPicked) {
				// TODO set metadata
				getWorld().setBlock(p.x, p.y, p.z, p.block);
			}
		}
		pickManager.clear();
	}

	public void resetPickManager(int numPicks) {
		pickManager.reset(numPicks);
	}

	protected boolean pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		if (!pickManager.isFinishedPicking()) {
			// Reads a horizontal displacement and sets it to vertical
			// TODO Replace with a class which prints x, z, and y displacements
			// to Menu and allows key selection of any
			// of three.
			if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
				Pick lastPick = pickManager.lastPick();
				int dx = Math.abs(x - lastPick.x);
				int dz = Math.abs(z - lastPick.z);
				int d = (dx >= dz) ? dx : dz;
				x = lastPick.x;
				z = lastPick.z;
				y += d;
			}
			Block block = getWorld().getBlock(x, y, z);

			// int currentPick = pickManager.size();
			// Block pickblock = null;
			// if (currentPick == 0)
			// pickblock = MOD.blockPick0;
			// else if (currentPick == 1)
			// pickblock = MOD.blockPick1;
			// else if (currentPick == 2)
			// pickblock = MOD.blockPick2;
			// else
			// throw new
			// IllegalStateException("Longer than expected pick list. currentPick="
			// + currentPick);

			// if (block != Blocks.air)
			// TODO Add metadata to class Pick and set metadata (reverse in
			// clearPicks)
			getWorld().setBlock(x, y, z, Plato.blockPicked);
			pickManager.addPick(x, y, z, block);
		}
		return pickManager.isFinishedPicking();
	}

	protected Iterable<Pick> getPicks() {
		return pickManager.getPicks();
	}

	protected List<Point3d> getPickPoints() {
		return pickManager.getPickPoints3d();
	}

	protected Point3d getPick(int i) {
		return pickManager.getPick(i).toDouble();
	}

	public Pick getPickAt(Point3i p) {
		return pickManager.getPickAt(p);
	}

//	public Pick getPickAt(int x, int y, int z) {
//		return pickManager.getPickAt(x, y, z);
//	}

	public Pick lastPick() {
		return pickManager.lastPick();
	}

	protected World getWorld() {
		if (world == null) {
			world = Plato.getWorldServer();
		}
		return world;
	}

	protected void transformSelections(Matrix4d matrix,
			boolean deleteInitialBlocks) {
		World w = getWorld();
		Transaction transaction = Plato.undoManager.newTransaction();
		for (Selection s : Plato.selectionManager.getSelections()) {
			Point3d p = s.getPoint3d();
			matrix.transform(p);
			transaction.add(new UndoableSetBlock(p, s.block, s.metadata));
			if (!transaction.contains(s)) {
				Block b = null;
				int metadata = 0;
				if (deleteInitialBlocks) {
					transaction.add(new UndoableSetBlock(s.x, s.y, s.z,
							Blocks.air, 0));
				} else {
					w.setBlock(s.x, s.y, s.z, s.block);
					w.setBlockMetadataWithNotify(s.x, s.y, s.z, s.metadata, 3);
				}
				Plato.selectionManager.removeSelection(s);
			}
		}
		transaction.commit();
	}

	protected void transformSelections(ITransformer transformer) {
		Transaction t = Plato.undoManager.newTransaction();
		for (Selection s : Plato.selectionManager.getSelections()) {
			t.add(new UndoableSetBlock(transformer.transform(s)));
		}
		t.commit();
	}

	protected void draw(IDrawable drawable, boolean hollow) {
		Transaction t = Plato.undoManager.newTransaction();
		VoxelSet voxels = drawable.voxelize();
		if (hollow) {
			voxels = voxels.shell();
		}
		Plato.log.info("[ItemStick.draw] drawable=" + drawable);
		Plato.log.info("[ItemStick.draw] voxels=" + voxels);
		// Minecraft y axis is up. Not needed for Solid - don't understand why.
		if (!(drawable instanceof Solid) && !(drawable instanceof Terrain)
				&& !(drawable instanceof Line)
				&& !(drawable instanceof Rectangle)) {
			voxels = voxels.transform(GeomUtil.newRotX270Matrix(drawable
					.getOrigin()));
			// voxels =
			// voxels.transform(GeomUtil.newRotX90Matrix(drawable.getOrigin()));
			// voxels =
			// voxels.transform(GeomUtil.newRotX180Matrix(drawable.getOrigin()));
		}
		for (Point3i p : voxels) {
			SlotEntry entry = Plato.getBlocksWithMetadataInIventorySlots().get(
					0);
			t.add(new UndoableSetBlock(p, entry.block, entry.metadata));
		}
		t.commit();
	}

	protected void onClickLeft(PlayerInteractEvent e) {
		if (e.isCancelable())
			e.setCanceled(true);
	}

	// Picks cleared in onItemRightClick() instead
	protected void onClickRightAir(PlayerInteractEvent e) {
		System.out.println("[ItemStick.onClickRightAir] e=" + e);
	}

	protected abstract void onClickRight(PlayerInteractEvent e);

	// public abstract void printCurrentState();

	public void toggle() {
		// state = (Toggleable) state.next();
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			state.previous();
		} else {
			state.next();
		}
		resetPickManager(state.getNumPicks());
		printCurrentState();
	}

	public void setInitialState(Property initialState) {
		this.initialState = initialState;
	}

	// public abstract void save();
	// TODO For now print to console.
	public void printCurrentState() {
		Plato.log.info("[ItemStickEdit.printCurrentState] state="
				+ state.current());
		System.out.println(state.getDescription());
	}

	public void save() {
		Plato.log.info("[ItemStickEdit.save] state.ordinal()="
				+ state.current().ordinal());
		// initialState.set(state.getOrdinal());
		initialState.set(state.current().ordinal());
	}

	public boolean isPicking() {
		return pickManager.isPicking();
	}

}
