package ds.plato.spell.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwjgl.input.Keyboard;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ds.plato.pick.Pick;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.spell.matrix.SpellCopy;
import ds.plato.test.PlatoTest;
import ds.plato.undo.Transaction;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Keyboard.class })
@PowerMockIgnore({ "javax.management.*" })
public class T_MoveSpell extends PlatoTest {

	Spell spell;
	Pick[] picks;
	Selection s1, s2;

	@Before
	public void setUp() {
		super.setUp();
		PowerMockito.mockStatic(Keyboard.class);
		List<Selection> selections = new ArrayList<>();
		s1 = new Selection(0, 0, 0, dirt, 0);
		s2 = new Selection(1, 0, 0, dirt, 0);
		selections.add(s1);
		selections.add(s2);
		when(selectionManager.getSelections()).thenReturn(selections);
		when(undoManager.newTransaction()).thenReturn(new Transaction(undoManager));
		spell = new SpellCopy(undoManager, selectionManager, pickManager,air);
		picks = new Pick[] { new Pick(0, 0, 0, dirt, 0), new Pick(1, 0, 0, dirt, 0) };
	}

	@Test
	public void invoke() {
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(false);
		//Parameter slotEntries not used by this spell
		spell.invoke(world, null);
		verify(world).setBlock(1, 0, 0, dirt, 0);
		verify(world).setBlock(2, 0, 0, dirt, 0);
	}

	// TODO Test that only the two transformed blocks are selected. Must test state instead of behavior.
	@Test
	public void invoke_deleteOriginal() {
		when(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)).thenReturn(true);
		spell.invoke(world, null);
		verify(world).setBlock(1, 0, 0, dirt, 0);
		verify(world).setBlock(2, 0, 0, dirt, 0);
		verify(world).setBlock(0, 0, 0, air, 0);
	}
}
