package ds.plato.spell;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.Transformer;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public class DeleteSpell extends Spell {

	public DeleteSpell(
			SpellDescriptor descriptor,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager) {
		super(descriptor, undoManager, selectionManager, pickManager);
	}
	
	@Override
	public void encant(PlayerInteractEvent playerInteractEvent) {
		transformSelections(new Transformer() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selectionManager's selection list.
				return new Selection(s.x, s.y, s.z, Blocks.air, 0);
			}
		});
	}
}
