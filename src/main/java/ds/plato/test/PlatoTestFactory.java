package ds.plato.test;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.Provider;

import ds.plato.IWorld;
import ds.plato.common.BlockSelected;

public class PlatoTestFactory {

	@Mock protected BlockDirt dirt;
	@Mock protected BlockAir air;
	
	protected BlockSelected blockSelected;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		blockSelected = new BlockSelected();
	}
	
	protected IWorld newMockWorld() {
		return new MockWorld();
	}

	protected Provider<IWorld> newMockWorldProvider() {
		return new Provider() {
			@Override
			public Object get() {
				return newMockWorld();
			}
		};
	}
}
