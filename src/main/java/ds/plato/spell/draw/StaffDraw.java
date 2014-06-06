package ds.plato.spell.draw;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;
import ds.plato.spell.Spell;
import ds.plato.spell.Staff;

public class StaffDraw extends Staff {

	public StaffDraw(Property propOrdinal, IPick pickManager) {
		super(propOrdinal, pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractSpellDraw;
		super.addSpell(spell);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffDraw [spells=");
		builder.append(spells);
		builder.append(", currentSpell()=");
		builder.append(currentSpell());
		builder.append("]");
		return builder.toString();
	}

}