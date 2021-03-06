package ds.plato.item.spell.other;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import com.google.common.collect.Lists;

import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.core.HotbarSlot;
import ds.plato.item.spell.Spell;
import ds.plato.item.spell.select.Shell;
import ds.plato.pick.Pick;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellHoleDrain extends Spell {

	private Set<Point3i> points = new HashSet<>();
	private int numBlocksDrained = 0;
	private int maxBlocksDrained = 9999;
	private int lastPointsSize = 0;

	public SpellHoleDrain(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	} 

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BTB", "   ", 'T', Items.ghast_tear, 'B', Items.bucket };
	}

	@Override
	public void invoke(IWorld world, HotbarSlot...slotEntries) {
		points.clear();
		numBlocksDrained = 0;
		lastPointsSize = 0;
		Pick pick = pickManager.getPicks()[0];
		// Pick is some block under water. Find the top water block
		int y = pick.y;
		while (true) {
			y++;
			Block b = world.getBlock(pick.x, y, pick.z);
			if (b == Blocks.air) {
				y--;
				break;
			}
		}

		points.add(new Point3i(pick.x, y, pick.z));
		recursivelyDrainWater(world);
		Transaction t = undoManager.newTransaction();
		for (Point3i p : points) {
			t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, Blocks.air, 0).set());
		}
		t.commit();
		pickManager.clearPicks();
		points.clear();

	}

	private void recursivelyDrainWater(IWorld world) {
		// To avoid concurrent modification
		for (Point3i center : Lists.newArrayList(points)) {
			Shell shell = new Shell(Shell.Type.HORIZONTAL, center, world);
			for (Point3i p : shell) {
				Block b = world.getBlock(p.x, p.y, p.z);
				if (b == Blocks.water) {
					numBlocksDrained++;
					points.add(p);
				}
			}
		}
		if (points.size() > lastPointsSize && numBlocksDrained < maxBlocksDrained) {
			lastPointsSize = points.size();
			recursivelyDrainWater(world);
		}
	}

}
