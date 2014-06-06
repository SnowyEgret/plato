package ds.plato.common;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.Plato;
import ds.plato.core.WorldWrapper;
import ds.plato.geom.IDrawable;
import ds.plato.geom.solid.Ball;
import ds.plato.geom.solid.Box;
import ds.plato.geom.solid.Cube;
import ds.plato.geom.solid.MengerSponge;
import ds.plato.geom.solid.RectangularPyramid;
import ds.plato.geom.solid.Tetrahedron;
import ds.plato.select.Selection;
import ds.plato.spell.select.EnumShell;
import ds.plato.spell.select.Shell;
import ds.plato.undo.Transaction;

@Deprecated
public class StickSolid extends Stick {

	public boolean firstPour = true;

	public StickSolid(Property initialState) {
		super(2, initialState, EnumSolid.class);
	}

	@Override
	public void onClickRight(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			IDrawable drawable = null;
			switch ((EnumSolid) state.current()) {
			case BALL:
				drawable = new Ball(getPick(0), getPick(1));
				break;
			case BOX:
				drawable = new Box(getPick(0), getPick(1));
				break;
			case PYRAMID:
				drawable = new RectangularPyramid(getPick(0), getPick(1), Keyboard.isKeyDown(Keyboard.KEY_LMETA));
				break;
			case TETRAHEDRON:
				drawable = new Tetrahedron(getPick(0), getPick(1));
				break;
			case CUBE:
				drawable = new Cube(getPick(0), getPick(1));
				break;
			case SPONGE:
				drawable = new MengerSponge(getPick(0), getPick(1));
				break;
			case WATER:
				pourWater();
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			default:
				break;
			}
			draw(drawable, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)); // Hollow when pressed
			clearPicks();
		} else {
			Plato.clearSelections();
		}
		if (e.isCancelable())
			e.setCanceled(true);
	}

	private void pourWater() {
		World w = Plato.getWorldServer();
		Transaction transaction = Plato.undoManager.newTransaction();

		if (firstPour) {
			Point3d p = getPick(0);
			p.y++;
			SlotEntry e = Plato.getBlocksWithMetadataInIventorySlots().get(0);
			transaction.add(new UndoableSetBlock(p, e.block, e.metadata));
			firstPour = false;
		} else {
			for (Selection s : Plato.selectionManager.getSelections()) {
				Shell shell = new Shell(EnumShell.HORIZONTAL, s.getPoint3i(), new WorldWrapper(w));
				for (Point3i p : shell) {
					Block b = w.getBlock(p.x, p.y, p.z);
					if (b == Blocks.air) {
						transaction.add(new UndoableSetBlock(p, s.block, s.metadata));
					}
				}
				Plato.selectionManager.deselect(s);
			}
			for (Selection s : Plato.selectionManager.getSelections()) {
				Shell shell = new Shell(EnumShell.DOWN, s.getPoint3i(), new WorldWrapper(w));
				for (Point3i p : shell) {
					Block b = w.getBlock(p.x, p.y, p.z);
					if (b == Blocks.air) {
						transaction.add(new UndoableSetBlock(p, s.block, s.metadata));
					}
				}
			}
		}
		transaction.commit();
	}
}
