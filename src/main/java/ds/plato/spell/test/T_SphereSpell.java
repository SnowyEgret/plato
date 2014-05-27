package ds.plato.spell.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.spell.Spell;
import ds.plato.spell.AbstractSpellDescriptor;
import ds.plato.spell.SphereSpell;
import ds.plato.test.PlatoTest;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public class T_SphereSpell extends PlatoTest {

	@Mock AbstractSpellDescriptor sd;
	SlotEntry[] slotEntries;
	Pick[] picks;

	@Before
	public void setUp() {
		super.setUp();
		slotEntries = new SlotEntry[] {new SlotEntry(dirt, 0, 0)};
		picks = new Pick[] {new Pick(0, 0, 0, dirt), new Pick(9, 0, 0, dirt)};
		when(pickManager.isFinishedPicking()).thenReturn(true);
		when(undoManager.newTransaction()).thenReturn(new Transaction(undoManager));
	}

	@Test
	public void invoke() {
		Spell s = new SphereSpell(undoManager, selectionManager, pickManager, air).setWorld(world);
		s.invoke(picks, slotEntries);
		verify(world).setBlock(9, 0, 0, dirt, 0, 3);
	}
}
