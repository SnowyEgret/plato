package ds.plato.item.spell.select;

import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellGrowBellow extends AbstractSpellSelect {

	public SpellGrowBellow(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.BELLOW, undo, select, pick);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BAB", "BBB", 'A', ingredientA, 'B', ingredientB };
	}
}
