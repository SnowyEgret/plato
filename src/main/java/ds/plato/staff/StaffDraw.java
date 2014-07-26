package ds.plato.staff;

import java.util.Arrays;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;
import ds.plato.spell.Spell;
import ds.plato.spell.draw.AbstractSpellDraw;

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