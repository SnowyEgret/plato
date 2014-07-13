package ds.plato.spell;

import static org.junit.Assert.*;

import org.junit.Test;

import ds.plato.test.PlatoTest;

public class SpellTest extends PlatoTest {

	@Test
	public void getRecipe() {
		Spell s = new SpellHoleDrain(undoManager, selectionManager, pickManager);
		System.out.println("[T_Spell.test] s.getRecipe()=" + s.getRecipe());
	}

}
