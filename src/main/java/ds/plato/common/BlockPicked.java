package ds.plato.common;

import ds.plato.client.ClientProxy;

public class BlockPicked extends BlockSelected {

	public BlockPicked() {
		super();
	}
	
	@Override
	public int getRenderType() {
		return ClientProxy.blockPickedRenderId;
	}
}
