package ds.plato.spell.test;

import org.junit.Test;

import ds.plato.spell.PickDescriptor;

public class T_PickDescriptor {

	@Test
	public void testToString() {
		PickDescriptor d = new PickDescriptor("foo", "bar");
		System.out.println("[T_PickDescriptor.testToString] d=" + d);
	}

}
