package ds.plato.spell;

import ds.plato.pick.IPick;

public class StaffDraw extends Staff {

	public StaffDraw(IPick pickManager) {
		super(pickManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractDrawSpell;
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
