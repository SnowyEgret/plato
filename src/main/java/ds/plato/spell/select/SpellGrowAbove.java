package ds.plato.spell.select;

import net.minecraft.client.resources.I18n;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.SpellGrowDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowAbove extends AbstractSpellSelect {

	public SpellGrowAbove(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.ABOVE, undo, select, pick);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellGrowDescriptor();
		d.name = I18n.format("item.spellGrowAbove.name");
		d.description = I18n.format("item.spellGrowAbove.description");
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "BBB", "BAB", "   ", 'A', ingredientA, 'B', ingredientB };
	}

}
