package ds.plato.spell;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface IClickable {

	public void onMouseClickLeft(ItemStack stack, MovingObjectPosition position);

	public void onMouseClickRight(ItemStack stack, MovingObjectPosition position);

}
