package ds.plato.spell.descriptor;


public class SpellDescriptor {
	
	public String name = "This staff has no spells";
	public String description;
	public PickDescriptor picks;
	public ModifierDescriptor modifiers;
	
	public SpellDescriptor() {
	}

	public SpellDescriptor(String name, String description, PickDescriptor picks, ModifierDescriptor modifiers) {
		super();
		this.name = name;
		this.description = description;
		this.picks = picks;
		this.modifiers = modifiers;
	}
	
//	public String getName() {
//		return name.toUpperCase() + " spell";
//	}

	@Override
	public String toString() {
		return "SpellDescriptor [name=" + name + ", description=" + description + ", picks=" + picks + ", modifiers="
				+ modifiers + "]";
	}
	
}
