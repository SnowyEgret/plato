package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Plato;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.undo.Undoable;

public class SetBlock implements Undoable {

	IWorld world;
	ISelect selectionManager;
	public final int x, y, z;
	Block block, prevBlock;
	int metadata, prevMetadata;

	public SetBlock(IWorld world, ISelect selectionManager, Selection s) {
		this(world, selectionManager, s.x, s.y, s.z, s.block, s.metadata);
	}

	public SetBlock(IWorld world, ISelect selectionManager, int x, int y, int z, Block block, int metadata) {
		this.world = world;
		this.selectionManager = selectionManager;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		prevBlock = world.getBlock(x, y, z);
		this.metadata = metadata;
		prevMetadata = world.getMetadata(x, y, z);
	}

	public SetBlock set() {

		Selection s = selectionManager.selectionAt(x, y, z);
		if (s != null) {
			prevBlock = s.block;
			prevMetadata = s.metadata;
		}
		world.setBlock(x, y, z, block, metadata, 3);

		//FIXME Unit test fails.
		if (!(block instanceof BlockAir)) {
			selectionManager.select(x, y, z);
		}
		return this;
	}

	@Override
	public void undo() {
		world.setBlock(x, y, z, prevBlock, prevMetadata, 3);
		selectionManager.clear();
		//TODO commented out for now
		// pickManager.clearPicks();
	}

	@Override
	public void redo() {
		world.setBlock(x, y, z, block, metadata, 3);
	}

	@Override
	public String toString() {
		return "SetBlock [world=" + idOf(world) + ", selectionManager=" + idOf(selectionManager) + ", x=" + x
				+ ", y=" + y + ", z=" + z + ", block=" + block + ", prevBlock=" + prevBlock + ", metadata=" + metadata
				+ ", prevMetadata=" + prevMetadata + "]";
	}

	private String idOf(Object o) {
		return o.getClass().getSimpleName() + "@" + Integer.toHexString(o.hashCode());
	}

}
