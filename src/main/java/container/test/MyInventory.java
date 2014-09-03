package container.test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MyInventory implements IInventory {

	private MyTagWrapper tag;
	private int size = 9;
	IInventory inventory;
	ItemStack stack;
	private int slot;

	public MyInventory(IInventory inventory, int slot) {
		this.inventory = inventory;
		this.slot = slot;
		stack = inventory.getStackInSlot(slot);
		NBTTagCompound t = stack.getTagCompound();
		if (t == null) {
			t = new NBTTagCompound();
			stack.setTagCompound(t);
		}
		tag = new MyTagWrapper(t, size);
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return tag.getItemStack(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int amount) {
		ItemStack stack = tag.getItemStack(i);
		tag.setItemStack(i, null);
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (tag.getItemStack(i) != null) {
			ItemStack itemstack = tag.getItemStack(i);
			tag.setItemStack(i, null);
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		tag.setItemStack(i, stack);
		inventory.setInventorySlotContents(slot, this.stack);
	}

	@Override
	public int getSizeInventory() {
		return size;
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}
}