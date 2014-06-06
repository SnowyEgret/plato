package ds.plato.spell;

import java.io.IOException;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import ds.plato.Plato;
import ds.plato.core.IO;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellSave extends Spell {

	public SpellSave(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir air) {
		super(undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 0, world.getWorld(), 0, 0, 0);
		String fileName = "saves/test.json";
		try {
			String json = IO.writeGroup(picks[0], selectionManager.getSelectionList(), fileName);
			System.out.println("[ItemStickEdit.saveSelections] json=" + json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getNumPicks() {
		return 1;
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_save_name;
		d.description = Messages.spell_save_description;
		d.picks = new PickDescriptor(Messages.spell_save_picks);
		return d;
	}
}
