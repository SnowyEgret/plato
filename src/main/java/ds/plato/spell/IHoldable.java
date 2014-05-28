package ds.plato.spell;

import ds.plato.common.ISelect;

public interface IHoldable {
	
	AbstractSpellDescriptor getDescriptor();

	boolean isPicking();

	void resetPickManager();

}
