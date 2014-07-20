package ds.plato.spell.draw;

import java.util.Arrays;

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
	public boolean hasRecipe() {
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffDraw [spells=");
		builder.append(Arrays.toString(spells));
		builder.append(", ordinal=");
		builder.append(ordinal);
		builder.append("]");
		return builder.toString();
	}


}
