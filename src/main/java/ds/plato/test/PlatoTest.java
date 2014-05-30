package ds.plato.test;

import static org.mockito.Mockito.when;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.Provider;

import ds.plato.IWorld;
import ds.plato.common.BlockPicked;
import ds.plato.common.BlockSelected;
import ds.plato.common.ISelect;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public class PlatoTest {

	@Mock protected BlockDirt dirt;
	@Mock protected BlockSand sand;
	@Mock protected BlockSand clay;
	@Mock protected BlockAir air;
	@Mock protected BlockSelected blockSelected;
	@Mock protected BlockPicked blockPicked;
	@Mock protected IWorld world;
	@Mock protected ISelect selectionManager;
	@Mock protected IPick pickManager;
	@Mock protected IUndo undoManager;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(dirt.getLocalizedName()).thenReturn("dirt");
		when(sand.getLocalizedName()).thenReturn("sand");
		when(clay.getLocalizedName()).thenReturn("clay");
	}

	protected IWorld newStubWorld() {
		return new StubWorld(dirt);
	}

	protected Provider<IWorld> newMockWorldProvider() {
		return new Provider() {
			@Override
			public Object get() {
				return newStubWorld();
			}
		};
	}
}
