package ds.plato.spell.transform;

import java.util.Arrays;

import net.minecraftforge.common.config.Property;
import ds.plato.pick.IPick;
import ds.plato.spell.Spell;
import ds.plato.spell.Staff;
import ds.plato.spell.matrix.AbstractSpellMatrix;

public class StaffTransform extends Staff {

	public StaffTransform(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert (spell instanceof AbstractSpellTransform || spell instanceof AbstractSpellMatrix);
		super.addSpell(spell);
	}

	@Override
	public boolean hasRecipe() {
		return false;
	}
}
