package ds.plato.spell;

import ds.plato.Plato;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

public class SpellRestore extends Spell {


	public SpellRestore(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(undoManager, selectionManager, pickManager);
	}
	
	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_restore_name;
		d.description = Messages.spell_restore_description;
		d.picks = new PickDescriptor(Messages.spell_pick);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 1, world.getWorld(), 0, 0, 0);
	}

	@Override
	public int getNumPicks() {
		return 1;
	}

	public void readFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

}
