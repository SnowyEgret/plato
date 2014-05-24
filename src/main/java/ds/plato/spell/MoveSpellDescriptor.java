package ds.plato.spell;


public class MoveSpellDescriptor extends SpellDescriptor {

	public MoveSpellDescriptor() {
		name = "MOVE";
		description = "Moves selections";
		picks = new PickDescriptor("From", "To");
		modifiers = new ModifierDescriptor("ctrl", "Deletes originial");
	}
}
