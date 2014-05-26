package ds.plato.common;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.geom.Box;
import ds.plato.WorldWrapper;

public class StickSelection extends Stick {

	private List<Point3i> expandedSelections = new ArrayList<>();

	public StickSelection(Property initialState) {
		super(2, initialState, EnumShell.class);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p) {
		if (!w.isRemote) {
			MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
			if (position.typeOfHit == MovingObjectType.MISS) {
				clearSelections();
			}
		}
		return is;
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {

		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
			// if (state == state.BOX) {
			if (pick(e.x, e.y, e.z)) {
				Box b = new Box(getPick(0), getPick(1));
				clearPicks();
				for (Point3i p : b.voxelize()) {
					selectBlockAt(p);
				}
				if (e.isCancelable())
					e.setCanceled(true);
			}
			return;
		}

		Point3i p = new Point3i(e.x, e.y, e.z);

		if (Plato.selectionManager.isSelected(p)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				Selection s = Plato.selectionManager.selectionAt(p);
				expandSelections((EnumShell)state.current(), s.block);
			} else {
				deselectBlockAt(p);
			}
		} else {
			selectBlockAt(p);
		}
		System.out.println("[ItemStickSelection.onClickRight] num selections:" + Plato.selectionManager.size());

		if (e.isCancelable())
			e.setCanceled(true);
	}

	@Override
	public void printCurrentState() {
		Plato.log.info("[ItemStickSelection.printCurrentState] SelectionStick: state=" + state.current() + " numSelections="
				+ Plato.selectionManager.size());
	}

	public Iterable<Point3i> clearSelections() {
		World w = getWorld();
		for (Selection s : Plato.selectionManager.getSelections()) {
			Block block = w.getBlock(s.x, s.y, s.z);
			// A selection can be pointing to a block which is not a BlockSelected
			if (block instanceof BlockSelected) {
				w.setBlock(s.x, s.y, s.z, s.block);
				w.setBlockMetadataWithNotify(s.x, s.y, s.z, s.metadata, 3);
			}
		}
		Iterable<Point3i> clearedPoints = Plato.selectionManager.clear();
		expandedSelections.clear();
		return clearedPoints;
	}

	private void expandSelections(EnumShell type) {
		expandSelections(type, Blocks.air);
	}

	private void expandSelections(EnumShell selectionType, Block patternBlock) {
		List<Point3i> newExpandedSelections = new ArrayList();
		if (expandedSelections.isEmpty()) {
			expandedSelections.addAll(Plato.selectionManager.selectedPoints());
		}
		for (Point3i center : expandedSelections) {
			Shell shell = new Shell(selectionType, center, new WorldWrapper(Plato.getWorldServer()));
			for (Point3i p : shell) {
				Block block = getWorld().getBlock(p.x, p.y, p.z);
				if (!(block == Blocks.air) && !(block == Plato.blockSelected)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { // Alt
						selectBlockAt(p);
						newExpandedSelections.add(p);
					} else {
						if (block == patternBlock) {
							selectBlockAt(p);
							newExpandedSelections.add(p);
						}
					}
				}
			}
		}
		expandedSelections = newExpandedSelections;
	}

	private void selectBlockAt(Point3i p) {
		Block prevBlock = getWorld().getBlock(p.x, p.y, p.z);
		int metadata = getWorld().getBlockMetadata(p.x, p.y, p.z);
		getWorld().setBlock(p.x, p.y, p.z, Plato.blockSelected);
		Plato.selectionManager.addSelection(new Selection(p, prevBlock, metadata));
	}

	private void deselectBlockAt(Point3i p) {
		Selection s = Plato.selectionManager.removeSelection(p);
		System.out.println("[StickSelection.deselectBlockAt] s=" + s);
		getWorld().setBlock(p.x, p.y, p.z, s.block);
		getWorld().setBlockMetadataWithNotify(p.x, p.y, p.z, s.metadata, 3);
	}
}
