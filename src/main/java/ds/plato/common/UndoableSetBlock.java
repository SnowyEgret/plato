package ds.plato.common;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import ds.plato.undo.Undoable;

public class UndoableSetBlock implements Undoable {
	public final int x, y, z, prevMetadata, metadata;
	Block prevBlock, block;
	final World world;

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
		this.world = Plato.getWorldServer();
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.prevBlock = world.getBlock(x, y, z);
		this.prevMetadata = world.getBlockMetadata(x, y, z);
		this.metadata = metadata;

		Selection s = Plato.selectionManager.selectionAt(x, y, z);
		if (s != null) {
			prevBlock = s.block;
			Plato.selectionManager.removeSelection(x, y, z);
		}
		if (block == Blocks.air) {
			world.setBlock(x, y, z, block);
		} else {
			world.setBlock(x, y, z, Plato.blockSelected);
			world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
			Plato.selectionManager.addSelection(new Selection(x, y, z, block, metadata));
		}
	}

	@Override
	public void undo() {
		world.setBlock(x, y, z, prevBlock);
		world.setBlockMetadataWithNotify(x, y, z, prevMetadata, 3);
		Plato.clearSelections();
		Plato.clearPicks();
	}

	@Override
	public void redo() {
		world.setBlock(x, y, z, block);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
	}

}
