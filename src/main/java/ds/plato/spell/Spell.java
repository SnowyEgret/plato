package ds.plato.spell;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;

public abstract class Spell extends Item implements IClickable {

	public SpellDescriptor descriptor;
	protected IWorld world;
	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;

	public Spell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		this.descriptor = descriptor;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	// Spell can only be partially constructed during FML initialization. The world is only available after the player
	// joins the game.
	public Spell setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		if (pickManager.isFinishedPicking()) {
			invoke(pickManager.getPicksArray());
		}
	}

	public SpellDescriptor getDescriptor() {
		return descriptor;
	}

	// For Staff.addSpell(). Only one spell of each class in spells list
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
			return true;
		return false;
	}

	//TODO Maybe this is protected and staff sends its PlayerInteractEvent to the onClickRight.
	public abstract void invoke(Pick[] picks);

	public abstract int getNumPicks();

}
