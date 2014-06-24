package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.geom.solid.Box;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.AbstractSpellSelect;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable, IHoldable {

	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;
	private int numPicks;

	public Spell(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.numPicks = numPicks;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	@Override
	public Spell getSpell() {
		return this;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		// Minimizes animation on selecting with left mouse button.
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, Block b, int x, int y, int z, EntityLivingBase p) {
		// Minimizes animation on selecting with left mouse button.
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		if (w.isRemote) {
			MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
			// System.out.println("[Spell.onItemRightClick] position.typeOfHit=" + position.typeOfHit);
			if (position.typeOfHit == MovingObjectType.MISS) {
				pickManager.clearPicks();
			}
		}
		return is;
	}

	@Override
	public void onClickLeft(PlayerInteractEvent e) {
		if (e.entity.worldObj.isRemote) {
			System.out.println("[Spell.onClickLeft] Returning. Got remote world: " + e.entity.worldObj);
			return;
		}

		// MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
		// if (position.typeOfHit == MovingObjectType.MISS) {
		// if (e.isCancelable())
		// e.setCanceled(true);
		// return;
		// }

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && selectionManager.size() != 0) {
			Point3d lastPointSelected = selectionManager.lastSelection().getPoint3d();
			selectionManager.clearSelections();
			Box b = new Box(lastPointSelected, new Point3d(e.x, e.y, e.z));
			for (Point3i p : b.voxelize()) {
				selectionManager.select(p.x, p.y, p.z);
			}
			if (e.isCancelable())
				e.setCanceled(true);
			return;
		}

		Selection s = selectionManager.selectionAt(e.x, e.y, e.z);
		if (s == null) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				selectionManager.clearSelections();
			}
			selectionManager.select(e.x, e.y, e.z);
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				selectionManager.deselect(s);
			}
		}
		e.setCanceled(true);
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		// System.out.println("[Spell.onClickRight] e.y=" + e.y);
		pickManager.pick(e.x, e.y, e.z);
		if (pickManager.isFinishedPicking()) {
			SlotEntry[] entries = getSlotEntriesFromPlayer(e.entityPlayer);
			World w = e.entity.worldObj;
			invoke(new WorldWrapper(w), entries);
		}
	}

	@Override
	public void onClickRightAir(PlayerInteractEvent e) {
		System.out.println("[Spell.onClickRightAir] e=" + e);
		// Not working
		// pickManager.clearPicks();
	}

	// TODO remove this from ISelect. Pass selection manager to EventHandler instead.
	public ISelect getSelectionManager() {
		return selectionManager;
	}

	public abstract SpellDescriptor getDescriptor();

	public boolean isPicking() {
		return pickManager.isPicking();
	}

	@Override
	public void resetPickManager() {
		pickManager.clearPicks();
		pickManager.reset(numPicks);
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

	public abstract void invoke(IWorld world, final SlotEntry[] slotEntries);

	public SlotEntry[] getSlotEntriesFromPlayer(EntityPlayer entityPlayer) {
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
				} else if (item instanceof ItemBucket) {
					// ItemBucket itemBucket = (ItemBucket) item;
					// TODO how to determine contents of a bucket
					b = Blocks.water;
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
