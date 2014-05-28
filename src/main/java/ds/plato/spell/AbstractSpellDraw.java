package ds.plato.spell;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import ds.geom.Drawable;
import ds.geom.GeomUtil;
import ds.geom.Line;
import ds.geom.Rectangle;
import ds.geom.Solid;
import ds.geom.Terrain;
import ds.geom.VoxelSet;
import ds.plato.common.ISelect;
import ds.plato.common.Plato;
import ds.plato.common.SlotEntry;
import ds.plato.common.UndoableSetBlock;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellDraw extends Spell {

	public AbstractSpellDraw(AbstractSpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	protected void draw(Drawable drawable, Block block, int metadata, boolean hollow) {
		Transaction t = undoManager.newTransaction();
		VoxelSet voxels = drawable.voxelize();
		if (drawable instanceof Solid && hollow) {
			voxels = voxels.shell();
		}
		//TODO Change all geom classes for y is up
		for (Point3i p : voxels) {
			//SlotEntry entry = Plato.getBlocksWithMetadataInIventorySlots().get(0);
			t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, block, metadata).set());
		}
		t.commit();
	}

}
