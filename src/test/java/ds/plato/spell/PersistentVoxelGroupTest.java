package ds.plato.spell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import org.junit.Test;

import ds.plato.select.Selection;
import ds.plato.test.PlatoTest;

public class PersistentVoxelGroupTest extends PlatoTest {
	
	String filename = "saves/test.json";

	@Test
	public void write() throws IOException {
		List<Selection> selections = new ArrayList<>();
		selections.add(new Selection(1, 2, 3, dirt, 0));
		selections.add(new Selection(4, 5, 6, dirt, 0));
		new PersistentVoxelGroup(new Point3i(0, 0, 0), selections).write(filename);
	}

	@Test
	public void read() throws FileNotFoundException {
		PersistentVoxelGroup group = PersistentVoxelGroup.read(filename);
		System.out.println("[T_IO.read] group=" + group);
	}

}
