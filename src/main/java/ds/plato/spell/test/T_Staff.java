package ds.plato.spell.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ DeleteSpell.class, MoveSpell.class })
//@PowerMockIgnore({ "javax.management.*" })
public class T_Staff {

	@Mock PlayerInteractEvent mockedEvent;
	@Mock DeleteSpell mockedDelete;
	@Mock MoveSpell mockedMove;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		// mockedDelete = PowerMockito.mock(DeleteSpell.class);
		// mockedMove = PowerMockito.mock(MoveSpell.class);
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
		// assertThat(staff.nextSpell(), equalTo(mockedMove));
		assertThat(staff.nextSpell().getClass().getName(), equalTo(mockedMove.getClass().getName()));
		assertThat(staff.nextSpell().getClass().getName(), equalTo(mockedDelete.getClass().getName()));
		assertThat(staff.nextSpell().getClass().getName(), equalTo(mockedMove.getClass().getName()));
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
