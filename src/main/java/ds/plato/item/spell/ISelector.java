package ds.plato.item.spell;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface ISelector {

	public void select(ItemStack stack, int x, int y, int z, int side);

}
