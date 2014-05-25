package ds.plato.spell;

import javax.vecmath.Matrix4d;

import org.lwjgl.input.Keyboard;

import ds.geom.GeomUtil;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.SelectionManager;
import ds.plato.common.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MoveSpell extends AbstractMatrixTransformationSpell {

	public MoveSpell(
			SpellDescriptor descriptor,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager,
			Block blockAir) {
		super(descriptor, undoManager, selectionManager, pickManager, blockAir);
	}

	@Override
	public void invoke(Pick[] picks, SlotEntry[] slotEntries) {
		assert picks.length == getNumPicks();
		Matrix4d matrix = GeomUtil.newTranslationMatrix(picks[0].toDouble(), picks[1].toDouble());
		transformSelections(matrix, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
	}

	@Override
	public int getNumPicks() {
		return 2;
	}
}
