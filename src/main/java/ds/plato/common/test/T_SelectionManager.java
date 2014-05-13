package ds.plato.common.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.vecmath.Point3i;

import org.junit.Test;

import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ BlockCactus.class, BlockDirt.class })
//@PowerMockIgnore({ "javax.management.*" })
public class T_SelectionManager {

	@Test
	public void selectionAt() {
		SelectionManager m = new SelectionManager();
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, null, -1);
		m.addSelection(s);
		assertThat(m.selectionAt(p), equalTo(s));
	}

	@Test
	public void addSelection() {
		SelectionManager m = new SelectionManager();
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, null, -1);
		m.addSelection(s);
		assertThat(m.selectionAt(p), equalTo(s));
		m.addSelection(s);
		m.addSelection(new Selection(2, 0, 0, null, 0));
		assertThat(m.size(), is(2));
	}

	@Test
	public void getSelections() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = { new Selection(0, 0, 0, null, -1), new Selection(1, 0, 0, null, -1), new Selection(2, 0, 0, null, -1) };
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		Iterable<Selection> selections = m.getSelections();
		assertThat(selections, hasItems(ss));
		//assertThat(selections, allOf(ss));
	}

	@Test
	public void isSelected() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = { new Selection(0, 0, 0, null, -1), new Selection(1, 0, 0, null, -1), new Selection(2, 0, 0, null, -1) };
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		Iterable<Selection> selections = m.getSelections();
		assertThat(m.isSelected(1, 0, 0), is(true));
	}

	@Test
	public void size() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = { new Selection(0, 0, 0, null, -1), new Selection(1, 0, 0, null, -1), new Selection(2, 0, 0, null, -1) };
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		Iterable<Selection> selections = m.getSelections();
		assertThat(m.size(), is(3));
	}

	@Test
	public void selectedPoints() {
		SelectionManager m = new SelectionManager();
		Point3i[] points = { new Point3i(0, 0, 0), new Point3i(1, 0, 0), new Point3i(2, 0, 0) };
		m.addSelection(new Selection(points[0], null, -1));
		m.addSelection(new Selection(points[1], null, -1));
		m.addSelection(new Selection(points[2], null, -1));
		assertThat(m.selectedPoints(), hasItems(points));
	}

	@Test
	public void removeSelection() {
		SelectionManager m = new SelectionManager();
		Selection[] selections = { new Selection(0, 0, 0, null, -1), new Selection(1, 0, 0, null, -1), new Selection(2, 0, 0, null, -1) };
		m.addSelection(selections[0]);
		m.addSelection(selections[1]);
		m.addSelection(selections[2]);
		assertThat(m.removeSelection(1, 0, 0), is(selections[1]));
		assertThat(m.size(), is(2));
	}

}
