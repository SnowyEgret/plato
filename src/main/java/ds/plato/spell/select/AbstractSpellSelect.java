package ds.plato.spell.select;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.block.BlockSelected;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.undo.IUndo;

public abstract class AbstractSpellSelect extends Spell {

	private List<Point3i> grownSelections = new ArrayList<>();
	protected Shell.Type shellType;
	protected Item ingredientA = Items.feather;
	protected Item ingredientB = Items.dye;

	public AbstractSpellSelect(Shell.Type type, IUndo undo, ISelect select, IPick pick) {
		super(1, undo, select, pick);
		this.shellType = type;
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		pickManager.clearPicks();
		if (selectionManager.size() != 0) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				shrinkSelections(shellType, world);
			} else {
				// Is this really the first block? getSelections gets the values from a map.
				Block firstBlockSelected = selectionManager.getSelections().iterator().next().block;
				growSelections(shellType, world, firstBlockSelected);
			}
		}
	}

	protected void growSelections(Shell.Type shellType, IWorld world, Block patternBlock) {
		if (grownSelections.isEmpty()) {
			grownSelections.addAll(selectionManager.selectedPoints());
		}
		List<Point3i> newGrownSelections = new ArrayList();
		for (Point3i center : grownSelections) {
			Shell shell = new Shell(shellType, center, world);
			for (Point3i p : shell) {
				Block block = world.getBlock(p.x, p.y, p.z);
				if (!(block instanceof BlockAir) && !(block instanceof BlockSelected)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) { // Alt
						selectionManager.select(world, p.x, p.y, p.z);
						newGrownSelections.add(p);
					} else {
						if (block == patternBlock) {
							selectionManager.select(world, p.x, p.y, p.z);
							newGrownSelections.add(p);
						}
					}
				}
			}
		}
		grownSelections = newGrownSelections;
	}

	protected void shrinkSelections(Shell.Type shellType, IWorld world) {
		List<Selection> shrunkSelections = new ArrayList<>();
		for (Selection s : selectionManager.getSelections()) {
			Shell shell = new Shell(shellType, s.point3i(), world);
			for (Point3i p : shell) {
				Block b = world.getBlock(p.x, p.y, p.z);
				if (!(b instanceof BlockSelected)) {
					shrunkSelections.add(s);
					break;
				}

			}
		}
		for (Selection s : shrunkSelections) {
			selectionManager.deselect(s);
		}
		grownSelections.clear();
	}

	public void clearGrownSelections() {
		grownSelections.clear();
	}

}
