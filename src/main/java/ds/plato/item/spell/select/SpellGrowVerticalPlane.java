package ds.plato.item.spell.select;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.item.spell.descriptor.Modifier;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellGrowVerticalPlane extends AbstractSpellSelect {

	public SpellGrowVerticalPlane(IUndo undo, ISelect select, IPick pick) {
		super(Shell.Type.XY, undo, select, pick);
		info.addModifiers(Modifier.SHIFT);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		shellType = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? Shell.Type.YZ : Shell.Type.XY;
		super.invoke(world, slotEntries);
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { " B ", " A ", " B ", 'A', ingredientA, 'B', ingredientB };
	}
}
