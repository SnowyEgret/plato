package ds.plato.spell.select;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;
import ds.plato.spell.Spell;
import ds.plato.spell.Staff;

public class StaffSelect extends Staff {

	public StaffSelect(Property propOrdinal, IPick pickManager) {
		super(propOrdinal, pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractSpellSelect;
		super.addSpell(spell);
	}

	@Override
	public boolean hasRecipe() {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffSelect [spells=");
		builder.append(spells);
		builder.append(", currentSpell()=");
		builder.append(getSpell());
		builder.append("]");
		return builder.toString();
	}

}
