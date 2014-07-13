package ds.plato.pick;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import net.minecraft.block.Block;
import ds.plato.block.BlockPicked;
import ds.plato.core.IWorld;

public class PickManager implements IPick {

	private int maxPicks = 0;
	private final LinkedList<Pick> picks = new LinkedList<>();
	private IWorld world;
	private Block blockPicked;

	public PickManager(Block blockPicked) {
		this.blockPicked = blockPicked;
	}

//	@Override
//	public IPick setWorld(IWorld world) {
//		this.world = world;
//		return this;
//	}

	@Override
	public void pick(IWorld world, int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		Block block = world.getBlock(x, y, z);
		int metadata = world.getMetadata(x, y, z);
		world.setBlock(x, y, z, blockPicked, 0);
		addPick(x, y, z, block, metadata);
		this.world = world;
	}

	@Override
	public boolean isPicking() {
		return picks.size() > 0 && !isFinishedPicking();
	}

	@Override
	public boolean isFinishedPicking() {
		return (picks.size() == maxPicks);
	}

	@Override
	public Pick[] getPicks() {
		Pick[] array = new Pick[picks.size()];
		return picks.toArray(array);
	}

	@Override
	public void reset(int maxPicks) {
		this.maxPicks = maxPicks;
		picks.clear();
	}

	@Override
	public void clearPicks() {
		System.out.println("[PickManager.clearPicks] getPicks()=" + getPicks());
		for (Pick p : getPicks()) {
			Block block = world.getBlock(p.x, p.y, p.z);
			if (block instanceof BlockPicked) {
				world.setBlock(p.x, p.y, p.z, p.block, p.metadata);
			}
		}
		picks.clear();
	}

	@Override
	public Pick getPickAt(int x, int y, int z) {
		for (Pick p : picks) {
			if (p.equals(new Pick(x, y, z, null, 0))) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Pick lastPick() {
		try {
			return picks.getLast();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PickManager [maxPicks=");
		builder.append(maxPicks);
		builder.append(", picks=");
		builder.append(picks);
		builder.append("]");
		return builder.toString();
	}

	//TODO only used by test class. Make default when test class is in same package.
	public PickManager() {
	}

	//TODO only used by test class. Make default when test class is in same package.
	public Pick getPick(int i) {
		return picks.get(i);
	}

	//TODO only used by test class. Make default when test class is in same package.
	public int size() {
		return picks.size();
	}

	//TODO only used by test class. Make default when test class is in same package.
	public Pick addPick(int x, int y, int z, Block block, int metadata) {
		if (picks.size() < maxPicks) {
			Pick p = new Pick(x, y, z, block, metadata);
			picks.add(p);
			return p;
		} else {
			return null;
		}
	}
}
