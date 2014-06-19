package ds.plato.test;

import static org.mockito.Mockito.when;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.block.BlockPicked;
import ds.plato.block.BlockSelected;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
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
	@Mock protected EntityPlayer player;
	@Mock protected InventoryPlayer inventory;
	@Mock protected Configuration config;
	@Mock protected Property property;

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

//	protected Provider<IWorld> newMockWorldProvider() {
//		return new Provider() {
//			@Override
//			public Object get() {
//				return newStubWorld();
//			}
//		};
//	}
}
