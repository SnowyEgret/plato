package ds.plato.spell.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.spell.DeleteSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Staff;

public class T_Staff {

	@Mock PlayerInteractEvent mockedEvent;
	@Mock DeleteSpell mockedDelete;
	@Mock MoveSpell mockedMove;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void onClickRight() {
		Staff staff = new Staff();
		staff.addSpell(mockedDelete);
		staff.onClickRight(mockedEvent);
		verify(mockedDelete).encant(mockedEvent);
	}

	@Test
	public void nextSpell() {
		Staff staff = new Staff();
		staff.addSpell(mockedDelete);
		staff.addSpell(mockedMove);
		assertEquals(mockedMove, staff.nextSpell());
		assertEquals(mockedDelete, staff.nextSpell());
		assertEquals(mockedMove, staff.nextSpell());
	}

	@Test
	public void addSpell() {
		Staff staff = new Staff();
		staff.addSpell(mockedDelete);
		staff.addSpell(mockedMove);
		assertThat(staff.numSpells(), equalTo(2));
		staff.addSpell(mockedMove);
		assertThat(staff.numSpells(), equalTo(2));
	}

}
