package ds.plato.spell.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.common.test.MockSelectionManager;
import ds.plato.pick.IPick;
import ds.plato.pick.test.MockPickManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
import ds.plato.spell.PickDescriptor;
import ds.plato.spell.SpellDescriptor;
import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;
import ds.plato.undo.test.MockUndoManager;

public class T_DeleteSpell extends PlatoTestFactory {

	IWorld mockWorld = mockWorld();
	IUndo mockUndoManager = new MockUndoManager(mockWorld);
	ISelect mockSelectionManager = new MockSelectionManager(mockWorld);
	IPick mockPickManager = new MockPickManager(mockWorld);
	SpellDescriptor descriptor = new DeleteSpellDescriptor();
	@Mock BlockDirt dirt;
	@Mock BlockAir air;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockSelectionManager.select(1, 2, 3);
	}

	@Test
	public void encant() {
		DeleteSpell s = new DeleteSpell(descriptor, mockUndoManager, mockSelectionManager, mockPickManager);
		s.setWorld(mockWorld);
		s.encant(new PlayerInteractEvent(null, null, 1, 2, 3, 0));
		verify(mockWorld).setBlock(1, 2, 3, null);
	}

}
