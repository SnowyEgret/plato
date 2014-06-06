package ds.plato.spell.select;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.block.BlockSelected;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public abstract class AbstractSpellSelect extends Spell {

	private List<Point3i> grownSelections = new ArrayList<>();

	public AbstractSpellSelect(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(undoManager, selectionManager, pickManager);
	}

	// Only clear selections when this is a selection spell. Spell.onItemRightClick only clears picks.
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		System.out.println("[AbstractSpellSelection.onItemRightClick] w=" + w);
		super.onItemRightClick(is, w, player);
		MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
		if (position.typeOfHit == MovingObjectType.MISS) {
			selectionManager.clearSelections();
		}
		return is;
	}

	protected void growSelections(EnumShell shellType, IWorld world, Block patternBlock) {
		if (grownSelections.isEmpty()) {
			grownSelections.addAll(selectionManager.selectedPoints());
		}
		List<Point3i> newGrownSelections = new ArrayList();
		for (Point3i center : grownSelections) {
			Shell shell = new Shell(shellType, center, world);
			for (Point3i p : shell) {
				Block block = world.getBlock(p.x, p.y, p.z);
				if (!(block instanceof BlockAir) && !(block instanceof BlockSelected)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LMETA)) { // Alt
						selectionManager.select(p.x, p.y, p.z);
						newGrownSelections.add(p);
					} else {
						if (block == patternBlock) {
							selectionManager.select(p.x, p.y, p.z);
							newGrownSelections.add(p);
						}
					}
				}
			}
		}
		grownSelections = newGrownSelections;
	}

	protected void shrinkSelections(EnumShell shellType, IWorld world) {
		List<Selection> shrunkSelections = new ArrayList<>();
		for (Selection s : selectionManager.getSelections()) {
			Shell shell = new Shell(shellType, s.getPoint3i(), world);
			for (Point3i p : shell) {
				Block b = world.getBlock(p.x, p.y, p.z);
				if (!(b instanceof BlockSelected)) {
					shrunkSelections.add(s);
					break;
				}

			}
		}
		for (Selection s : shrunkSelections) {
			selectionManager.deselect(s);
		}
		grownSelections.clear();
	}

	// @Override
	// public void onClickRightAir(PlayerInteractEvent e) {
	// System.out.println("[AbstractSpellSelection.onClickRightAir] e=" + e);
	// super.onClickRightAir(e);
	// selectionManager.clearSelections();
	// }

	@Override
	public int getNumPicks() {
		return 0;
	}

}
