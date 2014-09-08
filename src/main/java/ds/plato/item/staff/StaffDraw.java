package ds.plato.item.staff;

import ds.plato.api.IPick;
import ds.plato.item.spell.Spell;
import ds.plato.item.spell.draw.AbstractSpellDraw;

public class StaffDraw extends Staff {

	public StaffDraw(IPick pickManager) {
		super(pickManager);
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
}
