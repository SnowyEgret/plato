package ds.plato.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
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
import ds.plato.IWorld;
import ds.plato.client.ClientProxy;
import ds.plato.pick.IPick;
import ds.plato.pick.PickManager;
import ds.plato.spell.AbstractSpellDraw;
import ds.plato.spell.AbstractSpellMatrixTransformation;
import ds.plato.spell.AbstractSpellSelection;
import ds.plato.spell.AbstractSpellTransformer;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellLoader;
import ds.plato.spell.Staff;
import ds.plato.spell.StaffDraw;
import ds.plato.spell.StaffSelect;
import ds.plato.spell.StaffTransform;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;

@Mod(modid = Plato.ID, name = Plato.NAME, version = Plato.VERSION)
// @NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Plato {

	public static final String ID = "plato";
	public static final String NAME = "Plato";
	public static final String VERSION = "0.1";

	@Instance(ID) public static Plato instance;

	@SidedProxy(clientSide = "ds.plato.client.ClientProxy", serverSide = "ds.plato.common.CommonProxy") public static CommonProxy proxy;

	// Blocks
	public static Block blockSelected;
	public static Block blockPicked;

	// Items
	@Deprecated public static StickSelection selectionStick;
	@Deprecated public static StickCurve curveStick;
	@Deprecated public static StickSurface surfaceStick;
	@Deprecated public static StickSolid solidStick;
	@Deprecated public static StickEdit editStick;

	private List<Spell> spells;
	private List<Staff> staffs;

	public static IUndo undoManager;
	public static ISelect selectionManager;
	public static IPick pickManager;

	private Configuration configuration;
	public static Logger log;

	// TODO remove after migrating to staffs and spells
	@Deprecated static WorldServer world;
	@Deprecated public ConfigHelper config;

	public static SlotDistribution slotDistribution;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		log = LogManager.getLogger(NAME);
		File file = event.getSuggestedConfigurationFile();
		config = new ConfigHelper(file, ID);

		log.info("[Plato.preInit]Initializing blocks...");
		blockSelected = config.initBlock(BlockSelected.class);
		blockPicked = config.initBlock(BlockPicked.class);

		undoManager = new UndoManager();
		selectionManager = new SelectionManager((BlockSelected) blockSelected);
		pickManager = new PickManager(0, (BlockPicked) blockPicked);

		log.info("[Plato.preInit]Initializing items...");
		selectionStick = (StickSelection) config.initStick(StickSelection.class);
		surfaceStick = (StickSurface) config.initStick(StickSurface.class);
		curveStick = (StickCurve) config.initStick(StickCurve.class);
		editStick = (StickEdit) config.initStick(StickEdit.class);
		solidStick = (StickSolid) config.initStick(StickSolid.class);

		log.info("[Plato.preInit] Initializing spells and staff");
		configuration = new Configuration(file);
		SpellLoader loader = new SpellLoader(configuration, undoManager, selectionManager, pickManager, Blocks.air, ID);
		try {
			spells = loader.loadSpellsFromPackage("ds.plato.spell");

			Staff selectionStaff = loader.loadStaff(StaffSelect.class);
			Staff transformStaff = loader.loadStaff(StaffTransform.class);
			Staff drawStaff = loader.loadStaff(StaffDraw.class);
			loader.loadStaff(Staff.class);

			staffs = new ArrayList<>();
			// staffs.add(loader.loadStaff(Staff.class));
			staffs.add(selectionStaff);
			staffs.add(transformStaff);
			staffs.add(drawStaff);

			for (Spell s : spells) {
				if (s instanceof AbstractSpellSelection) {
					selectionStaff.addSpell(s);
				} else if (s instanceof AbstractSpellTransformer || s instanceof AbstractSpellMatrixTransformation) {
					transformStaff.addSpell(s);
				} else if (s instanceof AbstractSpellDraw) {
					drawStaff.addSpell(s);
				}
			}

			System.out.println("[Plato.preInit] selectionStaff=" + selectionStaff);
			System.out.println("[Plato.preInit] transformStaff=" + transformStaff);
			System.out.println("[Plato.preInit] drawStaff=" + drawStaff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		configuration.save();

		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// TODO remove static call
		// proxy.setCustomRenderers(selectionManager, pickManager);
		ClientProxy.setCustomRenderers(selectionManager, pickManager);

		proxy.registerEventHandlers(this, selectionManager, undoManager, pickManager, (BlockAir) Blocks.air);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		log.info(NAME + " " + VERSION + " loaded.");
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		log.log(Level.INFO, "[Plato.serverStarted]");
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		log.log(Level.INFO, "[Plato.serverStopping]");

		clearSelections();
		clearPicks();
		saveSticks();
		config.save();

		selectionManager.clearSelections();
		pickManager.clearPicks();
		for (Staff s : staffs) {
			s.save();
		}
		configuration.save();
	}

	@Deprecated
	private void saveSticks() {
		selectionStick.save();
		solidStick.save();
		surfaceStick.save();
		curveStick.save();
		editStick.save();
	}

	@Deprecated
	public static void clearSelections() {
		if (selectionManager.size() != 0)
			selectionStick.clearSelections();
	}

	@Deprecated
	public static void clearPicks() {
		selectionStick.clearPicks();
		solidStick.clearPicks();
		solidStick.firstPour = true;
		surfaceStick.clearPicks();
		curveStick.clearPicks();
		editStick.clearPicks();

	}

	@Deprecated
	public static WorldServer getWorldServer() {
		// http://www.minecraftforum.net/topic/1805594-how-to-get-worldserver-reference-from-world-or-entityplayer/
		if (world == null) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityClientPlayerMP player = mc.thePlayer;
			// System.out.println("[Plato.getWorldServer] player=" + player);
			if (mc.getIntegratedServer() != null) {
				world = mc.getIntegratedServer().worldServerForDimension(player.dimension);
			} else if (MinecraftServer.getServer() != null) {
				world = MinecraftServer.getServer().worldServerForDimension(player.dimension);
			}
			System.out.println("[Plato.getWorldServer] w=" + world);
		}
		return world;
	}

	@Deprecated
	public static List<SlotEntry> getBlocksWithMetadataInIventorySlots() {
		List<SlotEntry> entries = new ArrayList<>();
		InventoryPlayer inventory = Minecraft.getMinecraft().thePlayer.inventory;
		for (int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				Item item = stack.getItem();
				if (item instanceof ItemBlock) {
					ItemBlock itemBlock = (ItemBlock) item;
					Block b = itemBlock.field_150939_a;
					int metadata = item.getDamage(stack);
					SlotEntry entry = new SlotEntry(b, metadata, i + 1);
					entries.add(entry);
					// Not working
				} else if (item instanceof ItemBucket) {
					Block b = Blocks.water;
					int metadata = item.getDamage(stack);
					SlotEntry entry = new SlotEntry(b, metadata, i + 1);
					entries.add(entry);
				}
			}
		}
		if (entries.isEmpty()) {
			entries.add(new SlotEntry(Blocks.dirt));
		}
		return entries;
	}

	public static void updateInventoryDistribution() {
		SlotDistribution d = new SlotDistribution(Plato.getBlocksWithMetadataInIventorySlots());
		if (!d.equals(slotDistribution)) {
			slotDistribution = d;
			Plato.log.info("[Plato.updateBlockDistribution] Updated slot distribution: " + slotDistribution);
		}
	}

	// World is not available when selectionManager and spells are initialized.
	public void setWorld(IWorld world) {
		selectionManager.setWorld(world);
		pickManager.setWorld(world);
		System.out.println("[Plato.setWorld] Completed initalization of managers, staffs, and spells. world=" + world);
	}
}
