package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Plato;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.undo.Undoable;

public class UndoableSetBlock implements Undoable {

	IWorld world;
	ISelect selectionManager;
	public final int x, y, z;
	Block block, prevBlock;
	int metadata, prevMetadata;

	public UndoableSetBlock(IWorld world, ISelect selectionManager, Selection s) {
		this(world, selectionManager, s.x, s.y, s.z, s.block, s.metadata);
	}

	public UndoableSetBlock(IWorld world, ISelect selectionManager, int x, int y, int z, Block block, int metadata) {
		this.world = world;
		this.selectionManager = selectionManager;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.prevBlock = world.getBlock(x, y, z);
		this.metadata = metadata;
		this.prevMetadata = world.getBlockMetadata(x, y, z);

		Selection s = selectionManager.selectionAt(x, y, z);
		if (s != null) {
			prevBlock = s.block;
			//TODO selectionManager.deselect
			//selectionManager.removeSelection(x, y, z);
		}

		if (block == Blocks.air) {
			world.setBlock(x, y, z, block);
		} else {
			world.setBlock(x, y, z, Plato.blockSelected);
			world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
			//TODO selectionManager.select
			//selectionManager.addSelection(new Selection(x, y, z, block, metadata));
		}
	}

	@Override
	public void undo() {
		world.setBlock(x, y, z, prevBlock);
		world.setBlockMetadataWithNotify(x, y, z, prevMetadata, 3);
		// TODO move this to SelectionManager.
		//selectionManager.clearSelections();
		//pickManager.clearPicks();
	}

	@Override
	public void redo() {
		world.setBlock(x, y, z, block);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}

}
