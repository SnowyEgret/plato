package ds.plato.common.test;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;

public class MockSelectionManager implements ISelect {

	public MockSelectionManager(IWorld mockWorld) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterable<Selection> getSelections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection selectionAt(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deselect(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void select(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

}
