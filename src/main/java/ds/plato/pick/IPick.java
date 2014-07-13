package ds.plato.pick;

import javax.vecmath.Point3i;

import ds.plato.core.IWorld;
import net.minecraft.block.Block;

public interface IPick {

	public void pick(IWorld world, int x, int y, int z);

	public void clearPicks();

	public Pick[] getPicks();

	public boolean isFinishedPicking();

	public boolean isPicking();

	public Pick getPickAt(int x, int y, int z);

	public void reset(int numPicks);

//	public IPick setWorld(IWorld world);

	public Pick lastPick();
}
