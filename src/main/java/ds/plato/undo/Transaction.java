package ds.plato.undo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ds.plato.common.Selection;
import ds.plato.common.UndoableSetBlock;
import ds.plato.spell.SetBlock;

public class Transaction implements IUndoable, Iterable {

	protected List<IUndoable> undoables = new ArrayList<>();
	private final IUndo undoManager;
	
	public Transaction(IUndo um) {
		this.undoManager = um;
	}

	public void add(IUndoable undoable) {
		undoables.add(undoable);
	}

	@Override
	public void undo() {
		for (IUndoable undoable : undoables) {
			undoable.undo();
		}
	}

	@Override
	public void redo() {
		for (IUndoable undoable : undoables) {
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
		undoManager.addUndoable(this);
	}

	// Creates a dependency on package spell. TODO: Move SetBlock to package undo.
	public boolean contains(Selection s) {
		for (IUndoable u : undoables) {
			//UndoableSetBlock uu = (UndoableSetBlock) u;
			SetBlock uu = (SetBlock) u;
			if (uu.x == s.x && uu.y == s.y && uu.z == s.z ) {
				return true;
			}
		}
		return false;
	}
}