package ds.plato.spell.descriptor;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class ModifierDescriptorTest {

	@Test
	public void testToString() {
		ModifierDescriptor d = new ModifierDescriptor("foo, bar", "a,b");
		System.out.println("[T_ModifierDescriptor.testToString] d=" + d);
	}

}
