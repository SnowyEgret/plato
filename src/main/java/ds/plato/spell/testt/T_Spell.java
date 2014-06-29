package ds.plato.spell.testt;

import static org.junit.Assert.*;

import org.junit.Test;

import ds.plato.spell.Spell;
import ds.plato.spell.SpellHoleDrain;
import ds.plato.test.PlatoTest;

public class T_Spell extends PlatoTest {

	@Test
	public void getRecipe() {
		Spell s = new SpellHoleDrain(undoManager, selectionManager, pickManager);
		System.out.println("[T_Spell.test] s.getRecipe()=" + s.getRecipe());
	}

}
