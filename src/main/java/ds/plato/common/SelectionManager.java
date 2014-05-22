package ds.plato.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import ds.geom.VoxelSet;
import ds.plato.IWorld;

public class SelectionManager implements ISelect {

	private final Map<Point3i, Selection> selections = new HashMap<>();
	private IWorld world;
	private BlockSelected blockSelected;
	
	//TODO remove when blockSelected is injected throughout.
	public SelectionManager() {
	}

	public SelectionManager(BlockSelected blockSelected) {
		this.blockSelected = blockSelected;
	}

	// World is not available when SelectionManager is constructed. Called when player joins game in ForgeEventHandler
	public SelectionManager setWorld(IWorld world) {
		this.world = world;
		return this;
	}

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

	//For now, only used by UndoableSetBlock in new spell package.
	@Override
	public Selection select(int x, int y, int z) {
		Block prevBlock = world.getBlock(x, y, z);
		int metadata = world.getMetadata(x, y, z);
		world.setBlock(x, y, z, blockSelected, 0, 3);
		Selection s = new Selection(x, y, z, prevBlock, metadata);
		addSelection(s);
		return s;
	}

	@Override
	public void deselect(Selection s) {
		removeSelection(s);
		world.setBlock(s.x, s.y, s.z, s.block, s.metadata, 3);
	}

	@Override
	public Iterable<Point3i> clear() {
		List<Point3i> pointsCleared = new ArrayList<>();
		pointsCleared.addAll(selections.keySet());
		selections.clear();
		//System.out.println("[SelectionManager.clear] selections.size()=" + selections.size());
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
		return o.getClass().getSimpleName()+"@"+Integer.toHexString(o.hashCode());
	}


}
