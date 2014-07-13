package ds.plato.spell;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwjgl.input.Keyboard;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.spell.matrix.SpellCopy;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.test.PlatoTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Keyboard.class })
@PowerMockIgnore({ "javax.management.*" })
public class StaffTest extends PlatoTest {

	@Mock PlayerInteractEvent mockEvent;
	@Mock SpellDelete mockDelete;
	@Mock SpellCopy mockMove;
	Staff staff;

	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(Keyboard.class);
		Pick[] picks = new Pick[] { new Pick(1, 1, 1, dirt, 0), new Pick(2, 2, 2, dirt, 0) };
		when(pickManager.getPicks()).thenReturn(picks);
		when(pickManager.isFinishedPicking()).thenReturn(true);
		staff = new Staff(property, pickManager);
		staff.addSpell(mockDelete);
		staff.addSpell(mockMove);
	}

	@Test
	public void nextSpell_setsCurrentSpell() {
		staff.nextSpell();
		assertEquals(mockMove, staff.currentSpell());
		staff.nextSpell();
		assertEquals(mockDelete, staff.currentSpell());
	}

	@Test
	public void nextSpell_startsAtBeginningWhenReachesEnd() {
		assertEquals(mockMove, staff.nextSpell());
		assertEquals(mockDelete, staff.nextSpell());
		assertEquals(mockMove, staff.nextSpell());
	}

	@Test
	public void nextSpell_pickManagerResetToNumPicksOfSpell() {
		assertEquals(mockMove, staff.nextSpell());
		verify(pickManager).reset(mockMove.getNumPicks());
		assertEquals(mockDelete, staff.nextSpell());
		verify(pickManager, times(2)).reset(mockDelete.getNumPicks());
	}

	@Test
	public void previousSpell_startsAtEndWhenReachesBeginning() {
		assertEquals(mockMove, staff.previousSpell());
		assertEquals(mockDelete, staff.previousSpell());
		assertEquals(mockMove, staff.previousSpell());
	}

//	@Test
//	public void onClickRight() {
//		staff.onClickRight(mockEvent);
//		//verify(mockedDelete).invoke(mockPickManager.getPicksArray(), null);
//		verify(mockDelete).onClickRight(mockEvent);
//		staff.nextSpell();
//		staff.onClickRight(mockEvent);
//		//verify(mockedMove).invoke(mockPickManager.getPicksArray(), null);
//		verify(mockMove).onClickRight(mockEvent);
//	}

	@Test
	public void nextSpell_pickMangerIsReset() {
		Spell s = staff.nextSpell();
		verify(pickManager).reset(s.getNumPicks());
		s = staff.nextSpell();
		verify(pickManager, times(2)).reset(s.getNumPicks());
	}

	@Test
	public void addSpell_spellNotAddedTwice() {
		assertThat(staff.numSpells(), equalTo(2));
		staff.addSpell(mockMove);
		assertThat(staff.numSpells(), equalTo(2));
	}

	@Test
	public void toggle() {
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(true);
		staff.toggle();
		assertEquals(mockMove, staff.currentSpell());
		staff.toggle();
		assertEquals(mockDelete, staff.currentSpell());
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(false);
		staff.toggle();
		assertEquals(mockMove, staff.currentSpell());
		staff.toggle();
		assertEquals(mockDelete, staff.currentSpell());

	}

	// @Test
	// public void pick() {
	// staff.pick(0, 0, 0);
	// when(pickManager.isFinishedPicking()).thenReturn(false);
	// verify(world).setBlock(0, 0, 0, blockPicked, 0, 3);
	// }

}
