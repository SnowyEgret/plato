package container.test;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "myMod", name = "myMod", version = "")
public class MyMod implements IGuiHandler {

	MyItem item;
	@Instance("myMod")
	public static MyMod instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		item = new MyItem();
		item.setCreativeTab(CreativeTabs.tabMisc);
		item.setUnlocalizedName(item.getClass().getSimpleName());
		GameRegistry.registerItem(item, "myMod");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new MyContainer(player.inventory, item.inventory);
		//return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new MyGui(player.inventory, item.inventory);
	}

}
