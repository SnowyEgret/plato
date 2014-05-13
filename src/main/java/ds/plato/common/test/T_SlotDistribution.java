package ds.plato.common.test;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;

import org.junit.Test;

import ds.plato.common.SlotDistribution;
import ds.plato.common.SlotEntry;

public class T_SlotDistribution {

	@Test
	public void test() {
		List<SlotEntry> l = new ArrayList<>();
		l.add(new SlotEntry(Blocks.dirt, 0, 2));
		l.add(new SlotEntry(Blocks.wool, 0, 5));
		l.add(new SlotEntry(Blocks.clay, 0, 7));
		SlotDistribution distribution = new SlotDistribution(l);
		System.out.println("[T_SlotDistribution.test] d=" + distribution);
	}
}
