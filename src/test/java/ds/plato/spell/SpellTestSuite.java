package ds.plato.spell;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ds.plato.spell.draw.SpellSphereTest;
import ds.plato.spell.matrix.SpellCopyTest;
import ds.plato.spell.transform.SpellDeleteTest;
import ds.plato.undo.test.T_SetBlock;

@RunWith(Suite.class)
@SuiteClasses({ SpellDeleteTest.class, SpellCopyTest.class, T_SetBlock.class, SpellLoaderTest.class, SpellSphereTest.class,
		StaffTest.class })
public class SpellTestSuite {

}
