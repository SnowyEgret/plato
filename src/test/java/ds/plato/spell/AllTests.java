package ds.plato.spell;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ds.plato.staff.StaffSelectTest;

@RunWith(Suite.class)
@SuiteClasses({ PersistentVoxelGroupTest.class, SpellLoaderTest.class })
public class AllTests {

}
