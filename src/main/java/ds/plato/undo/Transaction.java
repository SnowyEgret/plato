package ds.plato.undo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ds.plato.select.Selection;

public class Transaction implements IUndoable, Iterable {

	// TODO Maybe this should be a set
	//protected List<IUndoable> undoables = new ArrayList<>();
	protected Set<IUndoable> undoables = new HashSet<>();
	private final IUndo undoManager;

	public Transaction(IUndo undoManager) {
		this.undoManager = undoManager;
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

	public boolean contains(Selection s) {
		return undoables.contains(s);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [undoManager=");
		builder.append(undoManager);
		builder.append(", undoables=");
		builder.append(undoables);
		builder.append("]");
		return builder.toString();
	}
}