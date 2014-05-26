package ds.plato.spell;

public abstract class AbstractSpellDescriptor {
	
	public String name;
	public String description;
	public PickDescriptor picks;
	public ModifierDescriptor modifiers;
	
	public AbstractSpellDescriptor() {
	}

	public AbstractSpellDescriptor(String name, String description, PickDescriptor picks, ModifierDescriptor modifiers) {
		super();
		this.name = name;
		this.description = description;
		this.picks = picks;
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "SpellDescriptor [name=" + name + ", description=" + description + ", picks=" + picks + ", modifiers="
				+ modifiers + "]";
	}
	
}
