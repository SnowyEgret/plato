package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.Transformer;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public class DeleteSpell extends Spell {

	private Block blockAir;

	public DeleteSpell(
			SpellDescriptor descriptor,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager,
			Block blockAir) {
		super(descriptor, undoManager, selectionManager, pickManager);
		this.blockAir = blockAir;
	}

	@Override
	public void encant(PlayerInteractEvent playerInteractEvent) {
		transformSelections(new Transformer() {
			@Override
			public Selection transform(Selection s) {
				// Create a copy here because we don't want to modify the selectionManager's selection list.
				return new Selection(s.x, s.y, s.z, blockAir, 0);
			}
		});
	}
}
