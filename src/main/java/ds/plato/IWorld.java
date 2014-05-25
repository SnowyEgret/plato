package ds.plato;

import net.minecraft.block.Block;

public interface IWorld {

	public void setBlock(int x, int y, int z, Block block, int metadata, int mode);

	public Block getBlock(int x, int y, int z);

	public int getMetadata(int x, int y, int z);

}
