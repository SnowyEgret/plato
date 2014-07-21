package ds.plato.spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.config.Property;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IToggleable;
import ds.plato.core.Player;
import ds.plato.pick.IPick;

public class Staff extends Item implements IClickable, IToggleable, IInventory {

	protected Spell[] spells = new Spell[9];
	protected int ordinal = 0;
	private IPick pickManager;
	private Property propertyOrdinal;
	private Property propertySpells;
	private String name = "Staff";

	public Staff(Property propertyOrdinal, IPick pickManager) {
		this.pickManager = pickManager;
		this.propertyOrdinal = propertyOrdinal;
		System.out.println("[Staff.Staff] staff=" + this);
	}

	public void setPropertySpells(Property propertySpells) {
		this.propertySpells = propertySpells;
	}
	
	public Object[] getRecipe() {
		return new Object[] { "A  ", " A ", "  A", 'A', Items.bone };
	}

	public boolean hasRecipe() {
		return getRecipe() != null;
	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition position) {
		if (getSpell() != null)
			getSpell().onMouseClickLeft(position);
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition position) {
		if (getSpell() == null || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			Player player = Player.client();
			player.openGui(3);
		} else {
			getSpell().onMouseClickRight(position);
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
		Spell s = null;
		for (int i = 0; i < spells.length; i++) {
			if (ordinal == spells.length - 1) {
				ordinal = 0;
			} else {
				ordinal++;
			}
			s = spells[ordinal];
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				return s;
			}
		}
		return null;
	}

	public Spell previousSpell() {
		Spell s = null;
		for (int i = 0; i < spells.length; i++) {
			if (ordinal == 0) {
				ordinal = spells.length - 1;
			} else {
				ordinal--;
			}
			s = spells[ordinal];
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				return s;
			}
		}
		return null;
	}

	public Spell getSpell() {
		Spell s = spells[ordinal];
		if (s == null) {
			s = nextSpell();
		}
		return s;
	}

	public void addSpell(Spell spell) {
		for (int i = 0; i < spells.length; i++) {
			Spell s = spells[i];
			if (s == null) {
				spells[i] = spell;
				return;
			}
		}

		System.out.println("[Staff.addSpell] No room for spell on staff. spell=" + spell);
	}

	public int numSpells() {
		// return spells.size();
		int numSpells = 0;
		for (Spell s : spells) {
			if (s != null)
				numSpells++;
		}
		return numSpells;
	}

	public void clearPicks() {
		pickManager.clearPicks();
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Staff [ordinal=");
		builder.append(ordinal);
		builder.append(", spells=");
		builder.append(Arrays.toString(spells));
		builder.append("]");
		return builder.toString();
	}

	public void save() {
		propertyOrdinal.set(ordinal);
		
		// Only save this staff if it has been assembled by player
		if (propertySpells != null) {
			List<String> spellClassNames = new ArrayList<>();
			for (Spell s : spells) {
				spellClassNames.add(s.getClass().getName());
			}
			String[] array = new String[spellClassNames.size()];
			propertySpells.set(spellClassNames.toArray(array));
		}
	}

	// ---------------------------------------------------------------------------------
	// Implementation of interface IInventory. Needed for GuiStaff and GuiStaffContainer

	@Override
	public int getSizeInventory() {
		return spells.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (spells[i] == null) {
			return null;
		} else {
			return new ItemStack(spells[i]);
		}
	}

	//called
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amount);
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
		if (stack != null) {
			spells[slot] = (Spell) stack.getItem();
		} else {
			//Fix for issue #85 Spells cannot be re-positioned on staff
			spells[slot] = null;
		}
	}

	@Override
	public String getInventoryName() {
		return name;
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
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	// http://www.minecraftforge.net/forum/index.php?topic=14115.0
	// Only called by hoppers, etc. Extend Slot (SpellSlot) and overide isItemValid
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack stack) {
		return true;
	}
}
