package ds.plato.pick;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import ds.plato.IWorld;
import ds.plato.common.BlockPick;
import ds.plato.common.Plato;

public class PickManager implements IPick {

	private int maxPicks = 0;
	private final LinkedList<Pick> picks = new LinkedList<>();
	private IWorld world;
	private BlockPick blockPicked;

	public PickManager(int numPicks, IWorld world, BlockPick blockPicked) {
		this.maxPicks = numPicks;
		this.world = world;
		this.blockPicked = blockPicked;
	}

	public PickManager() {
	}

	@Override
	public Pick addPick(int x, int y, int z, Block block) {
		if (picks.size() < maxPicks) {
			Pick p = new Pick(x, y, z, block);
			picks.add(p);
			return p;
		} else {
			return null;
		}
	}

	@Override
	public boolean pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		if (!isFinishedPicking()) {
			Block block = world.getBlock(x, y, z);
			int metatdata = world.getMetadata(x, y, z);
			// TODO pass BlockPick
			world.setBlock(x, y, z, blockPicked, 0, 3);
			// TODO add metatdata to Pick constructor
			addPick(x, y, z, block);
		}
		return isFinishedPicking();
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
	public boolean isPicking() {
		return picks.size() > 0;
	}

	@Override
	public void clear() {
		picks.clear();
	}
	
	@Override
	public void clearPicks() {
		for (Pick p : getPicksArray()) {
			Block block = world.getBlock(p.x, p.y, p.z);
			if (block instanceof BlockPick) {
				world.setBlock(p.x, p.y, p.z, p.block, p.metatdata, 3);
			}
		}
		clear();
	}

	public Pick getPickAt(int x, int y, int z) {
		for (Pick p : picks) {
			if (p.equals(new Pick(x, y, z, null))) {
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
