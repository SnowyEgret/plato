package ds.plato.spell;

import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.AbstractSpellDescriptor;

public interface IHoldable {
	
	AbstractSpellDescriptor getDescriptor();

	boolean isPicking();

	void resetPickManager();

}
