package ds.plato.select;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

import ds.plato.test.PlatoIntegrationTest;

public class SelectionManagerIntegrationTest extends PlatoIntegrationTest {

	@Test
	public void select() {
		selectionManager.select(world, 1, 1, 1);
		assertThat(world.getBlock(1, 1, 1), equalTo(blockSelected));
	}

	@Test
	public void deselect() {		
		selectionManager.select(world, 1, 1, 1);
		selectionManager.deselect(1, 1, 1);
		assertThat(world.getBlock(1, 1, 1), equalTo(dirt));
		assertThat(world.getMetadata(1, 1, 1), equalTo(0));
	}

}
