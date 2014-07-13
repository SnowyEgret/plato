package ds.plato.spell;

import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface IClickable {

	public void onMouseClickLeft(MovingObjectPosition position);

	public void onMouseClickRight(MovingObjectPosition position);

}
