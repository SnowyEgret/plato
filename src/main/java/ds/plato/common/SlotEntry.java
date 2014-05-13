package ds.plato.common;

import net.minecraft.block.Block;


public class SlotEntry {

	Block block;
	int metadata;
	int slotNumber;
	
	public SlotEntry(Block block, int metadata, int slotNumber) {
		super();
		this.block = block;
		this.metadata = metadata;
		this.slotNumber = slotNumber;
	}

	public SlotEntry(Block block) {
		this(block, 0, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + metadata;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + slotNumber;
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
		SlotEntry other = (SlotEntry) obj;
		if (metadata != other.metadata)
			return false;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (slotNumber != other.slotNumber)
			return false;
		return true;
	}
	
}
