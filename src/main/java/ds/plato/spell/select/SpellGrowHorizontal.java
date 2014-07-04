package ds.plato.spell.select;

import net.minecraft.block.Block;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowHorizontal extends AbstractSpellSelect {

	public SpellGrowHorizontal(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.HORIZONTAL, undo, select, pick);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_grow_horizontal_name;
		d.description = Messages.spell_grow_horizontal_description;
		d.picks = new PickDescriptor(Messages.spell_grow_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_grow_modifier_0, Messages.spell_grow_modifier_1);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BAB", "   ", 'A', ingredientA, 'B', ingredientB };
	}
}
