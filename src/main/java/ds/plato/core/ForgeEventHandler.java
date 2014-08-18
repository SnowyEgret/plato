package ds.plato.core;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3i;
import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;
import ds.plato.spell.IClickable;
import ds.plato.spell.Spell;
import ds.plato.spell.transform.SpellFill;
import ds.plato.staff.Staff;
import ds.plato.undo.IUndo;

public class ForgeEventHandler {

	private Spell spell = null;
	private IUndo undoManager;
	private ISelect selectionManager;
	private IPick pickManager;
	private Overlay overlay;
	private long nanoseconds = 0;

	public ForgeEventHandler(IUndo undoManager, ISelect selectionManager, IPick pickManager, Overlay overlay) {
		this.selectionManager = selectionManager;
		this.undoManager = undoManager;
		this.pickManager = pickManager;
		this.overlay = overlay;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseEvent(MouseEvent e) {

		//Fix for Clicking back to game selects a block. Issue #100
		//TODO Seems not to work in multiplayer
		if (Minecraft.getMinecraft().isGamePaused()) {
			return;
		}
		
		Player player = Player.getPlayer();
		IWorld world = player.getWorld(); 
		MovingObjectPosition position = Minecraft.getMinecraft().objectMouseOver;
		// System.out.println("[ForgeEventHandler.onMouseEvent] position.typeOfHit=" + position.typeOfHit +
		// ", e.button=" + e.button);

		if (position.typeOfHit == MovingObjectType.MISS) {
			if (e.button == 0) {
				selectionManager.clearSelections();
				e.setCanceled(true);

			} else if (e.button == 1) {
				pickManager.clearPicks();
				e.setCanceled(true);
			}

		} else if (position.typeOfHit == MovingObjectType.BLOCK) {

			ItemStack stack = player.getHeldItemStack();
			if (stack != null) {
				Item item = stack.getItem();

				// IClickable is either a staff or a spell
				if (item instanceof IClickable) {
					IClickable c = (IClickable) item;

					// MouseEvent is being sent twice. Throw out the second.
					if (e.nanoseconds - nanoseconds < 1000000000) {
						nanoseconds = 0;
						e.setCanceled(true);
						return;
					} else {
						nanoseconds = e.nanoseconds;
					}

					// Select
					if (e.button == 0) {
						c.onMouseClickLeft(position);
						e.setCanceled(true);
						return;

						// Pick
					} else if (e.button == 1) {
						Block b = world.getBlock(position.blockX, position.blockY, position.blockZ);
						if (isRightClickable(b)) {
							c.onMouseClickRight(position);
							e.setCanceled(true);
						}
					}

					// Fill the selections with the block in hand
				} else if (item instanceof ItemBlock) {
					ItemBlock itemBlock = (ItemBlock) item;
					if (e.button == 1) {
						if (selectionManager.isSelected(position.blockX, position.blockY, position.blockZ)) {
							Block b = itemBlock.field_150939_a;
							int metadata = item.getDamage(stack);
							// SlotEntry[] slotEntries = new SlotEntry[] { new SlotEntry(b, metadata, 0) };
							// new SpellFill(undoManager, selectionManager, pickManager).invoke(world, slotEntries);
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHightlight(DrawBlockHighlightEvent e) {
		MovingObjectPosition pos = e.target;

		// if (holdable != null) {
		if (spell != null) {
			Point3i p = null;
			Pick pick = pickManager.lastPick();
			if (pick != null) {
				p = pick.point3i();
			}
			if (p == null) {
				Selection s = selectionManager.firstSelection();
				if (s != null) {
					p = s.point3i();
				}
			}
			if (p != null) {
				Vector3d d = new Vector3d();
				d.x = p.x - pos.blockX;
				d.y = p.y - pos.blockY;
				d.z = p.z - pos.blockZ;
				overlay.setDisplacement(d);
			}
		}
	}

	// TODO Player.getSpell(), discard interface IHoldable. Ignore an empty staff. Overlay.draw(Spell s)
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		World w = e.entity.worldObj;
		if (!w.isRemote) {
			return;
		}
		if (e.entity instanceof EntityPlayer) {
			Spell s = Player.getPlayer().getSpell();
			if (s == null) {
				spell = null;
			} else {
				if (s != spell) {
					spell = s;
					s.reset();
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
		if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			if (spell != null) {
				overlay.drawSpell(spell);
			} else {
				Player p = Player.getPlayer();
				Staff staff = p.getStaff();
				if (staff != null) {
					overlay.drawStaff(staff, p.getHeldItemStack());
				}
			}
		}
	}

	// @SubscribeEvent
	// public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
	// }

	private boolean isRightClickable(Block block) {
		if (block instanceof BlockWorkbench)
			return false;
		if (block instanceof BlockContainer)
			return false;
		if (block instanceof BlockAnvil)
			return false;
		return true;
	}

	// Copied from class Player
	private Iterable<ItemStack> getStaffItemStacks(EntityPlayer player) {
		List<ItemStack> stacks = new ArrayList<>();
		InventoryPlayer inventory = player.inventory;
		for (ItemStack s : inventory.mainInventory) {
			if (s != null) {
				if (Staff.class.isAssignableFrom(s.getItem().getClass())) {
					System.out.println("[ForgeEventHandler.getStaffItemStacks] s=" + s);
					stacks.add(s);
				}
			}
		}
		return stacks;
	}
}
