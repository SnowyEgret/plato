package ds.plato.spell.test;

import static org.mockito.Mockito.verify;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.IWorld;
import ds.plato.common.SelectionManager;
import ds.plato.pick.IPick;
import ds.plato.pick.test.MockPickManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
import ds.plato.spell.SpellDescriptor;
import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.IUndo;
import ds.plato.undo.test.MockUndoManager;

public class T_DeleteSpell extends PlatoTestFactory {

	IWorld w = newMockWorld();
	IUndo mockUndoManager = new MockUndoManager(w);
	SelectionManager selectionManager = new SelectionManager().setWorld(w);
	IPick mockPickManager = new MockPickManager(w);
	SpellDescriptor descriptor = new DeleteSpellDescriptor();
	@Mock BlockDirt dirt;
	@Mock BlockAir air;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		selectionManager.select(1, 2, 3);
	}

	@Test
	public void encant() {
		DeleteSpell s = new DeleteSpell(descriptor, mockUndoManager, selectionManager, mockPickManager);
		s.setWorld(w);
		s.encant(new PlayerInteractEvent(null, null, 1, 2, 3, 0));
		//verify(w).setBlock(1, 2, 3, null);
	}

}
