package ds.plato.core;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.Plato;
import ds.plato.gui.Overlay;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.IClickable;
import ds.plato.spell.IHoldable;
import ds.plato.spell.Spell;
import ds.plato.spell.select.AbstractSpellSelect;
import ds.plato.spell.transform.SpellFill;
import ds.plato.undo.IUndo;

public class ForgeEventHandler {

	private IHoldable holdable = null;
	private IUndo undoManager;
	private ISelect selectionManager;
	private IPick pickManager;
	private boolean isWorldSet = false;
	private Overlay overlay;

	public ForgeEventHandler(IUndo undoManager, ISelect selectionManager, IPick pickManager, Overlay overlay) {
		this.selectionManager = selectionManager;
		this.undoManager = undoManager;
		this.pickManager = pickManager;
		this.overlay = overlay;
	}

	// @SideOnly(Side.CLIENT)
	// @SubscribeEvent
	// public void onMouseEvent(MouseEvent e) {
	// // Works but is called more often than PlayerInteractEvent
	// System.out.println("[ForgeEventHandle.onMouseEvent] e=" + e.getCurrentTarget());
	// }

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent e) {
		if (!isWorldSet && e.entity instanceof EntityPlayerMP) {
			// Minecraft's world does not implement IWorld
			IWorld w = new WorldWrapper(e.entity.worldObj);
			selectionManager.setWorld(w);
			pickManager.setWorld(w);
			isWorldSet = true;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerInteractEvent(PlayerInteractEvent e) {

		World w = e.entity.worldObj;
		if (w.isRemote)
			return;
		// Else we get java.lang.IllegalStateException: TickNextTick list out of synch
		// MOD.log.info("[ForgeEventHandle.onPlayerInteractEvent] w.isRemote=" + w.isRemote);

		EntityPlayer p = e.entityPlayer;
		ItemStack stack = p.inventory.getCurrentItem();
		if (stack == null) {
			return; // Do nothing if nothing held. Do not cancel event
		}
		Item item = stack.getItem();

		if (item instanceof ItemBlock) {
			ItemBlock itemBlock = (ItemBlock) item;
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				break;
			case RIGHT_CLICK_AIR:
				break;
			case RIGHT_CLICK_BLOCK:
				if (selectionManager.isSelected(e.x, e.y, e.z)) {
					Block b = itemBlock.field_150939_a;
					int metadata = item.getDamage(stack);
					SlotEntry[] slotEntries = new SlotEntry[] { new SlotEntry(b, metadata, 0) };
					new SpellFill(undoManager, selectionManager, null, null).invoke(new WorldWrapper(w), slotEntries);
					if (e.isCancelable())
						e.setCanceled(true);
				}
				break;
			default:
				break;
			}

		// IClickable covers both Spells and Staffs.
		} else if (item instanceof IClickable) {
			IClickable c = (IClickable) item;
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				c.onClickLeft(e);
				break;
			case RIGHT_CLICK_AIR:
				// Recieving this when right clicking (not on air)
				// c.onClickRightAir(e);
				break;
			case RIGHT_CLICK_BLOCK:
				c.onClickRight(e);
				break;
			default:
				break;
			}
			if (e.isCancelable()) {
				e.setCanceled(true);
			}
		} else {
			System.out.println("[ForgeEventHandler.onClick] Unexpected item: " + item);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHightlight(DrawBlockHighlightEvent e) {
		MovingObjectPosition pos = e.target;
		if (holdable != null) {
			Pick lastPick = pickManager.lastPick();
			if (lastPick != null) {
				Vector3d d = new Vector3d();
				d.x = lastPick.x - pos.blockX;
				d.y = lastPick.y - pos.blockY;
				d.z = lastPick.z - pos.blockZ;
				overlay.setDisplacement(d);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		World w = e.entity.worldObj;
		if (!w.isRemote)
			return;
		if (e.entity instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.entity;
			ItemStack is = p.getHeldItem();
			if (is == null) {
				holdable = null;
			} else {
				Item item = is.getItem();
				if (item instanceof IHoldable) {
					if (item != holdable) {
						holdable = (IHoldable) item;
						Spell s = holdable.getSpell();
						if (s != null) {
							pickManager.clearPicks();
							pickManager.reset(s.getNumPicks());
						}
					}
				} else {
					holdable = null;
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
		if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			if (holdable != null) {
				overlay.draw(holdable);
			}
		}
	}
}
