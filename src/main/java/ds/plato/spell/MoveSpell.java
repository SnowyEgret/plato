package ds.plato.spell;

import javax.vecmath.Matrix4d;

import org.lwjgl.input.Keyboard;

import ds.geom.GeomUtil;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SelectionManager;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MoveSpell extends Spell {

	public MoveSpell(
			SpellDescriptor descriptor,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}

	@Override
	public void encant(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			Matrix4d matrix = GeomUtil.newTranslationMatrix(getPick(0), getPick(1));
			transformSelections(matrix, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
		}
		System.out.println("[MoveSpell.encant] event" + "=" + e);
	}

	// private static SpellDescriptor newSpellDescriptor() {
	// String name = "MOVE";
	// String description = "Moves selection";
	// PickDescriptor picks = new PickDescriptor("From", "To");
	// ModifierDescriptor modifiers = new ModifierDescriptor("ctrl", "Deletes originial");
	// SpellDescriptor d = new SpellDescriptor(name, description, picks, modifiers);
	// return d;
	// }

}
