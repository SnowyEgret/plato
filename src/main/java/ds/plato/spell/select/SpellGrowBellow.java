package ds.plato.spell.select;

import net.minecraft.client.resources.I18n;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.SpellGrowDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowBellow extends AbstractSpellSelect {

	public SpellGrowBellow(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.BELLOW, undo, select, pick);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellGrowDescriptor();
		d.name = I18n.format("item.spellGrowBellow.name");
		d.description = I18n.format("item.spellGrowBellow.description");
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BAB", "BBB", 'A', ingredientA, 'B', ingredientB };
	}
}
