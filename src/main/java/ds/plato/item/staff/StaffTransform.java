package ds.plato.item.staff;

import java.util.Arrays;

import net.minecraftforge.common.config.Property;
import ds.plato.item.spell.Spell;
import ds.plato.item.spell.matrix.AbstractSpellMatrix;
import ds.plato.item.spell.transform.AbstractSpellTransform;
import ds.plato.pick.IPick;

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
