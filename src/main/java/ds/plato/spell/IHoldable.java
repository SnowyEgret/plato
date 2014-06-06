package ds.plato.spell;

import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;

public interface IHoldable {
	
	SpellDescriptor getDescriptor();

	boolean isPicking();

	void resetPickManager();

}
