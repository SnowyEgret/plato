package ds.plato.pick;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public class PickManager {

	private int maxPicks = 0;
	private final LinkedList<Pick> picks = new LinkedList<>();

	public PickManager(int numPicks) {
		this.maxPicks = numPicks;
	}

	public PickManager() {
	}

	public void reset(int maxPicks) {
		this.maxPicks = maxPicks;
		picks.clear();
	}

	public Pick pick(int x, int y, int z, Block block) {
		if (picks.size() < maxPicks) {
			Pick p = new Pick(x, y, z, block);
			picks.add(p);
			return p;
		} else
			return null;
	}

	public Pick getPickAt(int x, int y, int z) {
		for (Pick p : picks) {
			if (p.equals(new Pick(x, y, z, null))) {
				return p;
			}
		}
		return null;
	}

	public void clear() {
		picks.clear();
	}
	
	public Iterable<Pick> getPicks() {
		return picks;
	}

	public List<Point3d> getPickPoints() {
		List<Point3d> l = new ArrayList<>();
		for (Pick p : picks) {
			l.add(new Point3d(p.x, p.y, p.z));
		}
		return l;
	}

	public boolean isFinishedPicking() {
		return (picks.size() == maxPicks);
	}

	public Pick getPick(int i) {
		return picks.get(i);
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

	public boolean isPicking() {
		return picks.size() > 0;
	}
}
