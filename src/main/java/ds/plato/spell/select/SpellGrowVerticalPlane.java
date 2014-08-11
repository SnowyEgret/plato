package ds.plato.spell.select;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowVerticalPlane extends AbstractSpellSelect {

	public SpellGrowVerticalPlane(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.XY, undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		shellType = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? Shell.Type.YZ : Shell.Type.XY;
		super.invoke(world, slotEntries);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_grow_vertical_plane_name;
		d.description = Messages.spell_grow_vertical_plane_description;
		d.picks = new PickDescriptor(Messages.spell_grow_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_grow_modifier_0, Messages.spell_grow_modifier_1, Messages.spell_grow_vertical_plane_modifier_2);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { " B ", " A ", " B ", 'A', ingredientA, 'B', ingredientB };
	}
}
