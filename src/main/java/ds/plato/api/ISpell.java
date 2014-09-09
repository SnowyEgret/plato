package ds.plato.api;

import ds.plato.core.SlotEntry;
import ds.plato.item.spell.SpellInfo;

public interface ISpell {

	public abstract void invoke(IWorld world, SlotEntry... slotEntries);

	public abstract String getMessage();

	public abstract int getNumPicks();

	public abstract boolean isPicking();

	public abstract void reset();

	public abstract SpellInfo getInfo();

}