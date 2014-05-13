package ds.plato.common.test;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import ds.plato.common.Menu;

public class T_EnumDescription {

	@Test
	public void constructor() {
		Menu d = new Menu("box of chocolates", new String[]{"center", "edge"}, Pair.of("ctrl", "foo"), Pair.of("alt", "bar"));
		System.out.println("[T_EnumDescription.test] description=\n" + d);
	}

	@Test
	public void constructorNullVaryarg() {
		Menu d = new Menu("box", new String[]{"center", "edge"});
		System.out.println("[T_EnumDescription.test] description=\n" + d);
	}

}
