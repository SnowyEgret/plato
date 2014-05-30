package ds.plato.spell;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface IClickable {

	public void onClickLeft(PlayerInteractEvent e);

	public void onClickRight(PlayerInteractEvent e);

	public void onClickRightAir(PlayerInteractEvent e);

}
