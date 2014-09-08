package ds.plato.select;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.vecmath.Point3i;

import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.api.IWorld;
import ds.plato.core.WorldWrapper;
import ds.plato.test.PlatoTest;

public class SelectionManagerTest extends PlatoTest {
	
	SelectionManager m;
	IWorld stubWorld;
	
	@Before
	public void setUp() {
		stubWorld = newStubWorld();
		m = new SelectionManager(blockSelected);
		//m.setWorld(w);
	}

	@Test
	public void selectionAt() {
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, dirt, 0);
		m.addSelection(s);
		assertThat(m.selectionAt(p.x, p.y, p.z), equalTo(s));
	}

	@Test
	public void addSelection() {
		Point3i p = new Point3i(1, 0, 0);
		Selection s = new Selection(p, dirt, 0);
		m.addSelection(s);
		assertThat(m.selectionAt(p.x, p.y, p.z), equalTo(s));
		m.addSelection(s);
		m.addSelection(new Selection(2, 0, 0, dirt, 0));
		assertThat(m.size(), is(2));
	}

//	@Test
//	public void getSelections() {
//		Selection[] ss = arrayOfThreeSelections();
//		m.addSelection(ss[0]);
//		m.addSelection(ss[1]);
//		m.addSelection(ss[2]);
//		Iterable<Selection> selections = m.getSelections();
//		assertThat(selections, hasItems(ss));
//		// assertThat(selections, allOf(ss));
//	}

	@Test
	public void isSelected() {
		m.addSelection(new Selection(1, 0, 0, dirt, 0));
		assertThat(m.isSelected(1, 0, 0), is(true));
	}

//	@Test
//	public void size() {
//		Selection[] ss = arrayOfThreeSelections();
//		m.addSelection(ss[0]);
//		m.addSelection(ss[1]);
//		m.addSelection(ss[2]);
//		Iterable<Selection> selections = m.getSelections();
//		assertThat(m.size(), is(3));
//	}

//	@Test
//	public void selectedPoints() {
//		Point3i[] points = { new Point3i(0, 0, 0), new Point3i(1, 0, 0), new Point3i(2, 0, 0) };
//		m.addSelection(new Selection(points[0], dirt, 0));
//		m.addSelection(new Selection(points[1], dirt, 0));
//		m.addSelection(new Selection(points[2], dirt, 0));
//		assertThat(m.selectedPoints(), hasItems(points));
//	}

//	@Test
//	public void removeSelection() {
//		Selection[] ss = arrayOfThreeSelections();
//		m.addSelection(ss[0]);
//		m.addSelection(ss[1]);
//		m.addSelection(ss[2]);
//		assertThat(m.removeSelection(1, 0, 0), is(ss[1]));
//		assertThat(m.size(), is(2));
//	}

	@Test
	public void select() {
		assertThat(m.size(), is(0));
		m.select(stubWorld, 1, 2, 3);
		System.out.println("[T_SelectionManager.select] m=" + m);
		assertThat(m.size(), is(1));
		Selection s = m.getSelectionList().get(0);
		assertThat(s.x, equalTo(1));
		assertThat(s.y, equalTo(2));
		assertThat(s.z, equalTo(3));
	}

	// TODO fails. This is a bug in the test. blockSelected is null.
	@Test
	public void select_worldSetsBlocktoBlockSelected() {
		m.select(stubWorld, 1, 2, 3);
		assertEquals(blockSelected, stubWorld.getBlock(1,  2,  3));
	}

	@Test
	public void deselect() {
		m.select(stubWorld, 1, 2, 3);
		assertThat(m.size(), is(1));
		m.deselect(1, 2, 3);
		assertThat(m.size(), is(0));
	}

	@Test
	public void deselect_worldResetsBlock() {
		m.select(stubWorld, 1, 2, 3);
		m.deselect(1, 2, 3);
		assertEquals(dirt, stubWorld.getBlock(1, 2, 3));
	}

//	@Test
//	public void clear_sizeIsZero() {
//		Selection s = m.select(1, 2, 3);
//		assertThat(m.size(), is(1));
//		m.clear();
//		assertThat(m.size(), is(0));
//	}
//
//	@Test
//	public void clear_returnsClearedSelections() {
//		Selection s = m.select(1, 2, 3);
//		Iterable<Point3i> clearedSelections = m.clear();
//		for (Point3i p : clearedSelections) {
//			assertThat(p.x, is(1));
//			assertThat(p.y, is(2));
//			assertThat(p.z, is(3));
//		}
//	}

//	private Selection[] arrayOfThreeSelections() {
//		return new Selection[] { new Selection(0, 0, 0, dirt, 0), new Selection(1, 0, 0, dirt, 0),
//				new Selection(2, 0, 0, dirt, 0) };
//	}

}
