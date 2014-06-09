package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

	protected IWorld world;
	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;

	public Spell(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
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

//	@Override
//	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
//		System.out.println("[Spell.onUpdate] =");
//		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
//	}

	//TODO find a way to clear selections on left click air only
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		if (w.isRemote) {
			MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
			System.out.println("[Spell.onItemRightClick] position=" + position);
			if (position.typeOfHit == MovingObjectType.MISS) {
				pickManager.clearPicks();
				// if (this instanceof AbstractSpellSelection) {
				selectionManager.clearSelections();
				// }
			}
		}
		return is;
	}

	@Override
	public void onClickLeft(PlayerInteractEvent e) {
		if (e.entity.worldObj.isRemote) {
			System.out.println("[Spell.onClickLeft] Got remore world: " + e.entity.worldObj);
			return;
		}
		
		MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
		if (position.typeOfHit == MovingObjectType.MISS) {
			selectionManager.clearSelections();
			return;
		}

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
		pickManager.pick(e.x, e.y, e.z);
		System.out.println("[Spell.onClickRight] pickManager=" + pickManager);
		if (pickManager.isFinishedPicking()) {
			SlotEntry[] entries = getSlotEntriesFromPlayer(e.entityPlayer);
			//invoke(pickManager.getPicksArray(), entries);
			World w = e.entity.worldObj;
			System.out.println("[Spell.onClickRight] w=" + w);
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

	// public void clearPicks() {
	// // TODO move clearing of picks from Staff to PickManager so that it is available here.
	// pickManager.clearPicks();
	// }

	@Override
	public void resetPickManager() {
		pickManager.clearPicks();
		pickManager.reset(getNumPicks());
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

	public abstract int getNumPicks();

	public SlotEntry[] getSlotEntriesFromPlayer(EntityPlayer entityPlayer) {
		List<SlotEntry> entries = new ArrayList<>();
		InventoryPlayer inventory = entityPlayer.inventory;
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
				}
			}
		}
		if (entries.isEmpty()) {
			entries.add(new SlotEntry(Blocks.dirt));
		}
		SlotEntry[] array = new SlotEntry[entries.size()];
		return entries.toArray(array);
	}

}
