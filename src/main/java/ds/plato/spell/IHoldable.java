package ds.plato.spell;

import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.AbstractSpellSelect;

public interface IHoldable {
	
	SpellDescriptor getDescriptor();

	boolean isPicking();

	@Deprecated void resetPickManager();

	Spell getSpell();

}
