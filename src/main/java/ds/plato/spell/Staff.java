package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

import ds.plato.pick.IPick;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Staff {

	List<AbstractSpell> spells = new ArrayList<>();
	private int ordinal = 0;
	private IPick pickManager;

	public Staff(IPick pickManager) {
		this.pickManager = pickManager;
	}

	public AbstractSpell currentSpell() {
		// if (spells.isEmpty()) {
		// return null;
		// } else {
		return spells.get(ordinal);
		// }
	}

	public void addSpell(AbstractSpell spell) {
		if (!spells.contains(spell)) {
			spells.add(spell);
		}
	}

	public void onClickRight(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			currentSpell().invoke(pickManager.getPicksArray());
		}
	}

	public AbstractSpell nextSpell() {
		if (ordinal == spells.size() - 1) {
			ordinal = 0;
		} else {
			ordinal++;
		}
		AbstractSpell currentSpell = currentSpell();
		pickManager.reset(currentSpell.getNumPicks());
		return currentSpell;

	}

	public int numSpells() {
		return spells.size();
	}

	public boolean pick(int x, int y, int z) {
		// TODO: Handle case where location is already a selection
		if (!pickManager.isFinishedPicking()) {
			// Block block = world.getBlock(x, y, z);
			// TODO commented out for now. PickManager could pick a block like SelectionManager selects a block.
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
