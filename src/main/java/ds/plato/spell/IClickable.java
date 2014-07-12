package ds.plato.spell;

import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface IClickable {

	@Deprecated
	public void onClickLeft(PlayerInteractEvent e);

	@Deprecated
	public void onClickRight(PlayerInteractEvent e);

	@Deprecated
	public void onClickRightAir(PlayerInteractEvent e);

	public void onMouseClickLeft(MovingObjectPosition position);

}
