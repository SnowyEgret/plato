package ds.plato.item.staff;

import net.minecraft.init.Items;
import ds.plato.api.IPick;

public class StaffBirch extends StaffWood {

	public StaffBirch(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public Object[] getRecipe() {
		//TODO how to make recipe with oak?
		return new Object[] { "#  ", " # ", "  #", '#', Items.baked_potato};
	}

}
