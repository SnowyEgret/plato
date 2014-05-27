package ds.plato.pick;

import net.minecraft.block.Block;

public interface IPick {

	public boolean pick(int x, int y, int z);

	public void clearPicks();

	//TODO rename to getPicks and remove getPicks returns list from pickManager when migrating to staffs and spells
	public Pick[] getPicksArray();

	public boolean isFinishedPicking();

	public boolean isPicking();

	public void reset(int numPicks);

	//TODO remove when migrating to staffs and spells
	@Deprecated
	public Pick addPick(int x, int y, int z, Block block);

	//TODO remove when migrating to staffs and spells
	@Deprecated
	public void clear();
}
