package ds.plato.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.common.IO.Group;
import ds.plato.common.IO.Voxel;
import ds.plato.geom.GeomUtil;
import ds.plato.geom.VoxelSet;
import ds.plato.select.Selection;
import ds.plato.undo.Transaction;

@Deprecated
public class StickEdit extends Stick {

	public StickEdit(Property initialState) {
		super(2, initialState, EnumEdit.class);
	}

	@Override
	protected void onClickRight(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			Matrix4d matrix = null;
			Point3d p = null;
			switch ((EnumEdit) state.current()) {
			case MOVE:
				matrix = GeomUtil.newTranslationMatrix(getPick(0), getPick(1));
				break;
			case ROTATE:
				matrix = GeomUtil.newRotationMatrix(getPick(0), getPick(1), getPick(2));
				break;
			case ROT90:
				if (Keyboard.isKeyDown(Keyboard.KEY_LMETA)) {
					Point3i c = Plato.selectionManager.voxelSet().centroid();
					p = new Point3d(c.x, c.y, c.z);
				} else {
					p = getPick(0);
				}

				if (Keyboard.isKeyDown(Keyboard.KEY_X))
					matrix = GeomUtil.newRotX90Matrix(p);
				else if (Keyboard.isKeyDown(Keyboard.KEY_Y))
					matrix = GeomUtil.newRotY90Matrix(p);
				else if (Keyboard.isKeyDown(Keyboard.KEY_Z))
					matrix = GeomUtil.newRotZ90Matrix(p);
				else
					matrix = GeomUtil.newRotY90Matrix(p);
				break;
			case MIRROR:
				matrix = GeomUtil.newReflectionMatrix(getPick(0), getPick(1), getPick(2));
				break;
			case MIRROR_PLANE:
				p = getPick(0);

				// if (Keyboard.isKeyDown(Keyboard.KEY_X))
				// matrix = GeomUtil.newReflectionXMatrix(p);
				// else if (Keyboard.isKeyDown(Keyboard.KEY_Y))
				// matrix = GeomUtil.newReflectionYMatrix(p);
				// else if (Keyboard.isKeyDown(Keyboard.KEY_Z))
				// matrix = GeomUtil.newReflectionZMatrix(p);
				// else
				Point3i c = Plato.selectionManager.voxelSet().centroid();
				matrix = GeomUtil.newReflectionMatrix(new Point3d(c.x, c.y, c.z), p);
				break;
			case SCALE:
				matrix = GeomUtil.newScaleMatrix(getPick(0), getPick(1), getPick(2));
				break;
			case HOLLOW:
				hollowSelections();
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case DELETE:
				deleteSelections();
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case FILL:
				// MOD.log.info("[ItemStickEdit.onClickRight] e.entity.worldObj=" + e.entity.worldObj);
				fillSelections(Plato.getBlocksWithMetadataInIventorySlots().get(0));
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case FILL_CHECKER:
				fillChecker(Plato.getBlocksWithMetadataInIventorySlots());
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case FILL_RANDOM:
				fillRandom(Plato.getBlocksWithMetadataInIventorySlots());
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case DROP:
				dropSelections();
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case SAVE:
				writeGroup(new Point3i(e.x, e.y, e.z));
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case LOAD:
				readGroup(new Point3i(e.x, e.y, e.z));
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			case FILL_BELOW:
				clearPicks();
				fillBelowSelections();
				if (e.isCancelable())
					e.setCanceled(true);
				return;
			default:
				break;
			}
			// clearPicks();
			transformSelections(matrix, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL));
		}
		if (e.isCancelable())
			e.setCanceled(true);
	}

	@Override
	public String toString() {
		return "ItemStickEdit [state=" + state.current() + "]";
	}

	public void deleteSelections() {
		fillSelections(Blocks.air, 0);
		Plato.clearSelections();
	}

	public void fillSelections(SlotEntry slotEntry) {
		fillSelections(slotEntry.block, slotEntry.metadata);
	}

	public void fillSelections(final Block block, final int metadata) {
		transformSelections(new ITransformer() {
			@Override
			public Selection transform(Selection s) {
				//Create a copy here because we don't want to modify the selectionManager's selection list.
				return new Selection(s.x, s.y, s.z, block, metadata);
//				s.block = block;
//				s.metadata = metadata;
				//return s;

			}
		});
	}

	public void fillChecker(final List<SlotEntry> list) {
		transformSelections(new ITransformer() {
			@Override
			public Selection transform(Selection s) {
				int index = 0;
				if (((s.x & 1) == 0 && (s.z & 1) == 0) || ((s.x & 1) == 1 && (s.z & 1) == 1)) {
					index = ((s.y & 1) == 0) ? 0 : 1;
				} else {
					index = ((s.y & 1) == 0) ? 1 : 0;
				}
				SlotEntry entry = list.get(index);
				s.block = entry.block;
				s.metadata = entry.metadata;
				return s;
			}
		});
	}

	private void fillRandom(final List<SlotEntry> list) {
		transformSelections(new ITransformer() {
			Random r = new Random();

			@Override
			public Selection transform(Selection s) {
				SlotDistribution d = Plato.slotDistribution;
				SlotEntry entry = d.randomEntry();
				s.block = entry.block;
				s.metadata = entry.metadata;
				return s;
			}
		});
	}

	private void hollowSelections() {
		transformSelections(new ITransformer() {
			VoxelSet selections = Plato.selectionManager.voxelSet();

			@Override
			public Selection transform(Selection s) {
				List surroundingPoints = new ArrayList();
				surroundingPoints.add(new Point3i(s.x + 1, s.y, s.z));
				surroundingPoints.add(new Point3i(s.x - 1, s.y, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y + 1, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y - 1, s.z));
				surroundingPoints.add(new Point3i(s.x, s.y, s.z + 1));
				surroundingPoints.add(new Point3i(s.x, s.y, s.z - 1));
				if (selections.containsAll(surroundingPoints)) {
					s.block = Blocks.air;
				}
				return s;
			}
		});
	}

	private void dropSelections() {
		transformSelections(new ITransformer() {
			@Override
			public Selection transform(Selection s) {
				World w = Plato.getWorldServer();
				int drop = 0;
				Block b = w.getBlock(s.x, s.y - 1, s.z);
				while (b == Blocks.air) {
					drop += 1;
					b = w.getBlock(s.x, s.y - drop, s.z);
				}
				if (drop != 0 || w.getBlock(s.x, s.y - 1, s.z) == Blocks.air) {
					w.setBlock(s.x, s.y, s.z, Blocks.air);
					s.y = s.y - drop + 1;
				}
				return s;
			}
		});
	}

	private void fillBelowSelections() {
		World w = Plato.getWorldServer();
		Transaction transaction = Plato.undoManager.newTransaction();
		for (Selection s : Plato.selectionManager.getSelections()) {
			Block b = w.getBlock(s.x, s.y - 1, s.z);
			int drop = 0;
			while (b == Blocks.air) {
				drop++;
				//Causing NullPointerException when selection is returned null in deselectBlockAt
				//MOD.selectionStick.deselectBlockAt(s.getPoint3i());
				Plato.selectionManager.deselect(s);
				transaction.add(new UndoableSetBlock(s.x, s.y - drop, s.z, s.block, s.metadata));
				b = w.getBlock(s.x, s.y - drop -1, s.z);
			}
		}
		transaction.commit();
	}

	private void writeGroup(Point3i insertionPoint) {
		String fileName = "saves/test.json";
		try {
			String json = IO.writeGroup(insertionPoint, Plato.selectionManager.getSelectionList(), fileName);
			Plato.log.info("[ItemStickEdit.saveSelections] json=" + json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readGroup(Point3i insertionPoint) {
		String fileName = "saves/test.json";
		Group group = null;
		try {
			group = IO.readGroup(fileName);
			Plato.log.info("[ItemStickEdit.load] group=" + group);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		Transaction transaction = Plato.undoManager.newTransaction();
		Point3i d = new Point3i();
		d.sub(group.insertionPoint, insertionPoint);
		for (Voxel v : group.voxels) {
			Block b = Block.getBlockFromName(v.b);
			Plato.log.info("[ItemStickEdit.load] v.b=" + v.b);
			Plato.log.info("[ItemStickEdit.load] b=" + b);
			if (b != null) {
				Point3i p = new Point3i(v.x, v.y, v.z);
				p.sub(d);
				p.y += 1;
				Plato.log.info("[ItemStickEdit.load] p=" + p);
				transaction.add(new UndoableSetBlock(p, b, v.m));
			}
		}
		transaction.commit();
	}

}
