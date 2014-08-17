package ds.plato.spell.select;

import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellGrowUp extends AbstractSpellSelect {

	public SpellGrowUp(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.UP, undo, select, pick);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { " B ", " A ", "   ", 'A', ingredientA, 'B', ingredientB };
	}

}
