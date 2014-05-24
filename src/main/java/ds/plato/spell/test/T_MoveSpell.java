package ds.plato.spell.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockDirt;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwjgl.input.Keyboard;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.WorldWrapper;
import ds.plato.common.Selection;
import ds.plato.common.SelectionManager;
import ds.plato.pick.Pick;
import ds.plato.pick.PickManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Spell;
import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.Transaction;
import ds.plato.undo.UndoManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Keyboard.class })
@PowerMockIgnore({ "javax.management.*" })
public class T_MoveSpell extends PlatoTestFactory {

	@Mock WorldWrapper world;
	@Mock UndoManager um;
	@Mock SelectionManager sm;
	Spell spell;
	Pick[] picks;
	Selection s1, s2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(Keyboard.class);
		List<Selection> selections = new ArrayList<>();
		s1 = new Selection(0, 0, 0, dirt, 0);
		s2 = new Selection(1, 0, 0, dirt, 0);
		selections.add(s1);
		selections.add(s2);
		when(sm.getSelections()).thenReturn(selections);
		when(um.newTransaction()).thenReturn(new Transaction(um));
		spell = new MoveSpell(new MoveSpellDescriptor(), um, sm, air).setWorld(world);
		picks = new Pick[] { new Pick(0, 0, 0, dirt), new Pick(1, 0, 0, dirt) };
	}

	@Test
	public void invoke() {
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(false);
		spell.invoke(picks);
		verify(world).setBlock(1, 0, 0, dirt, 0, 3);
		verify(world).setBlock(2, 0, 0, dirt, 0, 3);
	}

	// TODO Test that only the two transformed blocks are selected. Must test state instead of behavior. More of an
	// integration test than a unit test.
	@Test
	public void invoke_deleteOriginal() {
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(true);
		spell.invoke(picks);
		verify(world).setBlock(1, 0, 0, dirt, 0, 3);
		verify(world).setBlock(2, 0, 0, dirt, 0, 3);
		verify(world).setBlock(0, 0, 0, air, 0, 3);
	}

	@Test
	public void getDescriptor() {
		assertThat(spell.getDescriptor(), instanceOf(MoveSpellDescriptor.class));
	}

}
