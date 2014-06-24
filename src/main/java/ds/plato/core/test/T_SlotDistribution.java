package ds.plato.core.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;

import org.junit.Before;
import org.junit.Test;

import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.test.PlatoTest;

public class T_SlotDistribution extends PlatoTest {
	
	@Test
	public void test() {
		SlotEntry[] l = new SlotEntry[3];
		l[0] = new SlotEntry(dirt, 0, 2);
		l[1] = new SlotEntry(sand, 0, 5);
		l[2] = new SlotEntry(clay, 0, 7);
		SlotDistribution d = new SlotDistribution(l);
		int sum = 0;
		for (Integer p : d.getPecentages()) {
			sum += p;
		}
		System.out.println("[T_SlotDistribution.test] d=" + d);
		assertThat(sum, equalTo(100));
	}
}
