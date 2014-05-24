package ds.plato.spell.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.pick.Pick;
import ds.plato.pick.PickManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Staff;
import ds.plato.test.PlatoTestFactory;

public class T_Staff extends PlatoTestFactory {

	@Mock PlayerInteractEvent mockedEvent;
	@Mock DeleteSpell mockedDelete;
	@Mock MoveSpell mockedMove;
	@Mock PickManager mockPickManager;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Pick[] picks = new Pick[] { new Pick(1, 1, 1, dirt), new Pick(2, 2, 2, dirt) };
		when(mockPickManager.getPicksArray()).thenReturn(picks);
		when(mockPickManager.isFinishedPicking()).thenReturn(true);
	}

	//TODO check this
	@Test
	public void onClickRight() {
		Staff staff = new Staff(mockPickManager);
		staff.addSpell(mockedDelete);
		staff.onClickRight(mockedEvent);
		verify(mockedDelete).invoke(mockPickManager.getPicksArray());
	}

	@Test
	public void nextSpell() {
		Staff staff = new Staff(mockPickManager);
		staff.addSpell(mockedDelete);
		staff.addSpell(mockedMove);
		assertEquals(mockedMove, staff.nextSpell());
		assertEquals(mockedDelete, staff.nextSpell());
		assertEquals(mockedMove, staff.nextSpell());
	}

	@Test
	public void addSpell() {
		Staff staff = new Staff(mockPickManager);
		staff.addSpell(mockedDelete);
		staff.addSpell(mockedMove);
		assertThat(staff.numSpells(), equalTo(2));
		staff.addSpell(mockedMove);
		assertThat(staff.numSpells(), equalTo(2));
	}

}
