package ds.plato.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ds.plato.core.Player.Direction;
import ds.plato.spell.Spell;
import ds.plato.staff.Staff;

public interface IPlayer {

	public abstract IWorld getWorld();

	public abstract SlotEntry[] getSlotEntries();

	public abstract Direction getDirection();

	public abstract SlotDistribution slotDistribution();

	public abstract ItemStack getHeldItemStack();

	public abstract Item getHeldItem();

	public abstract Spell getSpell();

	public abstract Staff getStaff();

}