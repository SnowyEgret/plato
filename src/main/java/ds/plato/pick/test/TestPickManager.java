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

public class TestPickManager {

	PickManager m;

	@Before
	public void setUp() throws Exception {
		m = new PickManager();
	}

	@Test
	public void pick() {
		m.reset(3);
		Pick p1 = m.pick(1, 0, 0, null);
		assertThat(m.getPickAt(1,  0,  0), is(p1));
	}

	@Test
	public void reset() {
		m.reset(3);
		int x = 0, y = 0, z = 0;
		Pick p1 = m.pick(x, y, z, null);
		Pick p2 = m.pick(x, y, z, null);
		Pick p3 = m.pick(x, y, z, null);
		Pick p4 = m.pick(x, y, z, null);
		assertThat(p3, is(p3));
		assertThat(p4, is(nullValue()));
	}

	@Test
	public void getPickAt() {
		m.reset(3);
		m.pick(1, 0, 0, null);
		assertThat(m.getPickAt(0, 0, 0), is(nullValue()));
		assertThat(m.getPickAt(1, 0, 0), is(notNullValue()));
	}

	@Test
	public void clear() {
		m.reset(3);
		m.pick(1, 0, 0, null);
		m.pick(2, 0, 0, null);
		m.clear();
		assertThat(m.getPickAt(1, 0, 0), is(nullValue()));
	}

	@Test
	public void getPicks() {
		m.reset(3);
		m.pick(1, 0, 0, null);
		m.pick(2, 0, 0, null);
		assertThat(m.getPickPoints(), hasItems(new Point3d(1, 0, 0), new Point3d(2, 0, 0)));
	}

	@Test
	public void getPick() {
		m.reset(3);
		m.pick(1, 0, 0, null);
		assertThat(m.getPick(0), equalTo(new Pick(1, 0, 0, null)));
	}

	@Test
	public void isFinishedPicking() {
		m.reset(2);
		m.pick(1, 0, 0, null);
		assertThat(m.isFinishedPicking(), is(false));
		m.pick(2, 0, 0, null);
		assertThat(m.isFinishedPicking(), is(true));
	}

}
