package ds.plato.item.staff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ds.plato.api.IPick;
import ds.plato.item.spell.Spell;
import ds.plato.item.spell.select.AbstractSpellSelect;

public class StaffSelect extends Staff {

	public StaffSelect(IPick pickManager) {
		super(pickManager);
	}

	@Override
	public void addSpell(Spell spell) {
		assert spell instanceof AbstractSpellSelect;
		super.addSpell(spell);
	}

	@Override
	public boolean hasRecipe() {
		return false;
	}
	
}
