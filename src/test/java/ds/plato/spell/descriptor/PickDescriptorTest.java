package ds.plato.spell.descriptor;

import org.junit.Test;

public class PickDescriptorTest {

	@Test
	public void testToString() {
		PickDescriptor d = new PickDescriptor("foo, bar");
		System.out.println("[T_PickDescriptor.testToString] d=" + d);
	}

}
