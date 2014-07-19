package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.Plato;
import ds.plato.core.IToggleable;
import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.pick.IPick;
import ds.plato.spell.descriptor.SpellDescriptor;

public class Staff extends Item implements IClickable, IToggleable, IInventory {

	protected List<Spell> spells = new ArrayList<>();
	protected int ordinal = 0;
	private IPick pickManager;
	private Property propertyOrdinal;
	private String name = "Staff";

	public Staff(Property propertyOrdinal, IPick pickManager) {
		this.pickManager = pickManager;
		this.propertyOrdinal = propertyOrdinal;
	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition position) {
		if (currentSpell() != null)
			currentSpell().onMouseClickLeft(position);
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition position) {
		if (currentSpell() == null) {
			Player player = Player.client();
			player.openGui(3);
		} else {
			currentSpell().onMouseClickRight(position);
		}
	}

	@Override
	public void toggle() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			previousSpell();
		} else {
			nextSpell();
		}
	}

	public Spell nextSpell() {
		if (ordinal == spells.size() - 1) {
			ordinal = 0;
		} else {
			ordinal++;
		}
		Spell currentSpell = currentSpell();
		if (currentSpell != null) {
			pickManager.reset(currentSpell.getNumPicks());
		}
		return currentSpell;
	}

	public Spell previousSpell() {
		if (ordinal == 0) {
			ordinal = spells.size() - 1;
		} else {
			ordinal--;
		}
		Spell currentSpell = currentSpell();
		pickManager.reset(currentSpell.getNumPicks());
		return currentSpell;
	}

	public Spell currentSpell() {
		if (spells.isEmpty()) {
			return null;
		} else {
			return spells.get(ordinal);
		}
	}

	public void addSpell(Spell spell) {
		if (!spells.contains(spell)) {
			spells.add(spell);
		}
	}

	public int numSpells() {
		return spells.size();
	}

	public void clearPicks() {
		pickManager.clearPicks();
	}

	@Override
	public String toString() {
		return "Staff [spells=" + spells + ", ordinal=" + ordinal + ", pickManager=" + pickManager + "]";
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public void save() {
		propertyOrdinal.set(ordinal);
		System.out.println("[Staff.save] propertyOrdinal=" + propertyOrdinal);
	}

	// //////////////////////// IInventory

	@Override
	public int getSizeInventory() {
		return numSpells();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (spells.isEmpty()) {
			return null;
		} else {
			return new ItemStack(spells.get(i));
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		spells.set(slot, (Spell) stack.getItem());
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return name ;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}
}
