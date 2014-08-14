package ds.plato.spell;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ds.plato.staff.StaffOakTest;

@RunWith(Suite.class)
@SuiteClasses({ PersistentVoxelGroupTest.class, SpellLoaderTest.class, SpellTest.class })
public class AllTests {

}
