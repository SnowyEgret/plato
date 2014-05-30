package ds.plato.common;

import ds.plato.client.ClientProxy;

public class BlockPick extends BlockSelected {

	public BlockPick() {
		super();
	}
	
	@Override
	public int getRenderType() {
		return ClientProxy.blockPickedRenderId;
	}
}
