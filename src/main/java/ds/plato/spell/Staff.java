package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import ds.plato.pick.IPick;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Staff extends Item implements IClickable {

	private List<Spell> spells = new ArrayList<>();
	private int ordinal = 0;
	private IPick pickManager;

	public Staff(IPick pickManager) {
		this.pickManager = pickManager;
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			//TODO
			//currentSpell().onClickRight(e);
			currentSpell().invoke(pickManager.getPicksArray());
		}
	}

	public Spell currentSpell() {
		// if (spells.isEmpty()) {
		// return null;
		// } else {
		return spells.get(ordinal);
		// }
	}

	public void addSpell(Spell spell) {
		if (!spells.contains(spell)) {
			spells.add(spell);
		}
	}

	public Spell nextSpell() {
		if (ordinal == spells.size() - 1) {
			ordinal = 0;
		} else {
			ordinal++;
		}
		Spell currentSpell = currentSpell();
		pickManager.reset(currentSpell.getNumPicks());
		return currentSpell;

	}

	public int numSpells() {
		return spells.size();
	}

	public boolean pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		if (!pickManager.isFinishedPicking()) {
			// TODO commented out for now. PickManager could pick a block like SelectionManager selects a block.
			// Block block = world.getBlock(x, y, z);
			// blockPick0 could be injected during construction
			// world.setBlock(x, y, z, Plato.blockPick0);
			// pickManager.pick(x, y, z, block);
		}
		return pickManager.isFinishedPicking();
	}

	@Override
	public String toString() {
		return "Staff [spells=" + spells + ", ordinal=" + ordinal + ", pickManager=" + pickManager + "]";
	}

//	protected Point3d getPick(int i) {
//		return pickManager.getPick(i).toDouble();
//	}

}
