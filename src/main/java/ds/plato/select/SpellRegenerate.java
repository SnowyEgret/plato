package ds.plato.select;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.spell.Messages;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import net.minecraft.block.BlockAir;
import net.minecraft.item.Item;

public class SpellRegenerate extends Spell {

	private String json;

	public SpellRegenerate(IUndo undoManager, ISelect selectionManager, IPick pickManager, BlockAir air, String json) {
		super(undoManager, selectionManager, pickManager);
		this.json = json;
	}
	

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_regenerate_name;
		d.description = Messages.spell_regenerate_description;
		d.picks = new PickDescriptor(Messages.spell_regenerate_picks);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		System.out.println("[ItemSave.invoke] json=" + json);
	}

	@Override
	public int getNumPicks() {
		// TODO Auto-generated method stub
		return 1;
	}

}
