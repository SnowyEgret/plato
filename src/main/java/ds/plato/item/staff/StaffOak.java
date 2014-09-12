package ds.plato.item.staff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ds.plato.api.IPick;

public class StaffOak extends Staff {

	public StaffOak(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public Object[] getRecipe() {
		//TODO how to make recipe with oak?
		return new Object[] { "#  ", " # ", "  #", '#', Items.apple};
	}

}
