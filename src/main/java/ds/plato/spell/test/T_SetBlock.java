package ds.plato.spell.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.spell.SetBlock;
import ds.plato.test.PlatoTestFactory;

public class T_SetBlock extends PlatoTestFactory {

	@Test
	public void set() {		
		IWorld w = newStubWorld();
		SelectionManager sm = new SelectionManager(blockSelected).setWorld(w);		
		new SetBlock(w, sm, 1, 2, 3, dirt, 9).set();
		Block b = w.getBlock(1, 2, 3);
		assertThat("Block is set", w.getBlock(1, 2, 3), is(notNullValue()));
		assertEquals(blockSelected, b);
		Selection s = sm.selectionAt(1, 2, 3);
		assertThat(s.block.getClass().getName(), equalTo(dirt.getClass().getName()));
	}
	
	@Test
	public void set_selectedAfterSet() {		
		IWorld w = newStubWorld();
		SelectionManager sm = new SelectionManager(blockSelected).setWorld(w);		
		new SetBlock(w, sm, 1, 2, 3, dirt, 9).set();
		Selection s = sm.selectionAt(1, 2, 3);
		assertEquals(dirt, s.block);
	}

	@Test
	public void set_airIsNotSelected() {		
		IWorld w = newStubWorld();
		SelectionManager sm = new SelectionManager(blockSelected).setWorld(w);		
		new SetBlock(w, sm, 1, 2, 3, air, 9).set();
		Selection s = sm.selectionAt(1, 2, 3);
		assertThat("Air is not selected", s, is(nullValue()));
	}

	@Test
	public void undo() {
		IWorld w = newStubWorld();
		SelectionManager sm = new SelectionManager(blockSelected).setWorld(w);		
		SetBlock undoable = new SetBlock(w, sm, 1, 2, 3, dirt, 9).set();
		undoable.undo();
		assertThat("Block is no longer set", w.getBlock(1, 2, 3), is(nullValue()));
	}

	@Test
	public void redo() {
		IWorld w = newStubWorld();
		SelectionManager sm = new SelectionManager(blockSelected).setWorld(w);		
		SetBlock undoable = new SetBlock(w, sm, 1, 2, 3, dirt, 9).set();
		undoable.undo();
		undoable.redo();
		assertEquals(dirt,  w.getBlock(1, 2, 3));
	}

}
