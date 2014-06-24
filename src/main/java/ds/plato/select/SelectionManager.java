package ds.plato.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import ds.plato.block.BlockSelected;
import ds.plato.core.IWorld;
import ds.plato.geom.VoxelSet;

public class SelectionManager implements ISelect {

	private final Map<Point3i, Selection> selections = new HashMap<>();
	private IWorld world;
	private Block blockSelected;
	private List<Point3i> lastSelections;

	public SelectionManager(Block blockSelected) {
		this.blockSelected = blockSelected;
	}

	// World is not available when SelectionManager is constructed. Called when player joins game in ForgeEventHandler
	public ISelect setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	// Returns a copy to avoid concurrent modification
	@Override
	public Iterable<Selection> getSelections() {
		List<Selection> l = new ArrayList<>();
		l.addAll(selections.values());
		return l;
	}

	@Override
	public Selection selectionAt(int x, int y, int z) {
		return selectionAt(new Point3i(x, y, z));
	}

	// For now, only used by UndoableSetBlock in new spell package.
	@Override
	public Selection select(int x, int y, int z) {
		Block prevBlock = world.getBlock(x, y, z);
		int metadata = world.getMetadata(x, y, z);
		world.setBlock(x, y, z, blockSelected, 0);
		Selection s = new Selection(x, y, z, prevBlock, metadata);
		addSelection(s);
		return s;
	}

	@Override
	public void deselect(Selection s) {
		removeSelection(s);
		world.setBlock(s.x, s.y, s.z, s.block, s.metadata);
	}

	@Override
	public Iterable<Point3i> clear() {
		List<Point3i> pointsCleared = new ArrayList<>();
		pointsCleared.addAll(selections.keySet());
		selections.clear();
		// System.out.println("[SelectionManager.clear] selections.size()=" + selections.size());
		return pointsCleared;
	}

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

	public VoxelSet voxelSet() {
		return new VoxelSet(selections.keySet());
	}

	public List<Selection> getSelectionList() {
		List<Selection> l = new ArrayList<>();
		l.addAll(selections.values());
		return l;
	}

	@Override
	public String toString() {
		return "SelectionManager [world=" + idOf(world) + ", selections=" + selections + "]";
	}

	private String idOf(Object o) {
		return o.getClass().getSimpleName() + "@" + Integer.toHexString(o.hashCode());
	}

	@Override
	public void clearSelections() {
		lastSelections = Lists.newArrayList(selectedPoints());
		System.out.println("[SelectionManager.clearSelections] lastSelections=" + lastSelections);
		for (Selection s : getSelections()) {
			deselect(s);
		}
	}

	@Override
	public Selection lastSelection() {
		if (selections.isEmpty()) {
			return null;
		}
		return getSelectionList().get(selections.size()-1);
	}

	@Override
	public void reselectLast() {
		System.out.println("[SelectionManager.reselectLast] lastSelections=" + lastSelections);
		for(Point3i p : lastSelections) {
			select(p.x, p.y, p.z);
		}
	}

}
