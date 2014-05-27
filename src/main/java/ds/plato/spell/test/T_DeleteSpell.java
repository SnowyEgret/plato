package ds.plato.spell.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;

import ds.plato.common.Selection;
import ds.plato.pick.Pick;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTest;
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
	Spell s;

	@Before
	public void setUp() {
		super.setUp();
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(0, 0, 0, dirt, 0));
		when(selectionManager.getSelections()).thenReturn(selections);
		when(undoManager.newTransaction()).thenReturn(new Transaction(undoManager));
		s = new DeleteSpell(undoManager, selectionManager, pickManager, air).setWorld(world);
	}

	@Test
	public void invoke() {
		// slotEnties not used
		s.invoke(new Pick[] {}, null);
		verify(world).setBlock(0, 0, 0, air, 0, 3);
	}

	@Test
	public void onClickLeft() {
		s.onClickLeft(new PlayerInteractEvent(null, null, 1, 1, 1, 0));
		verify(selectionManager).select(1,1,1);
	}

//	@Test
//	public void onClickRight() {
//		s.onClickRight(new PlayerInteractEvent(null, null, 0, 0, 0, 0));
//		verify(world).setBlock(0, 0, 0, air, 0, 3);
//	}
}
