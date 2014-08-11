package ds.plato.spell;

import java.io.FileNotFoundException;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import ds.plato.Plato;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.PersistentVoxelGroup.Voxel;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellRestore extends Spell {

	private IWorld world;
	private Pick lastPick;

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
	public void invoke(IWorld world, SlotEntry...slotEntries) {
		this.world = world;
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, 1, world.getWorld(), 0, 0, 0);
		// Clear picks here because player might have cancelled
		lastPick = pickManager.lastPick();
		pickManager.clearPicks();
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	public void restore(String filename) {
		try {
			PersistentVoxelGroup group = PersistentVoxelGroup.read("saves/" + filename + ".json");
			System.out.println("[SpellRestore.readFile] group=" + group);
			Transaction transaction = undoManager.newTransaction();
			Point3i d = new Point3i();
			d.sub(group.insertionPoint, lastPick.point3i());
			//Draw blocks one block up from pick
			d.y -= 1;
			for (Voxel v : group.voxels) {
				String name = v.b;
				Block b = Block.getBlockFromName(name);
				if (b == null) {
					//The block registry seems inconsistent regarding names
					if (name.endsWith("Block")) {
						//Quartz
						System.out.println("[SpellRestore.restore] Try replacing suffix Block with _block");
						name = name.substring(0, name.length()-5) + "_block";
						b = Block.getBlockFromName(name);
					} else if (name.equals("cloth")) {
						System.out.println("[SpellRestore.restore] Try replacing cloth with wool");
						name = "wool";
						b = Block.getBlockFromName(name);
					}
				}
				if (b != null) {
					Point3i p = new Point3i(v.x, v.y, v.z);
					p.sub(d);
					transaction.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, b, v.m).set());
				} else {
					System.out.println("[SpellRestore.restore] Could not get block from name: " + v.b);		
				}
			}
			transaction.commit();
			selectionManager.clearSelections();
			pickManager.clearPicks();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
