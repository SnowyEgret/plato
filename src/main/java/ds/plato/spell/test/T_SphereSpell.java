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
import ds.plato.spell.SpellDescriptor;
import ds.plato.spell.SphereSpell;
import ds.plato.test.PlatoTest;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public class T_SphereSpell extends PlatoTest {

	@Mock IWorld world;
	@Mock IUndo um;
	@Mock ISelect sm;
	@Mock IPick pm;
	@Mock SpellDescriptor sd;
	SlotEntry[] slotEntries;
	Pick[] picks;

	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		slotEntries = new SlotEntry[] {new SlotEntry(dirt, 0, 0)};
		picks = new Pick[] {new Pick(0, 0, 0, dirt), new Pick(9, 0, 0, dirt)};
		when(pm.isFinishedPicking()).thenReturn(true);
		when(um.newTransaction()).thenReturn(new Transaction(um));
	}

	@Test
	public void invoke() {
		Spell s = new SphereSpell(sd, um, sm, pm).setWorld(world);
		s.invoke(picks, slotEntries);
		verify(world).setBlock(9, 0, 0, dirt, 0, 3);
	}
}
