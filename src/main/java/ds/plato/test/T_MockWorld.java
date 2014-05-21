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
		IWorld w = mockWorld();
		w.setBlock(1, 2, 3, dirt);
		Block b = w.getBlock(1, 2, 3);
		assertThat(b.getClass().getName(), equalTo(dirt.getClass().getName()));
	}

	@Test
	public void getBlockMetadata_atPointNotSet() {
		IWorld w = mockWorld();
		int metadata = w.getBlockMetadata(1, 2, 3);
		assertThat(metadata, equalTo(0));
	}

	@Test
	public void setBlockMetadata() {
		IWorld w = mockWorld();
		w.setBlockMetadataWithNotify(1, 2, 3, 9, 0);
		int metadata = w.getBlockMetadata(1, 2, 3);
		assertThat(metadata, equalTo(9));
	}

}
