package ds.plato.staff;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import container.test.MyMod;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.core.Player;
import ds.plato.pick.IPick;
import ds.plato.pick.PickManager;
import ds.plato.spell.IClickable;
import ds.plato.spell.Spell;

public class StaffWood extends Item implements IClickable, IStaff {

	int size = 9;
	IPick pickManager;

	public StaffWood(IPick pickManager) {
		this.pickManager = pickManager;
	}

	public Object[] getRecipe() {
		return new Object[] { "#  ", " # ", "  #", '#', Items.bone };
	}

	public boolean hasRecipe() {
		return getRecipe() != null;
	}

	//https://github.com/TheGreyGhost/ItemRendering/blob/master/src/TestItemRendering/items/ItemLampshade.java
	@Override
	public int getSpriteNumber() {
		return 0;
	}

	// // Sets tag when staff is crafted
	// @Override
	// public void onCreated(ItemStack stack, World w, EntityPlayer player) {
	// if (stack.stackTagCompound == null) {
	// stack.setTagCompound(new NBTTagCompound());
	// }
	// System.out.println("[Staff.onCreated] stack.stackTagCompound=" + stack.stackTagCompound);
	// // Read here http://forgetutorials.weebly.com/nbt-tags.html that tag must be initialized
	// // Not convinced he is right
	// // for (int i = 0; i < spells.length; i++) {
	// // stack.stackTagCompound.setString(String.valueOf(i), "");
	// // }
	// }

	// Adds information to rollover in creative tab
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List rollOver, boolean par4) {
		if (isEmpty(stack)) {
			rollOver.add(EnumChatFormatting.RED + "No spells on staff");
		} else {
			rollOver.add(EnumChatFormatting.GREEN + " " + numSpells(stack) + " spells on staff");
		}
	}

	@Override
	public void onMouseClickLeft(ItemStack stack, MovingObjectPosition position) {
		if (!isEmpty(stack)) {
			getSpell(stack).onMouseClickLeft(stack, position);
		}
	}

	@Override
	public void onMouseClickRight(ItemStack stack, MovingObjectPosition position) {
		if (!isEmpty(stack)) {
			getSpell(stack).onMouseClickRight(stack, position);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(Plato.instance, 3, world, 0, 0, 0);
		}
		return stack;
	}

	@Override
	public Spell getSpell(ItemStack stack) {
		if (isEmpty(stack)) {
			return null;
		} else {
			Spell s = getSpell(stack, getOrdinal(stack));
			if (s == null) {
				s = nextSpell(stack);
			}
			return s;
		}
	}

	@Override
	public Spell nextSpell(ItemStack stack) {
		Spell s = null;
		for (int i = 0; i < size; i++) {
			if (getOrdinal(stack) == size - 1) {
				setOrdinal(stack, 0);
			} else {
				incrementOrdinal(stack, 1);
			}
			s = getSpell(stack, getOrdinal(stack));
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				break;
			}
		}
		return s;
	}

	@Override
	public Spell previousSpell(ItemStack stack) {
		Spell s = null;
		for (int i = 0; i < size; i++) {
			if (getOrdinal(stack) == 0) {
				setOrdinal(stack, size - 1);
			} else {
				incrementOrdinal(stack, -1);

			}
			s = getSpell(stack, getOrdinal(stack));
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				break;
			}
		}
		return s;
	}

	@Override
	public int numSpells(ItemStack stack) {
		int numSpells = 0;
		for (int i = 0; i < size; i++) {
			Spell s = getSpell(stack, i);
			if (s != null)
				numSpells++;
		}
		return numSpells;
	}

	@Override
	public boolean isEmpty(ItemStack stack) {
		for (int i = 0; i < size; i++) {
			Spell s = getSpell(stack, i);
			if (s != null) {
				return false;
			}
		}
		return true;
	}

	// ///////////////////////////

	private Spell getSpell(ItemStack stack, int i) {
		NBTTagCompound t = getTag(stack);
		String name = t.getString(String.valueOf(i));
		if (name != null && !name.equals("")) {
			Spell spell = (Spell) GameRegistry.findItem(Plato.ID, name);
			if (spell == null) {
				throw new RuntimeException("Game registry could not find item.  itemSimpleClassName=" + name);
			}
			return spell;
		}
		return null;
	}

	private int getOrdinal(ItemStack stack) {
		NBTTagCompound t = getTag(stack);
		int ordinal = t.getInteger("o");
		return ordinal;
	}

	private void setOrdinal(ItemStack stack, int i) {
		NBTTagCompound t = getTag(stack);
		t.setInteger("o", i);
	}

	private void incrementOrdinal(ItemStack stack, int increment) {
		NBTTagCompound t = getTag(stack);
		int ordinal = t.getInteger("o");
		ordinal = ordinal + increment;
		t.setInteger("o", ordinal);
	}

	private NBTTagCompound getTag(ItemStack stack) {
		NBTTagCompound t = stack.getTagCompound();
		if (t == null) {
			t = new NBTTagCompound();
			stack.setTagCompound(t);
		}
		return t;
	}
}
