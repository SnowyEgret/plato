package ds.plato.undo.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import net.minecraft.block.Block;

import org.junit.Before;
import org.junit.Test;

import ds.plato.IWorld;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.select.SelectionManager;
import ds.plato.test.PlatoTest;
import ds.plato.undo.SetBlock;

public class T_SetBlock extends PlatoTest {
	
	IWorld w;
	ISelect sm;
	
	@Before
	public void setUp() {
		super.setUp();
		w = newStubWorld();
		sm = new SelectionManager(blockSelected).setWorld(w);
	}

	@Test
	public void set() {		
		new SetBlock(w, sm, 1, 2, 3, sand, 9).set();
		Block b = w.getBlock(1, 2, 3);
		assertEquals(blockSelected, b);
	}
	
	@Test
	public void set_selectedAfterSet() {		
		new SetBlock(w, sm, 1, 2, 3, sand, 9).set();
		Selection s = sm.selectionAt(1, 2, 3);
		assertEquals(sand, s.block);
	}

	@Test
	public void set_airIsNotSelected() {		
		new SetBlock(w, sm, 1, 2, 3, air, 9).set();
		assertThat("Air is not selected", sm.selectionAt(1, 2, 3), is(nullValue()));
	}

	@Test
	public void undo() {
		SetBlock undoable = new SetBlock(w, sm, 1, 2, 3, sand, 9).set();
		undoable.undo();
		assertEquals(dirt, w.getBlock(1, 2, 3));
	}

	@Test
	public void redo() {
		SetBlock undoable = new SetBlock(w, sm, 1, 2, 3, sand, 9).set();
		undoable.undo();
		undoable.redo();
		assertEquals(sand,  w.getBlock(1, 2, 3));
	}

}
