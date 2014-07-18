package ds.plato.spell.select;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.select.Shell.Type;
import ds.plato.undo.IUndo;

public class SpellGrowFloor extends AbstractSpellSelect {

	public SpellGrowFloor(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.FLOOR, undo, select, pick);
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_grow_floor_name;
		d.description = Messages.spell_grow_floor_description;
		d.picks = new PickDescriptor(Messages.spell_grow_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_grow_modifier_0, Messages.spell_grow_modifier_1);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		int side = pickManager.getPicks()[0].side;
		System.out.println("[SpellGrowFloor.invoke] side=" + side);
		if (side == 0) {
				shellType = Shell.Type.CEILING;
		} else if (side == 1) {
				shellType = Shell.Type.FLOOR;
		} else {
			System.out.println("[SpellGrowFloor.invoke] Got unexpected side=" + side);
		}
		super.invoke(world, slotEntries);
	}
}
