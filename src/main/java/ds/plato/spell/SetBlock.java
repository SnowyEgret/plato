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
import ds.plato.undo.IUndoable;

public class SetBlock implements IUndoable {

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
		world.setBlock(x, y, z, block, metadata);

		//FIXME Unit test fails.
		if (block instanceof BlockAir) {
			//We do not want a selection pointing to a newly set air block.
			selectionManager.removeSelection(x, y, z);
		} else {
			selectionManager.select(x, y, z);
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetBlock other = (SetBlock) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public void undo() {
		selectionManager.clearSelections();
		//TODO commented out for now
		// pickManager.clearPicks();
		world.setBlock(x, y, z, prevBlock, prevMetadata);
	}

	@Override
	public void redo() {
		world.setBlock(x, y, z, block, metadata);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetBlock [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append(", block=");
		builder.append(block);
		builder.append(", prevBlock=");
		builder.append(prevBlock);
		builder.append(", metadata=");
		builder.append(metadata);
		builder.append(", prevMetadata=");
		builder.append(prevMetadata);
		builder.append("]");
		return builder.toString();
	}

	private String idOf(Object o) {
		return o.getClass().getSimpleName() + "@" + Integer.toHexString(o.hashCode());
	}

}
