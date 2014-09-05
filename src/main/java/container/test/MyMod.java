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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MyItem i = new MyItem();
		i.setCreativeTab(CreativeTabs.tabMisc);
		i.setUnlocalizedName(i.getClass().getSimpleName());
		i.setTextureName("plato:spell");
		GameRegistry.registerItem(i, "myMod");
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new MyGuiHandler());
	}
}
