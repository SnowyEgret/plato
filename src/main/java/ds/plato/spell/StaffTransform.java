package ds.plato.spell;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;

public class StaffTransform extends Staff {

	public StaffTransform(Property propOrdinal, IPick pickManager) {
		super(propOrdinal, pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert (spell instanceof AbstractSpellTransformer || spell instanceof AbstractSpellMatrixTransformation);
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
