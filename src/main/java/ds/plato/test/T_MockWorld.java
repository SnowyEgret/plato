package ds.plato.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.IWorld;

public class T_MockWorld extends PlatoTestFactory {

	@Mock BlockDirt dirt;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void setBlock() {
		IWorld w = newMockWorld();
		w.setBlock(1, 2, 3, dirt, 9, 0);
		System.out.println("[T_MockWorld.setBlock] w=" + w);
		Block b = w.getBlock(1, 2, 3);
		int metadata = w.getMetadata(1, 2, 3);
		assertThat(b.getClass().getName(), equalTo(dirt.getClass().getName()));
		assertThat(metadata, equalTo(9));
	}

	@Test
	public void getBlockMetadata_atPointNotSet() {
		IWorld w = newMockWorld();
		int metadata = w.getMetadata(1, 2, 3);
		assertThat(metadata, equalTo(0));
	}

}
