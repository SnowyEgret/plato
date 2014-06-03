package ds.plato.spell.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwjgl.input.Keyboard;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SelectionManager;
import ds.plato.common.SlotEntry;
import ds.plato.pick.Pick;
import ds.plato.spell.SpellGrowAll;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Keyboard.class })
@PowerMockIgnore({ "javax.management.*" })
public class T_GrowAllSpell extends PlatoTest {

	private SlotEntry[] slotEntries;
	private Pick[] picks;
	ISelect sm;
	IWorld stubWorld;
	Spell growSpell;

	@Before
	public void setUp() {
		super.setUp();
		PowerMockito.mockStatic(Keyboard.class);
		slotEntries = new SlotEntry[] {new SlotEntry(dirt, 0, 0)};
		picks = new Pick[] {new Pick(0, 0, 0, dirt), new Pick(9, 0, 0, dirt)};
		stubWorld = newStubWorld();
		sm = new SelectionManager(blockSelected).setWorld(stubWorld);
		growSpell = new SpellGrowAll(undoManager, sm, pickManager, air).setWorld(stubWorld);
	}

	@Test
	public void invoke_grow() {
		sm.select(0, 0, 0);
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(false);
		//growSpell.invoke(picks, slotEntries);
		//TODO set up pickManager
		growSpell.invoke(slotEntries);
		assertThat(sm.size(), equalTo(27));
	}

	@Test
	public void invoke_shrink() {
		sm.select(0, 0, 0);
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(false);
		//growSpell.invoke(picks, slotEntries);
		growSpell.invoke(slotEntries);
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(true);
		//growSpell.invoke(picks, slotEntries);
		growSpell.invoke(slotEntries);
		assertThat(sm.size(), equalTo(1));
	}

}
