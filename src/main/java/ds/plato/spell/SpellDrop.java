package ds.plato.spell;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.descriptor.Modifier;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellDrop extends Spell {


	public SpellDrop(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
		info.addModifiers(Modifier.CTRL, Modifier.ALT);
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		boolean fill = Keyboard.isKeyDown(Keyboard.KEY_LMENU);
		boolean deleteOriginal = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		Transaction transaction = undoManager.newTransaction();
		for (Selection s : selectionManager.getSelections()) {
			Block b = world.getBlock(s.x, s.y - 1, s.z);
			int drop = 0;
			while (b == Blocks.air) {
				drop++;
				Block nextBlockDown = world.getBlock(s.x, s.y - drop - 1, s.z);
				if (fill || nextBlockDown != Blocks.air) {
					transaction.add(new SetBlock(world, selectionManager, s.x, s.y - drop, s.z, s.block, s.metadata)
							.set());
				}
				b = nextBlockDown;
			}
			selectionManager.deselect(s);
			if (deleteOriginal) {
				transaction.add(new SetBlock(world, selectionManager, s.x, s.y, s.z, Blocks.air, 0).set());
			}
		}
		transaction.commit();
		pickManager.clearPicks();
	}

	@Override
	public Object[] getRecipe() {
		return null;
	}

//	@Override
//	public IModelCustom getModel() {
//		return model;
//	}

}
