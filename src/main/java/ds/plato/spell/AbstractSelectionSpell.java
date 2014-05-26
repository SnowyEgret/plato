package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;

import org.lwjgl.input.Keyboard;

import ds.plato.common.BlockSelected;
import ds.plato.common.EnumShell;
import ds.plato.common.ISelect;
import ds.plato.common.Shell;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public abstract class AbstractSelectionSpell extends Spell {

	private List<Point3i> grownSelections = new ArrayList<>();

	public AbstractSelectionSpell(
			SpellDescriptor descriptor,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	// TODO grow or shrink when key down.
	protected void growSelections(EnumShell selectionType, Block patternBlock) {
		System.out.println("[AbstractSelectionSpell.growSelections] grownSelections=" + grownSelections);
		if (grownSelections.isEmpty()) {
			grownSelections.addAll(selectionManager.selectedPoints());
			System.out.println("[T_GrowAllSpell.setUp] selectionManager.selectedPoints()=" + selectionManager.selectedPoints());
		}
		List<Point3i> newGrownSelections = new ArrayList();
		for (Point3i center : grownSelections) {
			//TODO pass world to shell
			//Shell shell = new Shell(selectionType, center, world);
			Shell shell = new Shell(selectionType, center, world);
			for (Point3i p : shell) {
				Block block = world.getBlock(p.x, p.y, p.z);
				System.out.println("[AbstractSelectionSpell.growSelections] block=" + block);
				if (!(block instanceof BlockAir) && !(block instanceof BlockSelected)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { // Alt
						selectionManager.select(p.x, p.y, p.z);
						newGrownSelections.add(p);
					} else {
						if (block == patternBlock) {
							selectionManager.select(p.x, p.y, p.z);
							newGrownSelections.add(p);
						}
					}
				}
			}
		}
		grownSelections = newGrownSelections;
	}

	@Override
	public int getNumPicks() {
		return 1;
	}

}
