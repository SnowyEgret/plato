package ds.plato.common;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import ds.plato.undo.IUndoable;

public class UndoableSetBlock implements IUndoable {
	
	World world;
	ISelect selectionManager;
	public final int x, y, z;
	Block block, prevBlock;
	int metadata, prevMetadata;

	// Started refactoring for dependency injection. Created this constructor with world and selectionManager parameters.
	// Only used by new spell package.
	// UndoableSetBlock(int x, int y, int z, Block block, int metadata) calls this constructor with Plato's world and selectionManager
	public UndoableSetBlock(World world, ISelect selectionManager, int x, int y, int z, Block block, int metadata) {
		
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
			selectionManager.removeSelection(x, y, z);
		}

		if (block == Blocks.air) {
			world.setBlock(x, y, z, block);
		} else {
			world.setBlock(x, y, z, Plato.blockSelected);
			world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
			selectionManager.addSelection(new Selection(x, y, z, block, metadata));
		}
	}
	
	public UndoableSetBlock(Point3i p, Block block, int metadata) {
		this(p.x, p.y, p.z, block, metadata);
	}

	public UndoableSetBlock(Point3d p, Block block, int metadata) {
		this((int) p.x, (int) p.y, (int) p.z, block, metadata);
	}

	public UndoableSetBlock(Selection s) {
		this(s.x, s.y, s.z, s.block, s.metadata);
	}

	public UndoableSetBlock(int x, int y, int z, Block block, int metadata) {
		this(Plato.getWorldServer(), Plato.selectionManager, x, y, z, block, metadata);
	}

	public UndoableSetBlock(World w, SelectionManager selectionManager, Selection s) {
		this(w, selectionManager, s.x, s.y, s.z, s.block, s.metadata);
	}

	@Override
	public void undo() {
		world.setBlock(x, y, z, prevBlock);
		world.setBlockMetadataWithNotify(x, y, z, prevMetadata, 3);
		//TODO move this to SelectionManager. 
		Plato.clearSelections();
		Plato.clearPicks();
	}

	@Override
	public void redo() {
		world.setBlock(x, y, z, block);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}

}
