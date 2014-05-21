package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Staff {

	List<Spell> spells = new ArrayList<>();
	private int ordinal = 0;

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

	public void onClickRight(PlayerInteractEvent e) {
		currentSpell().encant(e);
	}

	public Spell nextSpell() {
		if (ordinal == spells.size() - 1) {
			ordinal = 0;
		} else {
			ordinal++;
		}
		return currentSpell();

	}

	public int numSpells() {
		return spells.size();
	}

}
