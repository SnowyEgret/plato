package ds.plato.spell.test;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Spell;
import ds.plato.spell.Staff;
import ds.plato.test.PlatoTest;

public class T_Staff extends PlatoTest {

	@Mock PlayerInteractEvent mockEvent;
	@Mock DeleteSpell mockDelete;
	@Mock MoveSpell mockMove;
	@Mock IPick mockPickManager;
	Staff staff;

	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		Pick[] picks = new Pick[] { new Pick(1, 1, 1, dirt), new Pick(2, 2, 2, dirt) };
		when(mockPickManager.getPicksArray()).thenReturn(picks);
		when(mockPickManager.isFinishedPicking()).thenReturn(true);
		staff = new Staff(mockPickManager);
		staff.addSpell(mockDelete);
		staff.addSpell(mockMove);
		System.out.println("[T_Staff.setUp] staff=" + staff);
	}

	@Test
	public void nextSpell_startsAtBeginningWhenReachesEnd() {
		assertEquals(mockMove, staff.nextSpell());
		assertEquals(mockDelete, staff.nextSpell());
		assertEquals(mockMove, staff.nextSpell());
	}

	@Test
	public void onClickRight() {
		staff.onClickRight(mockEvent);
		//verify(mockedDelete).invoke(mockPickManager.getPicksArray(), null);
		verify(mockDelete).onClickRight(mockEvent);
		staff.nextSpell();
		staff.onClickRight(mockEvent);
		//verify(mockedMove).invoke(mockPickManager.getPicksArray(), null);
		verify(mockMove).onClickRight(mockEvent);
	}

	@Test
	public void nextSpell_pickMangerIsReset() {
		Spell s = staff.nextSpell();
		verify(mockPickManager).reset(s.getNumPicks());
		s = staff.nextSpell();
		verify(mockPickManager, times(2)).reset(s.getNumPicks());
	}

	@Test
	public void addSpell_spellNotAddedTwice() {
		assertThat(staff.numSpells(), equalTo(2));
		staff.addSpell(mockMove);
		assertThat(staff.numSpells(), equalTo(2));
	}

}
