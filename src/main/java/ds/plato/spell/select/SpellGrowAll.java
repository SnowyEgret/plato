package ds.plato.spell.select;

import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellGrowAll extends AbstractSpellSelect {

	public SpellGrowAll(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(Shell.Type.XYZ, undoManager, selectionManager, pickManager);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "BBB", "BAB", "BBB", 'A', ingredientA, 'B', ingredientB };
	}

}
