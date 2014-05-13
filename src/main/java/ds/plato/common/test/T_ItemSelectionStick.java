package ds.plato.common.test;

import static org.junit.Assert.fail;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockDirt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BlockCactus.class, BlockDirt.class })
@PowerMockIgnore({ "javax.management.*" })
public class T_ItemSelectionStick {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
