package container.test;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "myMod", name = "MyMod", version = "")
public class MyMod {

	@Instance("myMod") public static MyMod instance;
	//@SidedProxy(clientSide = "container.test.ClientProxy", serverSide = "container.test.CommonProxy") public static CommonProxy proxy;	

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {		
		MyItem myItem = new MyItem();
		myItem.setCreativeTab(CreativeTabs.tabMisc);
		myItem.setUnlocalizedName(myItem.getClass().getSimpleName());
		GameRegistry.registerItem(myItem, "myMod");		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new MyGuiHandler(myItem));
	}

//	@EventHandler
//	public void init(FMLInitializationEvent event) {
//		NetworkRegistry.INSTANCE.registerGuiHandler(this, new MyGuiHandler());
//		//proxy.registerGuiHandler();
//	}
}
