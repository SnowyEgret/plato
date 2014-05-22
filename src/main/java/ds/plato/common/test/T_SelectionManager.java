package ds.plato.common.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.vecmath.Point3i;

import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.test.PlatoTestFactory;

public class T_SelectionManager extends PlatoTestFactory {

	@Test
	public void selectionAt() {
		SelectionManager m = new SelectionManager();
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, dirt, 0);
		m.addSelection(s);
		assertThat(m.selectionAt(p), equalTo(s));
	}

	@Test
	public void addSelection() {
		SelectionManager m = new SelectionManager();
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, dirt, 0);
		m.addSelection(s);
		assertThat(m.selectionAt(p), equalTo(s));
		m.addSelection(s);
		m.addSelection(new Selection(2, 0, 0, dirt, 0));
		assertThat(m.size(), is(2));
	}

	@Test
	public void getSelections() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = arrayOfThreeSelections();
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		Iterable<Selection> selections = m.getSelections();
		assertThat(selections, hasItems(ss));
		// assertThat(selections, allOf(ss));
	}

	@Test
	public void isSelected() {
		SelectionManager m = new SelectionManager();
		m.addSelection(new Selection(1, 0, 0, dirt, 0));
		assertThat(m.isSelected(1, 0, 0), is(true));
	}

	@Test
	public void size() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = arrayOfThreeSelections();
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		Iterable<Selection> selections = m.getSelections();
		assertThat(m.size(), is(3));
	}

	private Selection[] arrayOfThreeSelections() {
		return new Selection[] { new Selection(0, 0, 0, dirt, 0), new Selection(1, 0, 0, dirt, 0),
				new Selection(2, 0, 0, dirt, 0) };
	}

	@Test
	public void selectedPoints() {
		SelectionManager m = new SelectionManager();
		Point3i[] points = { new Point3i(0, 0, 0), new Point3i(1, 0, 0), new Point3i(2, 0, 0) };
		m.addSelection(new Selection(points[0], dirt, 0));
		m.addSelection(new Selection(points[1], dirt, 0));
		m.addSelection(new Selection(points[2], dirt, 0));
		assertThat(m.selectedPoints(), hasItems(points));
	}

	@Test
	public void removeSelection() {
		SelectionManager m = new SelectionManager();
		Selection[] ss = arrayOfThreeSelections();
		m.addSelection(ss[0]);
		m.addSelection(ss[1]);
		m.addSelection(ss[2]);
		assertThat(m.removeSelection(1, 0, 0), is(ss[1]));
		assertThat(m.size(), is(2));
	}

	@Test
	public void select() {
		SelectionManager m = new SelectionManager();
		m.setWorld(newMockWorld());
		assertThat(m.size(), is(0));
		m.select(1, 2, 3);
		System.out.println("[T_SelectionManager.select] m=" + m);
		assertThat(m.size(), is(1));
		Selection s = m.getSelectionList().get(0);
		assertThat(s.x, equalTo(1));
		assertThat(s.y, equalTo(2));
		assertThat(s.z, equalTo(3));
	}

	@Test
	public void deselect() {
		SelectionManager m = new SelectionManager();
		m.setWorld(newMockWorld());
		Selection s = m.select(1, 2, 3);
		assertThat(m.size(), is(1));
		m.deselect(s);
		assertThat(m.size(), is(0));
	}

	@Test
	public void clear_sizeIsZero() {
		SelectionManager m = new SelectionManager().setWorld(newMockWorld());
		Selection s = m.select(1, 2, 3);
		assertThat(m.size(), is(1));
		m.clear();
		assertThat(m.size(), is(0));
	}

	@Test
	public void clear_returnsClearedSelections() {
		SelectionManager m = new SelectionManager().setWorld(newMockWorld());
		Selection s = m.select(1, 2, 3);
		Iterable<Point3i> clearedSelections = m.clear();
		for (Point3i p : clearedSelections) {
			assertThat(p.x, is(1));
			assertThat(p.y, is(2));
			assertThat(p.z, is(3));
		}
	}

}
