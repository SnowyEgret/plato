package ds.plato.undo;


public interface IUndo {
	
	public Transaction newTransaction();

	public void addUndoable(Undoable undoable);

}
