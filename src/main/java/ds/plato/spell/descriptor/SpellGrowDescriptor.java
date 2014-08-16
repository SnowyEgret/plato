package ds.plato.spell.descriptor;

import net.minecraft.client.resources.I18n;

@Deprecated
public class SpellGrowDescriptor extends SpellDescriptor {
	
	public SpellGrowDescriptor() {
		picks = new PickDescriptor(I18n.format("pick.pattern"));
		modifiers = new ModifierDescriptor("ctrl," + I18n.format("modifier.shrink"), "alt," + I18n.format("modifier.any"));
	}

}
