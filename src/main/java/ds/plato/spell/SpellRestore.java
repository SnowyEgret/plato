package ds.plato.spell;

import java.io.FileNotFoundException;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import ds.plato.Plato;
import ds.plato.core.IO;
import ds.plato.core.IO.Group;
import ds.plato.core.IO.Voxel;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellRestore extends Spell {

	private IWorld world;

	public SpellRestore(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(1, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_restore_name;
		d.description = Messages.spell_restore_description;
		d.picks = new PickDescriptor(Messages.spell_restore_pick);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 1, world.getWorld(), 0, 0, 0);
		this.world = world;
	}

	public void readFile(String filename) {
		try {
			Group group = IO.readGroup("saves/" + filename + ".json");
			System.out.println("[SpellRestore.readFile] group=" + group);
			Transaction transaction = Plato.undoManager.newTransaction();
			Point3i d = new Point3i();
			d.sub(group.insertionPoint, pickManager.lastPick());
			for (Voxel v : group.voxels) {
				Block b = Block.getBlockFromName(v.b);
				if (b != null) {
					Point3i p = new Point3i(v.x, v.y, v.z);
					p.sub(d);
					p.y += 1;
					transaction.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, b, v.m).set());
				}
			}
			transaction.commit();			
			pickManager.clearPicks();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

}
