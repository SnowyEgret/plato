package ds.plato.item.spell.select;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
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
	public void invoke(IWorld world, SlotEntry...slotEntries) {
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
