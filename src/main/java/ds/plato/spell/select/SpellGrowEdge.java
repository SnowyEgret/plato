package ds.plato.spell.select;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.Shell.Type;
import ds.plato.undo.IUndo;

public class SpellGrowEdge extends AbstractSpellSelect {

	public SpellGrowEdge(IUndo undo, ISelect select, IPick pick) {
		super(null, undo, select, pick);
	}

	@Override
	public Object[] getRecipe() {
		return null;
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_grow_edge_name;
		d.description = Messages.spell_grow_edge_description;
		d.picks = new PickDescriptor(Messages.spell_grow_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_grow_modifier_0, Messages.spell_grow_modifier_1);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		int side = pickManager.getPicks()[0].side;
		System.out.println("[SpellGrowEdge.invoke] side=" + side);
		if (side == 1) {
			shellType = Shell.Type.EDGE;
		} else if (side == 0) {
			shellType = Shell.Type.EDGE_UNDER;
		} else {
			System.out.println("[SpellGrowEdge.invoke] Got unexpected side=" + side);
		}
		super.invoke(world, slotEntries);
	}
}
