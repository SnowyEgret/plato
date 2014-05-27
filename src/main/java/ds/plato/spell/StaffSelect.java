package ds.plato.spell;

import ds.plato.pick.IPick;

public class StaffSelect extends Staff {

	public StaffSelect(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractSelectionSpell;
		super.addSpell(spell);
	}

}
