package ds.plato.spell.draw;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.test.PlatoTest;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public class SpellSphereTest extends PlatoTest {

	@Mock SpellDescriptor sd;
	SlotEntry[] slotEntries;
	Pick[] picks;

	@Before
	public void setUp() {
		super.setUp();
		slotEntries = new SlotEntry[] {new SlotEntry(dirt, 0, 0)};
		picks = new Pick[] {new Pick(0, 0, 0, dirt, 0, 0), new Pick(9, 0, 0, dirt, 0, 0)};
		when(pickManager.isFinishedPicking()).thenReturn(true);
		when(undoManager.newTransaction()).thenReturn(new Transaction(undoManager));
	}

	@Test
	public void doNothing() {
	}

	//@Test
	public void invoke() {
		Spell s = new SpellSphere(undoManager, selectionManager, pickManager);
		//s.invoke(picks, slotEntries);
		s.invoke(world, slotEntries);
		verify(world).setBlock(9, 0, 0, dirt, 0);
	}
}
