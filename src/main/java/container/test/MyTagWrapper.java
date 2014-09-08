package container.test;

import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.item.spell.Spell;
import ds.plato.item.staff.StaffBirch;
import ds.plato.item.staff.StaffOak;
import ds.plato.util.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MyTagWrapper {

	private NBTTagCompound tag;
	private int size;

	public MyTagWrapper(NBTTagCompound tag, int size) {
		this.tag = tag;
		this.size = size;
	}

	public ItemStack getItemStack(int i) {
		if (i < 0 || i > size - 1) {
			throw new IllegalArgumentException("Index not in tag range: " + i);
		}
		// System.out.println("[MyTagWrapper.getItemStack] tag=" + tag);
		String itemSimpleClassName = tag.getString(String.valueOf(i));
		if (itemSimpleClassName != null && !itemSimpleClassName.equals("")) {
			Spell spell = (Spell) GameRegistry.findItem(Plato.ID, itemSimpleClassName);
			if (spell == null) {
				throw new RuntimeException("Game registry could not find item.  itemSimpleClassName="
						+ itemSimpleClassName);
			}
			// System.out.println("[MyTagWrapper.getItemStack] Looked up spell in game registry. spell=" + spell);
			return new ItemStack(spell);
		}
		return null;
	}

	public void setItemStack(int i, ItemStack stack) {
		if (i < 0 || i > size - 1) {
			throw new IllegalArgumentException("Index not in tag range: " + i);
		}
		if (stack == null) {
			tag.removeTag(String.valueOf(i));
		} else {
			String n = stack.getItem().getClass().getSimpleName();
			// String n = StringUtils.toCamelCase(stack.getItem().getClass());
			tag.setString(String.valueOf(i), n);
		}
		System.out.println("[MyTagWrapper.setItemStack] i=" + i + ", tag=@" + System.identityHashCode(tag) + tag);
	}
}
