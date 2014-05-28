package ds.plato.spell;

import ds.plato.pick.IPick;

public class StaffSelect extends Staff {

	public StaffSelect(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractSpellSelection;
		super.addSpell(spell);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffSelect [spells=");
		builder.append(spells);
		builder.append(", currentSpell()=");
		builder.append(currentSpell());
		builder.append("]");
		return builder.toString();
	}

}
