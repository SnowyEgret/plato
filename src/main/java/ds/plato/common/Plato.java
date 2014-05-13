package ds.plato.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import ds.plato.client.ClientProxy;
import ds.plato.undo.UndoManager;

@Mod(modid = Plato.ID, name = Plato.NAME, version = Plato.VERSION)
public class Plato {

	public static final String ID = "plato";
	public static final String NAME = "Plato";
	public static final String VERSION = "0.1";

	public static Logger log;

	// Blocks
	public static Block blockSelected;
	public static Block blockPick0;

	// Items
	public static StickSelection selectionStick;
	public static StickCurve curveStick;
	public static StickSurface surfaceStick;
	public static StickSolid solidStick;
	public static StickEdit editStick;

	@Instance(ID)
	public static Plato instance;

	@SidedProxy(clientSide = "ds.plato.client.ClientProxy", serverSide = "ds.plato.common.CommonProxy")
	public static CommonProxy proxy;

	public static UndoManager undoManager;
	public static SelectionManager selectionManager;

	public ConfigHelper config;

	public static KeyBinding keyUndo, keyRedo, keyToggle, keyDelete;

	static WorldServer world;

	public static SlotDistribution slotDistribution;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		log = LogManager.getLogger(NAME);
		config = new ConfigHelper(event.getSuggestedConfigurationFile(), ID);

		log.info("Initializing blocks...");
		blockSelected = config.initBlock(BlockSelected.class);
		blockPick0 = config.initBlock(BlockPick.class);
		// blockPick1 = config.initBlock(BlockPick1.class);
		// blockPick2 = config.initBlock(BlockPick2.class);

		log.info("Initializing items...");
		selectionStick = (StickSelection) config.initStick(StickSelection.class);
		surfaceStick = (StickSurface) config.initStick(StickSurface.class);
		curveStick = (StickCurve) config.initStick(StickCurve.class);
		editStick = (StickEdit) config.initStick(StickEdit.class);
		solidStick = (StickSolid) config.initStick(StickSolid.class);

		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		undoManager = new UndoManager();
		selectionManager = new SelectionManager();

		// Should have a config.initKeyBinding for language
		keyUndo = new KeyBinding("Undo", Keyboard.KEY_Z, NAME);
		keyRedo = new KeyBinding("Redo", Keyboard.KEY_Y, NAME);
		keyToggle = new KeyBinding("Toggle", Keyboard.KEY_TAB, NAME);
		keyDelete = new KeyBinding("Delete", Keyboard.KEY_DELETE, NAME);
		ClientRegistry.registerKeyBinding(keyUndo);
		ClientRegistry.registerKeyBinding(keyRedo);
		ClientRegistry.registerKeyBinding(keyToggle);
		ClientRegistry.registerKeyBinding(keyDelete);

		// Is all this really necessary? Why not
		ClientProxy.setCustomRenderers();
		// blockSelectedRenderId = RenderingRegistry.getNextAvailableRenderId();
		// RenderingRegistry.registerBlockHandler(new BlockSelectedRenderer());

		MinecraftForge.EVENT_BUS.register(new ForgeEventHandle());
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		log.info(NAME + " " + VERSION + " loaded.");
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		log.log(Level.INFO, "[Plato.serverStopping]");
		clearSelections();
		clearPicks();
		saveSticks();
		config.save();
	}

	private void saveSticks() {
		selectionStick.save();
		solidStick.save();
		surfaceStick.save();
		curveStick.save();
		editStick.save();
	}

	public static void clearSelections() {
		if (selectionManager.size() != 0)
			selectionStick.clearSelections();
	}

	public static void clearPicks() {
		selectionStick.clearPicks();
		solidStick.clearPicks();
		solidStick.firstPour = true;
		surfaceStick.clearPicks();
		curveStick.clearPicks();
		editStick.clearPicks();
	}

	public static WorldServer getWorldServer() {
		// http://www.minecraftforum.net/topic/1805594-how-to-get-worldserver-reference-from-world-or-entityplayer/
		if (world == null) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.getIntegratedServer() != null) {
				world = mc.getIntegratedServer().worldServerForDimension(mc.thePlayer.dimension);
			} else if (MinecraftServer.getServer() != null) {
				world = MinecraftServer.getServer().worldServerForDimension(mc.thePlayer.dimension);
			}
			System.out.println("[Plato.getWorldServer] w=" + world);
		}
		return world;
	}

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
			Plato.log.info("[Plato.updateBlockDistribution] inventoryDistribution=" + slotDistribution);
		}
	}
}
