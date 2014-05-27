package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable, IHoldable {

	public AbstractSpellDescriptor descriptor;
	protected IWorld world;
	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;

	public Spell(AbstractSpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.descriptor = descriptor;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	// Spell can only be partially constructed during FML initialization. The world is only available after the player
	// joins the game.
	public Spell setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	// TODO Tried overiding left click to select or deselect a block. For now, just cancels event so block is not broken.
	// Description: Ticking memory connection
	//
	// java.lang.NullPointerException: Ticking memory connection
	// at net.minecraft.world.chunk.storage.ExtendedBlockStorage.func_150818_a(ExtendedBlockStorage.java:96)
	// at net.minecraft.world.chunk.Chunk.func_150807_a(Chunk.java:665)
	// at net.minecraft.world.World.setBlock(World.java:517)
	// at net.minecraft.world.World.setBlock(World.java:665)
	// at ds.plato.WorldWrapper.setBlock(WorldWrapper.java:26)
	// at ds.plato.common.SelectionManager.select(SelectionManager.java:53)
	// at ds.plato.spell.Spell.onClickLeft(Spell.java:51)
	@Override
	public void onClickLeft(PlayerInteractEvent e) {
//		if (e.entity.worldObj.isRemote)
//			return;
		//selectionManager.select(e.x, e.y, e.z);
		e.setCanceled(true);
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		if (pickManager.isFinishedPicking()) {
			// TODO move getBlocksWithMetadataInIventorySlots here
			SlotEntry[] entries = getSlotEntriesFromPlayer(e.entityPlayer);
			invoke(pickManager.getPicksArray(), entries);
		}
	}

	// TODO remove this from ISelect. Pass selection manager to EventHandler instead.
	public ISelect getSelectionManager() {
		return selectionManager;
	}

	public AbstractSpellDescriptor getDescriptor() {
		return descriptor;
	}

	public boolean isPicking() {
		return pickManager.isPicking();
	}

	@Override
	public void clearPicks() {
		// TODO move clearing of picks from Staff to PickManager so that it is available here.
		pickManager.clearPicks();
	}

	// TODO Eliminate static method getBlocksWithMetadataInIventorySlots in class Plato when migrating to staff and
	// spells.
	private SlotEntry[] getSlotEntriesFromPlayer(EntityPlayer entityPlayer) {
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

	// TODO Maybe this is protected and staff sends its PlayerInteractEvent to the onClickRight.
	public abstract void invoke(Pick[] picks, SlotEntry[] slotEntries);

	public abstract int getNumPicks();

}
