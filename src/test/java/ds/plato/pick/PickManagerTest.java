package ds.plato.pick;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import ds.plato.test.PlatoTest;

public class PickManagerTest extends PlatoTest {

	PickManager m;

	@Before
	public void setUp() {
		super.setUp();
		m = new PickManager();
		m.reset(3);
	}

	@Test
	public void pick() {
		Pick p1 = m.addPick(0, 0, 0, dirt, 0, 0);
		assertThat(m.getPick(0), is(p1));
	}

	@Test
	public void reset() {
		m.addPick(0, 0, 0, dirt, 0, 0);
		m.reset(3);
		for (int i = 0; i < 6; i++) {
			m.addPick(i, 0, 0, dirt, 0, 0);
		}
		assertThat(m.size(), is(3));
	}

	@Test
	public void getPickAt() {
		Pick p = m.addPick(1, 0, 0, dirt, 0, 0);
		assertThat(m.getPickAt(1, 0, 0), is(p));
	}

	@Test
	public void getPick() {
		Pick p = m.addPick(1, 0, 0, dirt, 0, 0);
		assertThat(m.getPick(0), equalTo(p));
	}

	@Test
	public void isFinishedPicking() {
		m.reset(2);
		m.addPick(1, 0, 0, dirt, 0, 0);
		assertThat(m.isFinishedPicking(), is(false));
		m.addPick(2, 0, 0, dirt, 0, 0);
		assertThat(m.isFinishedPicking(), is(true));
	}

	@Test
	public void getPicksArray() {
		Pick p0 = m.addPick(0, 0, 0, dirt, 0, 0);
		Pick p1 = m.addPick(1, 0, 0, dirt, 0, 0);
		Pick[] picks = m.getPicks();
		assertEquals(p0, picks[0]);
		assertEquals(p1, picks[1]);
	}

}
