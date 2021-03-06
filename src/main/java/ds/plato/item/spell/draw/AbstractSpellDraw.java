package ds.plato.item.spell.draw;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import ds.plato.api.IPick;
import ds.plato.api.ISelect;
import ds.plato.api.IUndo;
import ds.plato.api.IWorld;
import ds.plato.geom.IDrawable;
import ds.plato.geom.VoxelSet;
import ds.plato.geom.solid.Solid;
import ds.plato.item.spell.Spell;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;
import ds.plato.util.StringUtils;

public abstract class AbstractSpellDraw extends Spell {

	public AbstractSpellDraw(int numPicks, IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(numPicks, undoManager, selectionManager, pickManager);
	}

	protected void draw(IDrawable drawable, IWorld world, Block block, int metadata) {
		draw(drawable, world, block, metadata, false);
	}

	protected void draw(IDrawable drawable, IWorld world, Block block, int metadata, boolean isHollow) {
		Transaction t = undoManager.newTransaction();
		VoxelSet voxels = drawable.voxelize();
		if (drawable instanceof Solid && isHollow) {
			voxels = voxels.shell();
		}
		for (Point3i p : voxels) {
			t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, block, metadata).set());
		}
		t.commit();
		
		//Try playing a sound
		String sound = "plato:"+StringUtils.toCamelCase(getClass());
		world.getWorld().playSoundAtEntity(Minecraft.getMinecraft().thePlayer,sound , 1f, 1f);
	}

}
