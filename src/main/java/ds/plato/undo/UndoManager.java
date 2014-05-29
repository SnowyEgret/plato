package ds.plato.undo;

import java.util.NoSuchElementException;

public class UndoManager implements IUndo {

	private Node currentNode;
	int maxLength = 0;

	public UndoManager(int maxLength) {
		currentNode = new Node();
		this.maxLength = maxLength;
	}

	public UndoManager() {
		this(50);
	}

	public void addUndoable(IUndoable undoable) {
		Node node = new Node(undoable);
		currentNode.right = node;
		node.left = currentNode;
		currentNode = node;
		if (size() > maxLength)
			removeLeftEnd();
	}

	public void redo() {
		if (currentNode.right == null)
			throw new NoSuchElementException("Nothing left to redo.");
		currentNode = currentNode.right;
		currentNode.undoable.redo();
	}

	public void undo() {
		if (currentNode.left == null) {
			throw new NoSuchElementException("Nothing left to undo.");
		}
		currentNode.undoable.undo();
		currentNode = currentNode.left;
	}

	public int size() {
		int size = 0;
		Node n = currentNode;
		while (n.right != null) {
			n = n.right;
			size++;
		}
		n = currentNode;
		while (n.left != null) {
			n = n.left;
			size++;
		}
		return size;
	}

	public void clear() {
		currentNode = new Node();
	}

	public void removeLeftEnd() {
		Node n = currentNode;
		while (n.left != null) {
			n = n.left;
		}
		n = n.right;
		n.left = null;
	}

	private class Node {

		private Node left = null;
		private Node right = null;
		private final IUndoable undoable;

		public Node(IUndoable undoable) {
			this.undoable = undoable;
		}

		public Node() {
			undoable = null;
		}
	}

	public Transaction newTransaction() {
		return new Transaction(this);
	}
}
