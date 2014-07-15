package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldServer;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.geom.solid.Box;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable, IHoldable {

	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;
	protected String message;
	private int numPicks;

	public Spell(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.numPicks = numPicks;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	public abstract Object[] getRecipe();

	public abstract void invoke(IWorld world, final SlotEntry[] slotEntries);
	
	public void invoke(EntityClientPlayerMP player) {
		IWorld w = getWorldServer(player);
		SlotEntry[] entries = getSlotEntries(player);
		invoke(w, entries);
	}

	@Override
	public abstract SpellDescriptor getDescriptor();

	@Override
	public Spell getSpell() {
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition e) {

		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		IWorld w = getWorldServer(player);
		
		// Standard selection behavior. Shift selects a region.
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && selectionManager.size() != 0) {
			Point3d lastPointSelected = selectionManager.lastSelection().point3d();
			selectionManager.clearSelections();
			Box b = new Box(lastPointSelected, new Point3d(e.blockX, e.blockY, e.blockZ));
			for (Point3i p : b.voxelize()) {
				selectionManager.select(w, p.x, p.y, p.z);
			}

		// Control adds or subtracts a selection
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			Selection s = selectionManager.selectionAt(e.blockX, e.blockY, e.blockZ);
			System.out.println("[Spell.onMouseClickLeft] s=" + s);
			if (s == null) {
				selectionManager.select(w, e.blockX, e.blockY, e.blockZ);
			} else {
				selectionManager.deselect(s);
			}
			
		} else {
			selectionManager.clearSelections();
			selectionManager.select(w, e.blockX, e.blockY, e.blockZ);
		}
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition e) {
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		IWorld w = getWorldServer(player);		
		pickManager.pick(w, e.blockX, e.blockY, e.blockZ);
		if (pickManager.isFinishedPicking()) {
			SlotEntry[] entries = getSlotEntries(player);
			invoke(w, entries);
		}
	}
	
	@Override
	public boolean isPicking() {
		return pickManager.isPicking();
	}

	@Override
	public void reset() {
		//System.out.println("[Spell.reset] resetting");
		pickManager.clearPicks();
		pickManager.reset(numPicks);
		message = null;
	}

	// For Staff.addSpell(). Only one spell of each type on a staff
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
			return true;
		return false;
	}

	public static IWorld getWorldServer(EntityClientPlayerMP p) {
		WorldServer w = null;
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.getIntegratedServer() != null) {
			w = mc.getIntegratedServer().worldServerForDimension(p.dimension);
		} else if (MinecraftServer.getServer() != null) {
			w = MinecraftServer.getServer().worldServerForDimension(p.dimension);
		}
		return new WorldWrapper(w);
	}

	public SlotEntry[] getSlotEntries(EntityPlayer entityPlayer) {
		List<SlotEntry> entries = new ArrayList<>();
		InventoryPlayer inventory = entityPlayer.inventory;
		for (int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				Item item = stack.getItem();
				int metadata = item.getDamage(stack);

				Block b = null;
				if (item instanceof ItemBlock) {
					ItemBlock itemBlock = (ItemBlock) item;
					b = itemBlock.field_150939_a;
					// TODO how to get color name from sub block?
					// if (b instanceof BlockColored) {
					// if (stack.getHasSubtypes()) {
					// List<ItemStack> subBlocks = new ArrayList<>();
					// ((BlockColored) b).getSubBlocks(item, getCreativeTab(), subBlocks);
					// ItemStack is = subBlocks.get(metadata);
					// b = ((ItemBlock) is.getItem()).field_150939_a;
					// System.out.println("[Spell.getSlotEntriesFromPlayer] is=" + is);
					// }
					// MapColor c = ((BlockColored) b).getMapColor(metadata);
					// System.out.println("[Spell.getSlotEntriesFromPlayer] c=" + c.colorValue);
					// }
				} else if (item == Items.water_bucket) {
					b = Blocks.water;
				} else if (item == Items.lava_bucket) {
					b = Blocks.lava;
				}

				if (b != null) {
					SlotEntry entry = new SlotEntry(b, metadata, i + 1);
					entries.add(entry);
				}
			}
		}
		if (entries.isEmpty()) {
			entries.add(new SlotEntry(Blocks.dirt));
		}
		SlotEntry[] array = new SlotEntry[entries.size()];
		return entries.toArray(array);
	}

	public int getNumPicks() {
		return numPicks;
	}

}
