package container.test;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "myMod", name = "MyMod", version = "")
public class MyMod {

	@Instance("myMod") public static MyMod instance;
	private MyItem myItem;

	// @SidedProxy(clientSide = "container.test.ClientProxy", serverSide = "container.test.CommonProxy")
	// public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		myItem = new MyItem();
		myItem.setCreativeTab(CreativeTabs.tabMisc);
		myItem.setUnlocalizedName(myItem.getClass().getSimpleName());
		GameRegistry.registerItem(myItem, "myMod");
		// proxy.registerGuiHandler(myItem.inventory);
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new MyGuiHandler(myItem.inventory));
	}
}
