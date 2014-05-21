package ds.plato.pick.test;

import com.google.inject.Provider;

import net.minecraft.block.Block;
import ds.plato.IWorld;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;

public class MockPickManager implements IPick {

	public MockPickManager(IWorld mockWorld) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFinishedPicking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pick getPick(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pick(int x, int y, int z, Block block) {
		// TODO Auto-generated method stub

	}

}
