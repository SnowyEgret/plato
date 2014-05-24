package ds.plato.pick.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import ds.plato.pick.Pick;
import ds.plato.pick.PickManager;
import ds.plato.test.PlatoTest;

public class T_PickManager extends PlatoTest {

	PickManager m;

	@Before
	public void setUp() {
		super.setUp();
		m = new PickManager();
	}

	@Test
	public void pick() {
		m.reset(3);
		Pick p1 = m.pick(0, 0, 0, dirt);
		assertThat(m.getPick(0), is(p1));
	}

	@Test
	public void reset() {
		m.reset(3);
		m.pick(0, 0, 0, dirt);
		m.reset(3);
		for (int i = 0; i < 6; i++) {
			m.pick(i, 0, 0, dirt);
		}
		assertThat(m.size(), is(3));
	}

	@Test
	public void getPickAt() {
		m.reset(3);
		Pick p = m.pick(1, 0, 0, dirt);
		assertThat(m.getPickAt(1, 0, 0), is(p));
	}

	@Test
	public void clear() {
		m.reset(3);
		m.pick(1, 0, 0, dirt);
		m.pick(2, 0, 0, dirt);
		m.clear();
		assertThat(m.size(), is(0));
	}

	@Test
	public void getPickPoints() {
		m.reset(3);
		Pick p1 = m.pick(1, 0, 0, dirt);
		Pick p2 = m.pick(2, 0, 0, dirt);
		assertThat(m.getPickPoints3d(), hasItems(p1.toDouble(), p2.toDouble()));
	}

	@Test
	public void getPick() {
		m.reset(3);
		Pick p = m.pick(1, 0, 0, dirt);
		assertThat(m.getPick(0), equalTo(p));
	}

	@Test
	public void isFinishedPicking() {
		m.reset(2);
		m.pick(1, 0, 0, dirt);
		assertThat(m.isFinishedPicking(), is(false));
		m.pick(2, 0, 0, dirt);
		assertThat(m.isFinishedPicking(), is(true));
	}

}
