package ds.plato.pick;

import net.minecraft.block.Block;

public interface IPick {

	public boolean isFinishedPicking();

	public Pick getPick(int i);

	public void pick(int x, int y, int z, Block block);
}
