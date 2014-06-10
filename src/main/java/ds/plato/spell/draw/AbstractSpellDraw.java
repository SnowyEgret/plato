package ds.plato.spell.draw;

import javax.vecmath.Point3i;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import ds.plato.Plato;
import ds.plato.common.UndoableSetBlock;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.GeomUtil;
import ds.plato.geom.VoxelSet;
import ds.plato.geom.curve.Line;
import ds.plato.geom.curve.Rectangle;
import ds.plato.geom.solid.Solid;
import ds.plato.geom.surface.Terrain;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public abstract class AbstractSpellDraw extends Spell {

	public AbstractSpellDraw(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(undoManager, selectionManager, pickManager);
	}

	protected void draw(IDrawable drawable, IWorld world, Block block, int metadata) {
		Transaction t = undoManager.newTransaction();
		VoxelSet voxels = drawable.voxelize();
		boolean isHollow = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		if (drawable instanceof Solid && isHollow) {
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
