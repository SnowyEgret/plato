package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import ds.plato.IWorld;
import ds.plato.common.BlockPick;
import ds.plato.common.ISelect;
import ds.plato.common.IToggleable;
import ds.plato.common.Plato;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Staff extends Item implements IClickable, IToggleable, IHoldable {

	private List<Spell> spells = new ArrayList<>();
	private int ordinal = 0;
	private IPick pickManager;
	private IWorld world;

	public Staff(IPick pickManager) {
		this.pickManager = pickManager;
	}

	public Staff setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	@Override
	public void onClickLeft(PlayerInteractEvent e) {
		currentSpell().onClickLeft(e);
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		if (pickManager.pick(e.x, e.y, e.z)) {
			currentSpell().onClickRight(e);
			// currentSpell().invoke(pickManager.getPicksArray());
		}
	}

	@Override
	public void toggle() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			previousSpell();
		} else {
			nextSpell();
		}
	}

	public Spell currentSpell() {
		if (spells.isEmpty()) {
			return null;
		} else {
			return spells.get(ordinal);
		}
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

	public Spell previousSpell() {
		if (ordinal == 0) {
			ordinal = spells.size() - 1;
		} else {
			ordinal--;
		}
		Spell currentSpell = currentSpell();
		pickManager.reset(currentSpell.getNumPicks());
		return currentSpell;
	}

	public int numSpells() {
		return spells.size();
	}

//	// TODO Move pick and clearPicks to pickManager. Staff would not need world and BlockPick
//	public boolean pick(int x, int y, int z) {
//		// TODO: Handle case where location is already a selection
//		if (!pickManager.isFinishedPicking()) {
//			Block block = world.getBlock(x, y, z);
//			int metatdata = world.getMetadata(x, y, z);
//			// TODO pass BlockPick
//			world.setBlock(x, y, z, Plato.blockPicked, 0, 3);
//			// TODO add metatdata to Pick constructor
//			pickManager.addPick(x, y, z, block);
//		}
//		return pickManager.isFinishedPicking();
//	}

	public void clearPicks() {
		pickManager.clearPicks();
	}

	@Override
	public String toString() {
		return "Staff [spells=" + spells + ", ordinal=" + ordinal + ", pickManager=" + pickManager + "]";
	}

	@Override
	public AbstractSpellDescriptor getDescriptor() {
		Spell s = currentSpell();
		if (s == null) {
			return new EmptyStaffDescriptor();
		} else {
			return s.descriptor;
		}
	}

	@Override
	public boolean isPicking() {
		Spell s = currentSpell();
		if (s == null) {
			return false;
		} else {
			return s.isPicking();
		}
	}

	@Override
	public ISelect getSelectionManager() {
		return currentSpell().selectionManager;
	}
	
	private class EmptyStaffDescriptor extends AbstractSpellDescriptor {
	}	
}
