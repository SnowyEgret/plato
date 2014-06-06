package ds.plato.spell.transform;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;
import ds.plato.spell.Spell;
import ds.plato.spell.Staff;
import ds.plato.spell.matrix.AbstractSpellMatrix;

public class StaffTransform extends Staff {

	public StaffTransform(Property propOrdinal, IPick pickManager) {
		super(propOrdinal, pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert (spell instanceof AbstractSpellTransform || spell instanceof AbstractSpellMatrix);
		super.addSpell(spell);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffTransform [spells=");
		builder.append(spells);
		builder.append(", currentSpell()=");
		builder.append(currentSpell());
		builder.append("]");
		return builder.toString();
	}

}
