package ds.plato.spell.select;

import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellGrowHorizontal extends AbstractSpellSelect {

	public SpellGrowHorizontal(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.HORIZONTAL, undo, select, pick);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BAB", "   ", 'A', ingredientA, 'B', ingredientB };
	}
}
