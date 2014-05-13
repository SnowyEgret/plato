package ds.plato.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ds.plato.common.CommonProxy;

public class ClientProxy extends CommonProxy {

	public static int blockSelectedRenderId;

	public static void setCustomRenderers() {
		blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer());
	}
}
