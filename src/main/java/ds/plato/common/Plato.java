package ds.plato.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
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
import ds.plato.spell.AbstractTransformerSpell;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellLoader;
import ds.plato.spell.Staff;
import ds.plato.spell.StaffDraw;
import ds.plato.spell.StaffSelect;
import ds.plato.spell.StaffTransform;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;

@Mod(modid = Plato.ID, name = Plato.NAME, version = Plato.VERSION)
//@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Plato {

	public static final String ID = "plato";
	public static final String NAME = "Plato";
	public static final String VERSION = "0.1";

	@Instance(ID) public static Plato instance;

	// TODO move initialization to ClientProxy
	@SidedProxy(clientSide = "ds.plato.client.ClientProxy", serverSide = "ds.plato.common.CommonProxy") public static CommonProxy proxy;

	// Blocks
	public static Block blockSelected;
	public static Block blockPicked;

	// TODO no longer needed after staffs and spells
	// Items
	public static StickSelection selectionStick;
	public static StickCurve curveStick;
	public static StickSurface surfaceStick;
	public static StickSolid solidStick;
	public static StickEdit editStick;

	private List<Spell> spells;
	private List<Staff> staffs;

	public static IUndo undoManager;
	public static ISelect selectionManager;
	public static IPick pickManager;

	public ConfigHelper config;
	public static Logger log;
	// public static KeyBinding keyUndo, keyRedo, keyToggle, keyDelete;
	// TODO remove after migrating to staffs and spells
	static WorldServer world;

	public static SlotDistribution slotDistribution;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
//		IUndo undoManager;
//		ISelect selectionManager;
//		IPick pickManager;
		
		undoManager = new UndoManager();
		selectionManager = new SelectionManager();
		pickManager = new PickManager();

		log = LogManager.getLogger(NAME);
		File file = event.getSuggestedConfigurationFile();
		config = new ConfigHelper(file, ID);

		log.info("[Plato.preInit]Initializing blocks...");
		blockSelected = config.initBlock(BlockSelected.class);
		blockPicked = config.initBlock(BlockPick.class);

		log.info("[Plato.preInit]Initializing items...");
		selectionStick = (StickSelection) config.initStick(StickSelection.class);
		surfaceStick = (StickSurface) config.initStick(StickSurface.class);
		curveStick = (StickCurve) config.initStick(StickCurve.class);
		editStick = (StickEdit) config.initStick(StickEdit.class);
		solidStick = (StickSolid) config.initStick(StickSolid.class);

		log.info("[Plato.preInit] Initializing spells and staff");
		SpellLoader loader = new SpellLoader(undoManager, selectionManager, pickManager, Blocks.air, ID);
		try {

			// List spellClasses = Lists.newArrayList(DeleteSpell.class, MoveSpell.class, GrowAllSpell.class,
			// SphereSpell.class);
			// spells = loader.loadSpells(spellClasses);
			spells = loader.loadSpellsFromPackage("ds.plato.spell");
			Staff selectionStaff = loader.loadStaff(StaffSelect.class);
			Staff transformStaff = loader.loadStaff(StaffTransform.class);
			Staff drawStaff = loader.loadStaff(StaffDraw.class);
			staffs = new ArrayList<>();
			staffs.add(loader.loadStaff(Staff.class));
			staffs.add(selectionStaff);
			staffs.add(transformStaff);
			staffs.add(drawStaff);
			for (Spell s : spells) {
				if (s instanceof AbstractSpellSelection) {
					selectionStaff.addSpell(s);
				} else if (s instanceof AbstractTransformerSpell || s instanceof AbstractSpellMatrixTransformation) {
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

		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		ClientProxy.setCustomRenderers();
		
		proxy.registerEventHandlers(this, selectionManager, undoManager);		
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
	}

	// TODO remove after migrating to staffs and spells
	private void saveSticks() {
		selectionStick.save();
		solidStick.save();
		surfaceStick.save();
		curveStick.save();
		editStick.save();
	}

	// TODO remove after migrating to staffs and spells
	public static void clearSelections() {
		if (selectionManager.size() != 0)
			selectionStick.clearSelections();
	}

	// TODO remove after migrating to staffs and spells
	public static void clearPicks() {
		selectionStick.clearPicks();
		solidStick.clearPicks();
		solidStick.firstPour = true;
		surfaceStick.clearPicks();
		curveStick.clearPicks();
		editStick.clearPicks();
	}

	// TODO make private after migrating to staffs and spells
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

	// TODO remove when migrating to staff and spells. Moved to class Spell.
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
		for (Spell s : spells) {
			s.setWorld(world);
		}
		for (Staff s : staffs) {
			s.setWorld(world);
		}
		System.out.println("[Plato.setWorld] Completed initalization of managers, staffs, and spells. world=" + world);
	}
}
