package ds.plato.spell;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
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
	private String name = "Staff";
	private boolean spellsInitialized = false;

	public Staff(IPick pickManager) {
		this.pickManager = pickManager;
	} 

	public Object[] getRecipe() {
		return new Object[] { "#  ", " # ", "  #", '#', Items.bone };
	}

	public boolean hasRecipe() {
		return getRecipe() != null;
	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition position) {
		if (!isEmpty()) {
			getSpell().onMouseClickLeft(position);
		}
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition position) {
		if (isEmpty() || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			Player.client().openGui(3);
		} else {
			getSpell().onMouseClickRight(position);
		}
	}

	@Override
	public void toggle(IToggleable.Direction direction) {
		switch (direction) {
		case NEXT:
			nextSpell();
			break;
		case PREVIOUS:
			previousSpell();
			break;
		}
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
//			previousSpell();
//		} else {
//			nextSpell();
//		}
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
		if (isEmpty()) {
			return null;
		} else {
			Spell s = spells[ordinal];
			if (s == null) {
				s = nextSpell();
			}
			return s;
		}
	}

	//Called in preInit to initialize staffs
	public void addSpell(Spell spell) {
		for (int i = 0; i < spells.length; i++) {
			Spell s = spells[i];
			if (s == null) {
				spells[i] = spell;
				return;
			}
		}
		throw new RuntimeException("No room for spell on staff. spell=" + spell);
	}

	public int numSpells() {
		int numSpells = 0;
		for (Spell s : spells) {
			if (s != null)
				numSpells++;
		}
		return numSpells;
	}

	public boolean isEmpty() {
		for (Spell s : spells) {
			if (s != null) {
				return false;
			}
		}
		return true;
	}

	// Sets tag after crafting
	@Override
	public void onCreated(ItemStack stack, World w, EntityPlayer player) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		System.out.println("[Staff.onCreated] stack.stackTagCompound=" + stack.stackTagCompound);
		// Read here http://forgetutorials.weebly.com/nbt-tags.html that tag must be initialized
		// Not convinced he is right
		// for (int i = 0; i < spells.length; i++) {
		// stack.stackTagCompound.setString(String.valueOf(i), "");
		// }
	}

	// To write to rollover on mouse over in creative tab
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List rollOver, boolean par4) {
		if (isEmpty()) {
			rollOver.add(EnumChatFormatting.RED + "No spells on staff");
		} else {
			rollOver.add(EnumChatFormatting.GREEN + " " + numSpells() + " spells on staff");
		}

	}

	// On first call, initializes spells array and ordinal from stack's tag.
	// On tick, synchronizes tag with spells array and ordinal
	// For subclasses of Staff which have fixed spells, only manages ordinal.
	@Override
	public void onUpdate(ItemStack stack, World w, Entity entity, int par4, boolean par5) {

		if (!stack.hasTagCompound()) {
			System.out.println("[Staff.onUpdate] No tag on stack. Creating a new one...");
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();

		// First time through, set spells array with tag info
		if (!spellsInitialized) {
			// Subclasses of Staff already have spells.
			if (getClass().equals(Staff.class)) {
				System.out.println("[Staff.onUpdate] Initializing spells. tag=" + tag);
				int i = 0;
				while (true) {
					String spellClassName = tag.getString(String.valueOf(i));
					if (spellClassName != null && !spellClassName.equals("")) {
						System.out.println("[Staff.onUpdate] Found string in tag: i=" + i + ", spellClassName="
								+ spellClassName);
						Spell spell = (Spell) GameRegistry.findItem(Plato.ID, spellClassName);
						if (spell == null) {
							throw new RuntimeException("Game registry could not find item.  spellClassName="
									+ spellClassName);
						}
						System.out.println("[Staff.onUpdate] Looked up spell in game registry. spell=" + spell);
						spells[i] = spell;
						i++;
					} else {
						break;
					}
				}
			}
			ordinal = tag.getInteger("ordinal");
			spellsInitialized = true;
			System.out.println("[Staff.onUpdate] Staff initialized. " + this);

			// Spells array is initialized. Update tag to reflect any changes that may have been made to spells
		} else {
			// Subclasses of Staff cannot be reordered (Only in creative mode - no recipe)
			if (getClass().equals(Staff.class)) {
				int i = 0;
				for (Spell s : spells) {
					if (s == null) {
						// If tags are not initialized in on created
						tag.removeTag(String.valueOf(i));
					} else {
						String n = s.getClass().getSimpleName();
						tag.setString(String.valueOf(i), n);
					}
					i++;
				}
			}
			tag.setInteger("ordinal", ordinal);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(" [ordinal=");
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

	// Called every tick by GUI to draw screen.
	// Called by decrStackSize
	@Override
	public ItemStack getStackInSlot(int i) {

		// Exception e = new Exception();
		// StackTraceElement[] trace = e.getStackTrace();
		// for (int j = 0; j < 3; j++) {
		// if (!trace[1].getClassName().equals("net.minecraft.inventory.Slot")) {
		// e.printStackTrace();
		// }
		// }

		if (spells[i] == null) {
			return null;
		} else {
			ItemStack s = new ItemStack(spells[i]);
			return s;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int amount) {
		System.out.println("\n[Staff.decrStackSize] i=" + i + ", amount=" + amount);
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			// Simplified because inventory stack limit is 1
			// if (stack.stackSize <= amount) {
			setInventorySlotContents(i, null);
			// } else {
			// stack = stack.splitStack(amount);
			// if (stack.stackSize == 0) {
			// setInventorySlotContents(i, null);
			// }
			// }
		} else {
			System.out.println("[Staff.decrStackSize] UNEXPEXTED! stack=" + stack);
		}
		return stack;
	}

	// When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	// like when you close a workbench GUI.
	// Not being called
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		System.out.println("[Staff.getStackInSlotOnClosing] i=" + i);
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			setInventorySlotContents(i, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		System.out.println("[Staff.setInventorySlotContents] i=" + i + ", stack=" + stack);
		//new Exception().printStackTrace();
		if (stack == null) {
			// Fix for issue #85 Spells cannot be re-positioned on staff
			spells[i] = null;
		} else {
			spells[i] = (Spell) stack.getItem();
		}
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// System.out.println("[Staff.getInventoryStackLimit]");
		return 1;
	}

	@Override
	public void markDirty() {
	}

	// Not being called
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		System.out.println("[Staff.isUseableByPlayer] player=" + player);
		return true;
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
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}
}
