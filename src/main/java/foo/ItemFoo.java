package foo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoo extends Item implements IInventory {
	
	protected ItemStack[] stacks = new ItemStack[9];
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player) {
		player.openGui(ModFoo.instance, 0, w, 0, 0, 0);
		return stack;
	}

	// IInventory ----------------------------------------------

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return stacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int var2) {
		//ItemStack stack = getStackInSlot(i);
		//ItemStack stack = stacks[i].copy();
		ItemStack stack = stacks[i];
		stacks[i] = null;
		//setInventorySlotContents(i, null);
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		stacks[i] = stack;
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		return false;
	}

}
