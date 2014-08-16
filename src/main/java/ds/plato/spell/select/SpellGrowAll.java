package ds.plato.spell.select;

import net.minecraft.client.resources.I18n;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.SpellGrowDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowAll extends AbstractSpellSelect {

	public SpellGrowAll(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(Shell.Type.XYZ, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellGrowDescriptor();
		d.name = I18n.format("item.spellGrowAll.name");
		d.description = I18n.format("item.spellGrowAll.description");
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "BBB", "BAB", "BBB", 'A', ingredientA, 'B', ingredientB };
	}

}
