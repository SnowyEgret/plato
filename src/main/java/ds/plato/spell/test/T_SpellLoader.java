package ds.plato.spell.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.item.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.common.ConfigHelper;
import ds.plato.common.Plato;
import ds.plato.common.SelectionManager;
import ds.plato.spell.DeleteSpell;
import ds.plato.spell.DeleteSpellDescriptor;
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
		loader = new SpellLoader(Plato.ID);
	}

	@Test
	public void initSpell() throws Exception {
		Item i = loader.loadSpell(DeleteSpell.class, DeleteSpellDescriptor.class, undoManager, selectionManager, air);
		assertThat(i, instanceOf(DeleteSpell.class));
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

}
