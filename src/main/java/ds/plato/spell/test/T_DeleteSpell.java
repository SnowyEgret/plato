package ds.plato.spell.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.WorldWrapper;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.pick.PickManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.Transaction;
import ds.plato.undo.UndoManager;

public class T_DeleteSpell extends PlatoTestFactory {

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
	
	@Mock WorldWrapper world;
	@Mock UndoManager um;
	@Mock SelectionManager sm;
	@Mock PickManager pm;
	@Mock DeleteSpellDescriptor sd;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(1, 2, 3, dirt, 0));
		when(sm.getSelections()).thenReturn(selections);
		when(um.newTransaction()).thenReturn(new Transaction(um));
//		selectionManager.addSelection(new Selection(1, 2, 3, dirt, 0));
//		System.out.println("[T_DeleteSpell.setUp] selection=" + selectionManager.getSelectionList().get(0));
	}


	@Test
	public void encant() {
		// Spell s = new DeleteSpell(descriptor, undoManager, selectionManager, pickManager).setWorld(w);
		Spell s = new DeleteSpell(sd, um, sm, pm, air).setWorld(world);
		s.encant(new PlayerInteractEvent(null, null, 1, 2, 3, 0));
		verify(world).setBlock(1, 2, 3, air, 0, 3);
	}
}
