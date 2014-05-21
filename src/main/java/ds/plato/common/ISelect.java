package ds.plato.common;

public interface ISelect {

	Iterable<Selection> getSelections();

	Selection selectionAt(int x, int y, int z);

	void deselect(int x, int y, int z);
	void select(int x, int y, int z);

}
