package ds.plato.undo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ds.plato.common.Selection;
import ds.plato.common.UndoableSetBlock;

public class Transaction implements Undoable, Iterable {

	protected List<Undoable> undoables = new ArrayList<>();
	private final UndoManager undoManager;
	
	Transaction(UndoManager undoManager) {
		this.undoManager = undoManager;
	}

	public void add(Undoable undoable) {
		undoables.add(undoable);
	}

	@Override
	public void undo() {
		for (Undoable undoable : undoables) {
			undoable.undo();
		}
	}

	@Override
	public void redo() {
		for (Undoable undoable : undoables) {
			undoable.redo();
		}
	}

	@Override
	public Iterator iterator() {
		return undoables.iterator();
	}

	public boolean isEmpty() {
		return (undoables.size() == 0);
	}

	public void commit() {
		undoManager.add(this);
	}

	// Creates a dependency on package ds.plato.common. TODO: Move ds.undo and ds.selection to ds.plato.common.
	public boolean contains(Selection s) {
		for (Undoable u : undoables) {
			UndoableSetBlock uu = (UndoableSetBlock) u;
			if (uu.x == s.x && uu.y == s.y && uu.z == s.z ) {
				return true;
			}
		}
		return false;
	}
}