package ds.plato.spell;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.google.inject.Inject;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.Transformer;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class Spell {

	public SpellDescriptor descriptor;
	protected IWorld world;
	private IUndo undoManager;
	protected ISelect selectionManager;
	private IPick pickManager;

	@Inject
	public Spell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.descriptor = descriptor;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	// Spell can only be partially constructed during FML initialization. The world is only available after the player
	// joins the game.
	public Spell setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	protected void transformSelections(Matrix4d matrix, boolean deleteInitialBlocks) {
		Transaction transaction = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Point3d p = s.getPoint3d();
			matrix.transform(p);
			transaction.add(new SetBlock(world, selectionManager, s));
			if (!transaction.contains(s)) {
				Block b = null;
				int metadata = 0;
				if (deleteInitialBlocks) {
					//TODO replace static reference to Blocks.air
					//TODO set block to previous block instead of air
					transaction.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, Blocks.air, 0).set());
				} else {
					world.setBlock(s.x, s.y, s.z, s.block, s.metadata, 3);
				}
				// Don't want to add to ISelect for now - just to compile
				// selectionManager.removeSelection(s);
			}
		}
		transaction.commit();
	}

	protected void transformSelections(Transformer transformer) {
		Transaction t = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			t.add(new SetBlock(world, selectionManager, transformer.transform(s)).set());
		}
		t.commit();
	}

	//TODO Move to PickManager?
	protected boolean pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		if (!pickManager.isFinishedPicking()) {
			Block block = world.getBlock(x, y, z);
			// TODO commented out for now. PickManager could pick a block like SelectionManager selects a block.
			// blockPick0 could be injected during construction
			// world.setBlock(x, y, z, Plato.blockPick0);
			pickManager.pick(x, y, z, block);
		}
		return pickManager.isFinishedPicking();
	}

	protected Point3d getPick(int i) {
		return pickManager.getPick(i).toDouble();
	}

	public SpellDescriptor getDescriptor() {
		return descriptor;
	}

	// For Staff.addSpell(). Only one spell of each class in spells list
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

	public abstract void encant(PlayerInteractEvent playerInteractEvent);

}
