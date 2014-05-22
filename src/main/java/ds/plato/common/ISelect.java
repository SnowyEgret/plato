package ds.plato.common;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;

public interface ISelect {

	public Iterable<Selection> getSelections();

	public Selection selectionAt(int x, int y, int z);

	public Selection select(int x, int y, int z);

	public void deselect(Selection selection);

	public Iterable<Point3i> clear();
}
