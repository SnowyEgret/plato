package ds.plato.spell.test;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import ds.plato.spell.ModifierDescriptor;

public class T_ModifierDescriptor {

	@Test
	public void testToString() {
		ModifierDescriptor d = new ModifierDescriptor(Pair.of("foo", "bar"), Pair.of("a", "b"));
		System.out.println("[T_ModifierDescriptor.testToString] d=" + d);
	}

}
