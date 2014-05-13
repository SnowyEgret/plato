package ds.plato.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3i;

import net.minecraft.world.World;
import ds.geom.VoxelSet;

public class SelectionManager {

	private final Map<Point3i, Selection> selections = new HashMap<>();

	public Selection selectionAt(int x, int y, int z) {
		return selectionAt(new Point3i(x, y, z));
	}

//	public Selection selectionAt(Point3d p) {
//		return selectionAt(new Point3i((int) p.x, (int) p.y, (int) p.z));
//	}

	public Selection selectionAt(Point3i p) {
		return selections.get(p);
	}

	public void addSelection(Selection s) {
		selections.put(s.getPoint3i(), s);
	}

	public void addSelections(Iterable<Selection> selections) {
		for (Selection s : selections) {
			addSelection(s);
		}
	}

	public Iterable<Point3i> clear() {
		List<Point3i> pointsCleared = new ArrayList<>();
		pointsCleared.addAll(selections.keySet());
		selections.clear();
		Plato.log.info("[SelectionManager.clear] selections.size()=" + selections.size());
		return pointsCleared;
	}

//	public Iterable<Selection> block() {
//		List<Selection> l = new ArrayList<>();
//		l.addAll(selections.values());
//		return l;
//	}

	public boolean isSelected(Point3i p) {
		return selections.containsKey(p);
	}

	public boolean isSelected(int x, int y, int z) {
		return isSelected(new Point3i(x, y, z));
	}

	public int size() {
		return selections.size();
	}

	public Collection<Point3i> selectedPoints() {
		return selections.keySet();
	}

	public Selection removeSelection(Point3i p) {
		return selections.remove(p);
	}

	public Selection removeSelection(int x, int y, int z) {
		return removeSelection(new Point3i(x, y, z));
	}

	public Selection removeSelection(Selection s) {
		return removeSelection(s.getPoint3i());
	}

	public void deselect(Selection s) {
		removeSelection(s);
		World w = Plato.getWorldServer();
		w.setBlock(s.x, s.y, s.z, s.block);
		w.setBlockMetadataWithNotify(s.x, s.y, s.z, s.metadata, 3);
	}

	public VoxelSet voxelSet() {
		//return new VoxelSet(selections.values());
		return new VoxelSet(selections.keySet());
	}

	public Iterable<Selection> getSelections() {
		List<Selection> l = new ArrayList<>();
		l.addAll(selections.values());
		return l;
	}

	public List<Selection> getSelectionList() {
		List<Selection> l = new ArrayList<>();
		l.addAll(selections.values());
		return l;
	}
}
