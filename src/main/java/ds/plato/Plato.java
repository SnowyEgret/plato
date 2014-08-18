package ds.plato;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import ds.plato.block.BlockModel;
import ds.plato.block.BlockModelTileEntity;
import ds.plato.block.BlockPicked;
import ds.plato.block.BlockSelected;
import ds.plato.item.ItemFoo;
import ds.plato.network.SetBlockMessage;
import ds.plato.network.SetBlockMessageHandler;
import ds.plato.pick.IPick;
import ds.plato.pick.PickManager;
import ds.plato.proxy.CommonProxy;
import ds.plato.select.ISelect;
import ds.plato.select.SelectionManager;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellLoader;
import ds.plato.spell.draw.AbstractSpellDraw;
import ds.plato.spell.matrix.AbstractSpellMatrix;
import ds.plato.spell.select.AbstractSpellSelect;
import ds.plato.spell.transform.AbstractSpellTransform;
import ds.plato.staff.Staff;
import ds.plato.staff.StaffBirch;
import ds.plato.staff.StaffDraw;
import ds.plato.staff.StaffOak;
import ds.plato.staff.StaffSelect;
import ds.plato.staff.StaffTransform;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;

@Mod(modid = Plato.ID, name = Plato.NAME, version = Plato.VERSION)
public class Plato {

	public static final String ID = "plato";
	public static final String NAME = "Plato";
	public static final String VERSION = "0.5";

	@Instance(ID) public static Plato instance;
	@SidedProxy(clientSide = "ds.plato.proxy.ClientProxy", serverSide = "ds.plato.proxy.CommonProxy") public static CommonProxy proxy;

	private List<Staff> staffs;
	private static IUndo undoManager;
	private static ISelect selectionManager;
	private static IPick pickManager;
	private Configuration configuration;
	public static SimpleNetworkWrapper network;
	public static Logger log;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		log = LogManager.getLogger(NAME);

		log.info("[Plato.preInit]Initializing blocks...");
		Block blockSelected = initBlock(new BlockSelected());
		Block blockPicked = initBlock(new BlockPicked());

		// Try loading a Wavefront model
		Block blockModel = initBlock(new BlockModel());
		blockModel.setCreativeTab(CreativeTabs.tabBlock);
		// TODO what is this stringID
		GameRegistry.registerTileEntity(BlockModelTileEntity.class, "stringID");
		
		ItemFoo foo = new ItemFoo();
		foo.setUnlocalizedName("foo");
		foo.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(foo, foo.getClass().getSimpleName());

		undoManager = new UndoManager();
		selectionManager = new SelectionManager(blockSelected);
		pickManager = new PickManager(blockPicked);

		log.info("[Plato.preInit] Initializing spells and staff");
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		SpellLoader loader = new SpellLoader(configuration, undoManager, selectionManager, pickManager, ID);
		try {
			List<Spell> spells = loader.loadSpellsFromPackage("ds.plato.spell");
			log.info("[Plato.preInit] loaded spells=" + spells);

			Staff selectionStaff = loader.loadStaff(StaffSelect.class);
			Staff transformStaff = loader.loadStaff(StaffTransform.class);
			Staff drawStaff = loader.loadStaff(StaffDraw.class);

			staffs = new ArrayList<>();
			staffs.add(selectionStaff);
			staffs.add(transformStaff);
			staffs.add(drawStaff);

			// Create an empty staff
			//staffs.add(loader.loadStaff(Staff.class));
			staffs.add(loader.loadStaff(StaffOak.class));
			staffs.add(loader.loadStaff(StaffBirch.class));

			for (Spell s : spells) {
				if (s instanceof AbstractSpellSelect) {
					selectionStaff.addSpell(s);
				} else if (s instanceof AbstractSpellTransform || s instanceof AbstractSpellMatrix) {
					transformStaff.addSpell(s);
				} else if (s instanceof AbstractSpellDraw) {
					drawStaff.addSpell(s);
				}
			}

			log.info("[Plato.preInit] selectionStaff=" + selectionStaff);
			log.info("[Plato.preInit] transformStaff=" + transformStaff);
			log.info("[Plato.preInit] drawStaff=" + drawStaff);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		configuration.save();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

		//http://www.minecraftforge.net/forum/index.php?topic=20135.0
		network = NetworkRegistry.INSTANCE.newSimpleChannel("plato");
		network.registerMessage(SetBlockMessageHandler.class, SetBlockMessage.class, 0, Side.SERVER);
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers(selectionManager, pickManager);
		proxy.registerEventHandlers(this, selectionManager, undoManager, pickManager);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		log.info(NAME + " " + VERSION + " loaded.");
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		log.info("[Plato.serverStarted]");
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		log.info("[Plato.serverStopping]");
		selectionManager.clearSelections();
		pickManager.clearPicks();

		// TODO run through players inventory to look for ItemStacks containing staffs and set their NBT.
		// Iterable<ItemStack> ss = Player.client().getStaffItemStacks();
		// for (ItemStack stack : ss) {
		// Staff staff = (Staff) stack.getItem();
		// staff.save(stack);
		// }

		// for (Staff s : staffs) {
		// s.save();
		// }
		// configuration.save();
	}

	private Block initBlock(Block block) {
		String classname = block.getClass().getSimpleName();
		String name = classname.substring(0, 1).toLowerCase() + classname.substring(1);
		block.setBlockName(name);
		GameRegistry.registerBlock(block, ID + name);
		return block;
	}
}
