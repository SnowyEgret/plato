package ds.plato.undo.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.Transaction;
import ds.plato.undo.UndoManager;
import ds.plato.undo.Undoable;

public class T_UndoManager extends PlatoTestFactory {

	String state = null;
	UndoManager m;

	@Before
	public void setUp() {
		super.setUp();
		m = new UndoManager();
	}

	@Test
	public void size() {
		m.clear();
		createTransactions(3);	
		assertThat(m.size(), equalTo(3));
	}

	@Test
	public void maxSize() {
		m = new UndoManager(3);
		createTransactions(5);	
		assertThat(m.size(), equalTo(3));
	}

	@Test
	public void removeLeftEnd() {
		m.clear();
		createTransactions(5);
		m.removeLeftEnd();
		assertThat(m.size(), equalTo(4));
	}

	@Test
	public void undo() {
		m.clear();
		createTransactions(1);
		m.undo();
		assertThat(state, equalTo("Undone"));
	}

	@Test
	public void redo() {
		m.clear();
		createTransactions(1);
		m.undo();
		m.redo();
		assertThat(state, equalTo("Redone"));
	}

	@Test
	public void redoThrows() {
		m.clear();
		createTransactions(1);
		try {
			m.redo();
		} catch (Exception e) {
			assertTrue(e instanceof NoSuchElementException);
		}
	}

	@Test
	public void undoThrows() {
		m.clear();
		createTransactions(3);
		m.undo();
		m.undo();
		m.undo();
		try {
			m.undo();
		} catch (Exception e) {
			assertTrue(e instanceof NoSuchElementException);
		}
	}

	@Test
	public void add() {
		m.clear();
		createTransactions(5);
		assertThat(m.size(), equalTo(5));
	}

	@Test
	public void addToMiddle() {
		m.clear();
		createTransactions(5);
		m.undo();
		m.undo();
		m.undo();
		createTransactions(1);
		assertThat(m.size(), equalTo(3));
	}

	@Test
	public void clear() {
		m = new UndoManager();
		createTransactions(3);
		m.clear();
		assertThat(m.size(), equalTo(0));
	}

	private void createTransactions(int numTransactions) {
		for (int i = 0; i < numTransactions; i++) {
			Transaction t = m.newTransaction();
			t.add(new TestUndoable());
			t.commit();
		}
	}

	private class TestUndoable implements Undoable {

		@Override
		public void undo() {
			state = "Undone";
		}

		@Override
		public void redo() {
			state = "Redone";
		}
	}

}
