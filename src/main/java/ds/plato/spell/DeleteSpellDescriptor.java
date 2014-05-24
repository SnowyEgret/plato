package ds.plato.spell;

public class DeleteSpellDescriptor extends SpellDescriptor {

	public DeleteSpellDescriptor() {
		name = "DELETE";
		description = "Deletes selection";
		picks = new PickDescriptor("Anywhere");
	}
}
