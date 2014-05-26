package ds.plato.spell;

import ds.plato.common.ISelect;

public interface IHoldable {
	
	AbstractSpellDescriptor getDescriptor();

	void clearPicks();

	boolean isPicking();

	ISelect getSelectionManager();

}
