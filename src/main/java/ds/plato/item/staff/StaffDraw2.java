package ds.plato.item.staff;

import java.util.List;

import net.minecraft.init.Items;
import ds.plato.api.IPick;
import ds.plato.item.spell.Spell;

public class StaffDraw2 extends StaffPreset {

	public StaffDraw2(IPick pickManager, List<Spell> spells) {
		super(pickManager, spells);
	}
	
	public Object[] getRecipe() {
		return new Object[] { "#  ", " # ", "  #", '#', Items.bone };
	}
}
