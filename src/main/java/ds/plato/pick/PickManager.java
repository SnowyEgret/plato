package ds.plato.pick;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import ds.plato.Plato;
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

	public PickManager() {
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

	@Override
	public IPick setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	@Override
	public Pick addPick(int x, int y, int z, Block block, int metadata) {
		if (picks.size() < maxPicks) {
			Pick p = new Pick(x, y, z, block, metadata);
			picks.add(p);
			return p;
		} else {
			return null;
		}
	}

	@Override
	public void pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		Block block = world.getBlock(x, y, z);
		int metadata = world.getMetadata(x, y, z);
		world.setBlock(x, y, z, blockPicked, 0);
		addPick(x, y, z, block, metadata);
	}

	@Override
	public boolean isPicking() {
		return picks.size() > 0 && !isFinishedPicking();
	}

	@Override
	public boolean isFinishedPicking() {
		return (picks.size() == maxPicks);
	}

	public Pick getPick(int i) {
		return picks.get(i);
	}

	@Override
	public Pick[] getPicksArray() {
		Pick[] array = new Pick[picks.size()];
		return picks.toArray(array);
	}

	@Override
	public void reset(int maxPicks) {
		this.maxPicks = maxPicks;
		picks.clear();
	}

	@Override
	public void clear() {
		picks.clear();
	}

	@Override
	public void clearPicks() {
		for (Pick p : getPicksArray()) {
			Block block = world.getBlock(p.x, p.y, p.z);
			if (block instanceof BlockPicked) {
				world.setBlock(p.x, p.y, p.z, p.block, p.metadata);
			}
		}
		clear();
	}

	public Pick getPickAt(int x, int y, int z) {
		for (Pick p : picks) {
			if (p.equals(new Pick(x, y, z, null, 0))) {
				return p;
			}
		}
		return null;
	}

	public Iterable<Pick> getPicks() {
		return picks;
	}

	public List<Point3d> getPickPoints3d() {
		List<Point3d> l = new ArrayList<>();
		for (Pick p : picks) {
			l.add(new Point3d(p.x, p.y, p.z));
		}
		return l;
	}

	public Pick getPickAt(Point3i p) {
		return getPickAt(p.x, p.y, p.z);
	}

	public int size() {
		return picks.size();
	}

	public Pick lastPick() {
		try {
			return picks.getLast();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
