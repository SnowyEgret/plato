package ds.plato;

import net.minecraft.block.Block;

public interface IWorld {

	public void setBlock(int x, int y, int z, Block block);
	public void setBlockMetadataWithNotify(int x, int y, int z, int metadata, int mode);
	public Block getBlock(int x, int y, int z);
	public int getBlockMetadata(int x, int y, int z);

}
