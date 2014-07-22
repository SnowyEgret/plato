package ds.plato.spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.core.IToggleable;
import ds.plato.core.Player;
import ds.plato.pick.IPick;

public class Staff extends Item implements IClickable, IToggleable, IInventory {

	protected Spell[] spells = new Spell[9];
	protected int ordinal = 0;
	private IPick pickManager;
	private Property propertyOrdinal;
	// private Property propertySpells;
	private String name = "Staff";
	private boolean areSpellsInitialized = false;

	public Staff(Property propertyOrdinal, IPick pickManager) {
		this.pickManager = pickManager;
		this.propertyOrdinal = propertyOrdinal;
		System.out.println("[Staff.Staff] staff=" + this);
	}

	// public void setPropertySpells(Property propertySpells) {
	// this.propertySpells = propertySpells;
	// }

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

	@Deprecated
	public void save() {
		propertyOrdinal.set(ordinal);
	}

	// // FIXME
	// public void read(ItemStack stack) {
	// NBTTagCompound tag = stack.getTagCompound();
	// // FIXME tag is null
	// // NBTTagCompound tag = new NBTTagCompound();
	// System.out.println("[Staff.read] tag=" + tag);
	// stack.readFromNBT(tag);
	// int i = 0;
	// while (true) {
	// String n = tag.getString(String.valueOf(i));
	// System.out.println("[Staff.read] n=" + n);
	// if (n != "") {
	// Spell s = (Spell) GameRegistry.findItem(Plato.ID, n);
	// if (s == null) {
	// throw new RuntimeException("Game registry could not find item " + n);
	// }
	// System.out.println("[Staff.read] Looked up spell in game registry. s=" + s);
	// spells[i] = s;
	// i++;
	// } else {
	// break;
	// }
	// }
	// }

	// public void save(ItemStack stack) {
	// if (!stack.hasTagCompound()) {
	// System.out.println("[Staff.save] No tag on stack. Creating a new one...");
	// stack.setTagCompound(new NBTTagCompound());
	// }
	// NBTTagCompound tag = stack.getTagCompound();
	// System.out.println("[Staff.save] tag before setting=" + tag);
	//
	// int i = 0;
	// for (Spell s : spells) {
	// if (s != null) {
	// String n = s.getClass().getSimpleName();
	// tag.setString(String.valueOf(i), n);
	// i++;
	// }
	// }
	// System.out.println("[Staff.save] tag after setting=" + tag);
	// stack.writeToNBT(tag);
	// }

	// Sets tag after crafting
	@Override
	public void onCreated(ItemStack stack, World w, EntityPlayer player) {
		if (!stack.hasTagCompound()) {
			System.out.println("[Staff.onCreated] No tag on stack. Creating a new one...");
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		System.out.println("[Staff.onCreated] tag=" + tag);
		// Do nothing. A newly crafted staff has no spells
	}

	// @Override
	// public boolean onItemUseFirst(ItemStack stack, EntityPlayer p, World w, int x, int y, int z, int side, float
	// hitX,
	// float hitY, float hitZ) {
	// if (!stack.hasTagCompound()) {
	// System.out.println("[Staff.onItemUseFirst] No tag on stack. Creating a new one...");
	// stack.setTagCompound(new NBTTagCompound());
	// }
	// NBTTagCompound tag = stack.getTagCompound();
	// System.out.println("[Staff.onItemUseFirst] tag=" + tag);
	// return true;
	// }

	// To write to tooltip on mouse over in creative tab
	// FIXME tag is empty even though it has been set in onUpdate();
	// Called every tick when mouse over staff in creative tab and player inventory.
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean par4) {
		if (!stack.hasTagCompound()) {
			System.out.println("[Staff.addInformation] No tag on stack. Creating a new one...");
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		// System.out.println("[Staff.addInformation] tag=" + tag);
		// TODO could display something meaningfull here
		if (spells.length == 0) {
			toolTip.add("No spells on staff");
		}

		//FIXME find the right place for this block
		// if (!areSpellsInitialized) {
		int i = 0;
		while (true) {
			String spellClassName = tag.getString(String.valueOf(i));
			if (spellClassName != "") {
				System.out.println("[Staff.addInformation] found string in tag: i=" + 1 + ", n=" + spellClassName);
				Spell spell = (Spell) GameRegistry.findItem(Plato.ID, spellClassName);
				if (spell == null) {
					throw new RuntimeException("Game registry could not find item " + spellClassName);
				}
				System.out.println("[Staff.read] Looked up spell in game registry. s=" + spell);
				spells[i] = spell;
				// list.add(spellClassName);
				i++;
			} else {
				break;
			}
		}
		// areSpellsInitialized = true;
		// }
	}

	// Seems to be working ok.
	// Called every tick when staff is in inventory
	// Could be used to update spells instead of in Plato.serverStopping
	@Override
	public void onUpdate(ItemStack stack, World w, Entity entity, int par4, boolean par5) {
		if (!stack.hasTagCompound()) {
			System.out.println("[Staff.onUpdate] No tag on stack. Creating a new one...");
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		// System.out.println("[Staff.onUpdate] tag=" + tag);

		int i = 0;
		for (Spell s : spells) {
			if (s == null) {
				tag.removeTag(String.valueOf(i));
			} else {
				String n = s.getClass().getSimpleName();
				tag.setString(String.valueOf(i), n);
			}
			i++;
		}
		// System.out.println("[Staff.onUpdate] tag after setting=" + tag);
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

	// called
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
			// Fix for issue #85 Spells cannot be re-positioned on staff
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
