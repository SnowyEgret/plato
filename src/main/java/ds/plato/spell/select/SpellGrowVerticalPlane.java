package ds.plato.spell.select;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.SpellGrowDescriptor;
import ds.plato.undo.IUndo;

public class SpellGrowVerticalPlane extends AbstractSpellSelect {

	public SpellGrowVerticalPlane(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.XY, undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		shellType = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? Shell.Type.YZ : Shell.Type.XY;
		super.invoke(world, slotEntries);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellGrowDescriptor();
		d.name = I18n.format("item.spellGrowVerticalPlane.name");
		d.description = I18n.format("item.spellGrowVerticalPlane.description");
		d.modifiers.addModifier(SHIFT + I18n.format("item.spellGrowVerticalPlane.modifier.0"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { " B ", " A ", " B ", 'A', ingredientA, 'B', ingredientB };
	}
}
