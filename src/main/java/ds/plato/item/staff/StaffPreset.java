package ds.plato.item.staff;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ds.plato.api.IPick;
import ds.plato.item.spell.Spell;

public class StaffPreset extends StaffWood {


	private List<Spell> spells;

	public StaffPreset(IPick pickManager, List<Spell> spells) {
		super(pickManager);
		this.spells = spells;
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.setTagCompound(new NBTTagCompound());
		int i = 0;
		for (Spell s : spells) {
			if (i < 9) {
			String n = s.getClass().getSimpleName();
			stack.stackTagCompound.setString(String.valueOf(i), n);
			i++;
			} else {
				System.out.println("[StaffPreset.onCreated] No room on staff for spell " + s);
			}
		}
		stack.stackTagCompound.setInteger("o=", 0);
	}
}
