package ds.plato.spell;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

import org.lwjgl.input.Keyboard;

import ds.plato.Plato;
import ds.plato.core.IO;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.gui.ITextSetable;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.undo.IUndo;

public class SpellSave extends Spell implements ITextSetable {

	public SpellSave(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 0, world.getWorld(), 0, 0, 0);
	}

	@Override
	public void setText(String text) {
		// TODO could create a new item in the players inventory. Could be named with an anvil like a sword. Could have
		// a texture generated from the player's view at the time of creation
		Pick[] picks = pickManager.getPicksArray();
		String json = null;
		try {
			json = IO.writeGroup(picks[0].getPoint3i(), selectionManager.getSelectionList(), "saves/" + text + ".json");
			System.out.println("[SpellSave.writeFile] json=" + json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pickManager.clearPicks();

		boolean deleteOriginal = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		if (deleteOriginal) {
			SpellDelete s = new SpellDelete(undoManager, selectionManager, pickManager);
			pickManager.reset(s.getNumPicks());
			// Can't remember why this world is preferable, or maybe I just didn't set world and slotEntries in invoke
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			s.invoke(new WorldWrapper(player.worldObj), null);
		}
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_save_name;
		d.description = Messages.spell_save_description;
		d.picks = new PickDescriptor(Messages.spell_save_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_save_modifier);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
