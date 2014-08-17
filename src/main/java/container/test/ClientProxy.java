package container.test;

import net.minecraft.inventory.IInventory;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerGuiHandler(IInventory inventory) {
		NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new MyGuiHandler(inventory));
	}
}
