package ds.plato.common;

import java.util.Collection;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public interface ISelect {

	public Iterable<Selection> getSelections();

	public Selection selectionAt(int x, int y, int z);

	public Selection select(int x, int y, int z);

	public void deselect(Selection selection);

	public Iterable<Point3i> clear();

	public Selection removeSelection(Selection s);

	public int size();

	public Collection<? extends Point3i> selectedPoints();
}
