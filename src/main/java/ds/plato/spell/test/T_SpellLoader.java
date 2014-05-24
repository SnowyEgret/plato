package ds.plato.spell.test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.common.Plato;
import ds.plato.common.SelectionManager;
import ds.plato.spell.AbstractSpell;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.SpellLoader;
import ds.plato.test.PlatoTestFactory;
import ds.plato.undo.UndoManager;

public class T_SpellLoader extends PlatoTestFactory {

	SpellLoader loader;
	@Mock UndoManager undoManager;
	@Mock SelectionManager selectionManager;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		loader = new SpellLoader(undoManager, selectionManager, air, Plato.ID);
	}

	@Test
	public void loadSpell() throws Exception {
		AbstractSpell spell = loader.loadSpell(DeleteSpell.class);
		assertThat(spell, instanceOf(DeleteSpell.class));
		assertThat(spell.descriptor.name.toLowerCase(), equalTo("delete"));
	}

	@Test
	public void loadSpells() throws Exception {
		Iterable<AbstractSpell> spells = loader.loadSpells(DeleteSpell.class, MoveSpell.class);
		int i = 0;
		for (AbstractSpell s : spells) {
			Class c = s.getClass();
			if (c != MoveSpell.class && c != DeleteSpell.class) {
				fail("Class not expected: "+c);
			}
			i++;
		}
		assertThat(i, is(2));
	}
}
