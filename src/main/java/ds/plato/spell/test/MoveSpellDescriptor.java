package ds.plato.spell.test;

import ds.plato.spell.PickDescriptor;
import ds.plato.spell.SpellDescriptor;

public class MoveSpellDescriptor extends SpellDescriptor {

	public MoveSpellDescriptor() {
		name = "MOVE";
		description = "Moves selections";
		picks = new PickDescriptor("From", "To");
	}
}
