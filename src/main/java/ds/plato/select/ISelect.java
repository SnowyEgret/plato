package ds.plato.select;

import java.util.Collection;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import ds.plato.core.IWorld;
import ds.plato.geom.VoxelSet;
import net.minecraft.block.Block;

public interface ISelect {

	public Iterable<Selection> getSelections();

	public Selection selectionAt(int x, int y, int z);

	public Selection select(int x, int y, int z);

	public void deselect(Selection selection);

	public void clearSelections();

	public Selection removeSelection(Selection s);

	public int size();

	public Collection<? extends Point3i> selectedPoints();

	public boolean isSelected(int x, int y, int z);

	public ISelect setWorld(IWorld world);
	
	public Selection lastSelection();
	
	// TODO methods below can be removed when migrating to spells and staffs. Used by package common.

	public VoxelSet voxelSet();

	public List<Selection> getSelectionList();

	public void addSelection(Selection selection);

	public Iterable<Point3i> clear();

	public Selection removeSelection(Point3i p);

	public Selection removeSelection(int x, int y, int z);

}
