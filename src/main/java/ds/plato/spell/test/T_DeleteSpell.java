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
import ds.plato.spell.AbstractSpellDescriptor;
import ds.plato.spell.SpellDelete;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTest;
import ds.plato.undo.Transaction;

public class T_DeleteSpell extends PlatoTest {

	Spell s;

	@Before
	public void setUp() {
		super.setUp();
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(0, 0, 0, dirt, 0));
		when(selectionManager.getSelections()).thenReturn(selections);
		when(undoManager.newTransaction()).thenReturn(new Transaction(undoManager));
		s = new SpellDelete(undoManager, selectionManager, pickManager, air).setWorld(world);
	}

	//TODO set up mocks
	@Test
	public void invoke() {
		// slotEnties not used
		s.invoke(null);
		verify(world).setBlock(0, 0, 0, air, 0);
	}

	@Test
	public void onClickLeft() {
		s.onClickLeft(new PlayerInteractEvent(null, null, 1, 1, 1, 0));
		verify(selectionManager).select(1, 1, 1);
	}

	// @Test
	// public void onClickRight() {
	// s.onClickRight(new PlayerInteractEvent(null, null, 0, 0, 0, 0));
	// verify(world).setBlock(0, 0, 0, air, 0, 3);
	// }

	@Test
	public void descriptor() {
		AbstractSpellDescriptor d = s.descriptor;
		System.out.println("[T_DeleteSpell.descriptor] d=" + d);
	}

}
