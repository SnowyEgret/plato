package ds.plato.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ds.plato.core.Player;
import ds.plato.core.SlotDistribution;
import ds.plato.core.SlotEntry;
import ds.plato.core.Player.Direction;
import ds.plato.item.spell.Spell;
import ds.plato.item.staff.Staff;

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