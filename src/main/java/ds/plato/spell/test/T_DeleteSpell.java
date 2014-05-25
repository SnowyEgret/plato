package ds.plato.spell.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTest;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public class T_DeleteSpell extends PlatoTest {

	// This is an integration test
	// IWorld w;
	// IUndo undoManager;
	// ISelect selectionManager;
	// PickManager pickManager;
	// SpellDescriptor descriptor;
	//
	// @Override
	// public void setUp() {
	// super.setUp();
	// w = newMockWorld();
	// undoManager = new UndoManager();
	// selectionManager = new SelectionManager(blockSelected).setWorld(w);
	// pickManager = new PickManager(1, blockPicked);
	// descriptor = new DeleteSpellDescriptor();
	// w.setBlock(1, 2, 3, dirt, 0, 3);
	// Transaction t = undoManager.newTransaction();
	// t.add(new SetBlock(w, selectionManager, 1, 2, 3, dirt, 0));
	// t.commit();
	// System.out.println("[T_DeleteSpell.encant] selectionManager=" + selectionManager);
	// }

	@Mock IWorld world;
	@Mock IUndo um;
	@Mock ISelect sm;
	@Mock IPick pm;
	@Mock DeleteSpellDescriptor sd;

	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(0, 0, 0, dirt, 0));
		when(sm.getSelections()).thenReturn(selections);
		when(um.newTransaction()).thenReturn(new Transaction(um));
	}

	@Test
	public void invoke() {
		Spell s = new DeleteSpell(sd, um, sm, pm, air).setWorld(world);
		//slotEnties not used
		s.invoke(new Pick[] {}, null);
		verify(world).setBlock(0, 0, 0, air, 0, 3);
	}
}
