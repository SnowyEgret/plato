package ds.plato.common.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockDirt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.common.IO;
import ds.plato.common.Selection;
import ds.plato.common.IO.Group;
import ds.plato.test.PlatoTest;

public class T_IO extends PlatoTest {
	
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
