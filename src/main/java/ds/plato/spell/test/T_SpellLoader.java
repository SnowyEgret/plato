package ds.plato.spell.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.common.ISelect;
import ds.plato.common.Plato;
import ds.plato.pick.IPick;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellLoader;
import ds.plato.test.PlatoTest;
import ds.plato.undo.IUndo;

public class T_SpellLoader extends PlatoTest {

	SpellLoader loader;
	@Mock IUndo undoManager;
	@Mock ISelect selectionManager;
	@Mock IPick pickManager;

	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		loader = new SpellLoader(undoManager, selectionManager, pickManager, air, Plato.ID);
	}

	@Test
	public void loadSpell() throws Exception {
		Spell spell = loader.loadSpell(DeleteSpell.class);
		assertThat(spell, instanceOf(DeleteSpell.class));
		assertThat(spell.descriptor.name.toLowerCase(), equalTo("delete"));
	}

	@Test
	public void loadSpells() throws Exception {
		Iterable<Spell> spells = loader.loadSpells(DeleteSpell.class, MoveSpell.class);
		int i = 0;
		for (Spell s : spells) {
			Class c = s.getClass();
			if (c != MoveSpell.class && c != DeleteSpell.class) {
				fail("Class not expected: "+c);
			}
			i++;
		}
		assertThat(i, is(2));
	}
}
