package foo;

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

@Mod(modid = "foo", name = "foo", version = "")
public class ModFoo implements IGuiHandler {

	ItemFoo item;
	@Instance("foo")
	public static ModFoo instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		item = new ItemFoo();
		item.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(item, "foo");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GuiFoo(player.inventory, item.inventory);
	}

}
