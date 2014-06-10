package ds.plato.spell;

import java.io.IOException;

import javax.vecmath.Point3i;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import ds.plato.Plato;
import ds.plato.core.IO;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.core.WorldWrapper;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.transform.SpellDelete;
import ds.plato.undo.IUndo;

public class SpellSave extends Spell {

	public SpellSave(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 0, world.getWorld(), 0, 0, 0);
	}

	// Called by GuiSave
	public void writeFile(String name) {
		// TODO could create a new item in the players inventory. Could be named with an anvil like a sword. Could have
		// a texture generated from the player's view at the time of creation
		Pick[] picks = pickManager.getPicksArray();
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		String json = null;
		try {
			Pick p = picks[0];
			json = IO.writeGroup(new Point3i(p.x, p.y, p.z), selectionManager.getSelectionList(), "saves/" + name + ".json");
			System.out.println("[SpellSave.writeFile] json=" + json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pickManager.clearPicks();
		SpellDelete s = new SpellDelete((IUndo) undoManager, selectionManager, pickManager);
		pickManager.reset(s.getNumPicks());
		s.invoke(new WorldWrapper(player.worldObj), null);
		// SpellRegenerate i = new SpellRegenerate(undoManager, selectionManager, pickManager, json);
		// GameRegistry.registerItem(i, name);
		// i.setCreativeTab(SpellLoader.tabSpells);
		// i.setUnlocalizedName(name);
		// System.out.println("[SpellSave.writeFile] i=" + i);
		// player.displayGUIAnvil(0, 0, 0);
		// player.dropItem(i, 7);
		//pickManager.clearPicks();
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_save_name;
		d.description = Messages.spell_save_description;
		d.picks = new PickDescriptor(Messages.spell_pick);
		d.modifiers = new ModifierDescriptor(Messages.spell_save_modifier);
		return d;
	}
}
