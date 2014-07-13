package ds.plato.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import org.junit.Test;

import ds.plato.core.IO.Group;
import ds.plato.select.Selection;
import ds.plato.test.PlatoTest;

public class IOTest extends PlatoTest {
	
	String filename = "saves/test.json";

	@Test
	public void write() throws IOException {
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(1, 2, 3, dirt, 0));
		selections.add(new Selection(4, 5, 6, dirt, 0));
		IO.writeGroup(new Point3i(0, 0, 0), selections, filename);
	}

	@Test
	public void read() throws FileNotFoundException {
		Group group = IO.readGroup(filename);
		System.out.println("[T_IO.read] group=" + group);
	}

}
