package ds.plato.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.MouseEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.core.IWorld;
import ds.plato.core.Player;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.spell.IClickable;
import ds.plato.spell.transform.SpellFill;
import ds.plato.undo.IUndo;

public class MouseHandler {

	private long nanoseconds = 0;
	private IUndo undoManager;

	public MouseHandler(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super();
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
	}

	private ISelect selectionManager;
	private IPick pickManager;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseEvent(MouseEvent e) {

		System.out.println("[MouseHandler.onMouseEvent] e=" + e);

		// Fix for Clicking back to game selects a block. Issue #100
		// TODO Seems not to work in multiplayer
		if (Minecraft.getMinecraft().isGamePaused()) {
			return;
		}

		Player player = Player.getPlayer();
		IWorld world = player.getWorld();
		MovingObjectPosition p = Minecraft.getMinecraft().objectMouseOver;
		// System.out.println("[ForgeEventHandler.onMouseEvent] position.typeOfHit=" + position.typeOfHit +
		// ", e.button=" + e.button);

		if (p.typeOfHit == MovingObjectType.MISS) {
			if (e.button == 0) {
				selectionManager.clearSelections();

			} else if (e.button == 1) {
				pickManager.clearPicks();
			}
			//e.setCanceled(true);

		} else if (p.typeOfHit == MovingObjectType.BLOCK) {

			ItemStack stack = player.getHeldItemStack();
			if (stack != null) {
				Item item = stack.getItem();

				// IClickable is either a staff or a spell
				if (item instanceof IClickable) {
					IClickable c = (IClickable) item;

					// MouseEvent is being sent twice. Throw out the second.
//					if (e.nanoseconds - nanoseconds < 1000000000) {
//						nanoseconds = 0;
//						e.setCanceled(true);
//						return;
//					} else {
//						nanoseconds = e.nanoseconds;
//					}

					if (e.button == 0) {
						// Select
						c.onMouseClickLeft(stack, p.blockX, p.blockY, p.blockZ, p.sideHit);
						e.setCanceled(true);

					} else if (e.button == 1) {
						// Pick
						// Do not cancel event so that onItemUse is called
						// Block b = world.getBlock(p.blockX, p.blockY, p.blockZ);
						// if (isRightClickable(b)) {
						// if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
						// // Do not cancel event so that onItemRightClick is called
						// return;
						// } else {
						// 
						// return;
						// //c.onMouseClickRight(stack, p.blockX, p.blockY, p.blockZ, p.sideHit);
						// //e.setCanceled(true);
						// }
						// }
					}

				} else if (item instanceof ItemBlock) {
					ItemBlock itemBlock = (ItemBlock) item;
					if (e.button == 1) {
						// Fill the selections with the block in hand
						if (selectionManager.isSelected(p.blockX, p.blockY, p.blockZ)) {
							Block b = itemBlock.field_150939_a;
							int metadata = item.getDamage(stack);
							new SpellFill(undoManager, selectionManager, pickManager).invoke(world, new SlotEntry(b,
									metadata, 0));
							e.setCanceled(true);
						}
					} else if (e.button == 0) {
						System.out.println("[ForgeEventHandler.onMouseEvent] Left mouse button with block in hand");
					}
				}
			}
		}
	}

	private boolean isRightClickable(Block block) {
		if (block instanceof BlockWorkbench)
			return false;
		if (block instanceof BlockContainer)
			return false;
		if (block instanceof BlockAnvil)
			return false;
		return true;
	}

}
