package ds.plato.spell;

import ds.plato.pick.IPick;

public class StaffTransform extends Staff {

	public StaffTransform(IPick pickManager) {
		super(pickManager);
		// TODO Auto-generated constructor stub
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
