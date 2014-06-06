package ds.plato.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.core.IWorld;

public class T_StubWorld extends PlatoTest {

	IWorld world;

	@Before
	public void setUp() {
		super.setUp();
		world = newStubWorld();
	}

	@Test
	public void setBlock() {
		world.setBlock(1, 2, 3, dirt, 9);
		Block b = world.getBlock(1, 2, 3);
		int metadata = world.getMetadata(1, 2, 3);
		assertEquals(dirt, b);
		assertThat(metadata, equalTo(9));
	}

	@Test
	public void getBlock_atPointNotSetReturnsDirt() {
		Block b = world.getBlock(2, 2, 2);
		System.out.println("[T_StubWorld.getBlock_atPointNotSetReturnsDirt] dirt=" + dirt);
		System.out.println("[T_StubWorld.getBlock_atPointNotSetReturnsDirt] b=" + b);
		assertEquals(dirt, b);
	}

	@Test
	public void getBlockMetadata_atPointNotSetReturns0() {
		int metadata = world.getMetadata(1, 2, 3);
		assertThat(metadata, equalTo(0));
	}

}
