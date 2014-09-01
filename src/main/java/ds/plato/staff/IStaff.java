package ds.plato.staff;

import net.minecraft.item.ItemStack;
import ds.plato.spell.Spell;

public interface IStaff {

	public Spell getSpell(ItemStack stack);

	public Spell nextSpell(ItemStack stack);

	public Spell previousSpell(ItemStack stack);

	public int numSpells(ItemStack stack);

	public boolean isEmpty(ItemStack stack);

}