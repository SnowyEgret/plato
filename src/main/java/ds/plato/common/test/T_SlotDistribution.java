package ds.plato.common.test;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;

import org.junit.Before;
import org.junit.Test;

import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.test.PlatoTest;

public class T_SlotDistribution extends PlatoTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void test() {
		List<SlotEntry> l = new ArrayList<>();
		l.add(new SlotEntry(dirt, 0, 2));
		l.add(new SlotEntry(sand, 0, 5));
		l.add(new SlotEntry(clay, 0, 7));
		SlotDistribution distribution = new SlotDistribution(l);
		System.out.println("[T_SlotDistribution.test] d=" + distribution);
	}
}
