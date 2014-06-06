package ds.plato.block;

import ds.plato.proxy.ClientProxy;

public class BlockPicked extends BlockSelected {

	public BlockPicked() {
		super();
	}
	
	@Override
	public int getRenderType() {
		return ClientProxy.blockPickedRenderId;
	}
}
