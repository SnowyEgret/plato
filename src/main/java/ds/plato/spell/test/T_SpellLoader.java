package ds.plato.spell.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.common.Plato;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.GrowAllSpell;
import ds.plato.spell.MoveSpell;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellLoader;
import ds.plato.test.PlatoTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameRegistry.class })
@PowerMockIgnore({ "javax.management.*" })
public class T_SpellLoader extends PlatoTest {

	SpellLoader loader;

	@Before
	public void setUp() {
		super.setUp();
		PowerMockito.mockStatic(GameRegistry.class);
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
		List spellClasses = Lists.newArrayList(DeleteSpell.class, MoveSpell.class, GrowAllSpell.class);
		List<Spell> spells = loader.loadSpells(spellClasses);
		//assertThat(spells, hasItems(spellClasses));
		for (Spell s : spells) {
			Class c = s.getClass();
			if (!spellClasses.contains(c)) {
				fail("Class not expected: "+c);
			}
		}
		assertThat(spells.size(), is(spellClasses.size()));
	}
}
