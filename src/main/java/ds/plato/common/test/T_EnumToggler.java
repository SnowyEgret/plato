package ds.plato.common.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import ds.plato.common.EnumToggler;
import ds.plato.common.Menu;

public class T_EnumToggler {
	
	static Menu b = new Menu("b");
	
	public enum EnumTest {
		A(0, new Menu("a")), B(1, b), C(2, new Menu("c"));
		public int numPicks;
		public Menu description;
		EnumTest(int i, Menu description) {
			this.numPicks = i;
			this.description = description;
		}
	}

	@Test
	public void constructor1() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		assertThat(t.current().name(), equalTo("A"));
	}

	@Test
	public void constructor2() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class, 1);
		assertThat(t.current().name(), equalTo("B"));
	}

	@Test
	public void next() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		assertThat(t.next().name(), equalTo("B"));
		assertThat(t.next().name(), equalTo("C"));
	}

	@Test
	public void previous() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		assertThat(t.next().name(), equalTo("B"));
		assertThat(t.next().name(), equalTo("C"));
		assertThat(t.previous().name(), equalTo("B"));
	}

	@Test
	public void current() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		assertThat(t.next().name(), equalTo("B"));
		assertThat((EnumTest) t.current(), equalTo(EnumTest.values()[1]));
		assertThat(t.current().name(), equalTo("B"));
	}

	@Test
	public void set() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		t.set(1);
		assertThat(t.current().name(), equalTo("B"));
	}

	@Test
	public void getNumPicks() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		t.next();
		assertThat(t.getNumPicks(), equalTo(1));
	}

	@Test
	public void getDescription() throws Exception {
		EnumToggler t = new EnumToggler(EnumTest.class);
		t.next();
		assertThat(t.getDescription().toString(), equalTo(b.toString()));
	}

}
