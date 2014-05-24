package ds.plato.common;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
import ds.plato.WorldWrapper;
import ds.plato.pick.Pick;
import ds.plato.spell.AbstractSpell;

public class ForgeEventHandle {

	private Stick heldStick = null;
	private Vector3d displacement = new Vector3d();
	private Plato plato;
	private boolean isWorldSet = false;

	// @SideOnly(Side.CLIENT)
	// @SubscribeEvent
	// public void onMouseEvent(MouseEvent e) {
	// //Works but is called more often than PlayerInteractEvent
	// MOD.log.info("[ForgeEventHandle.onMouseEvent] e=" + e);
	// }

	public ForgeEventHandle(Plato plato) {
		this.plato = plato;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent e) {
		if (!isWorldSet && e.entity instanceof EntityPlayerMP) {
			plato.setWorld(new WorldWrapper(e.entity.worldObj));
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

		if (item instanceof Stick) {
			Stick stick = (Stick) item;
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				stick.onClickLeft(e);
				break;
			case RIGHT_CLICK_AIR:
				// Doesn't seem to be working
				// stick.onClickRightAir(e);
				break;
			case RIGHT_CLICK_BLOCK:
				stick.onClickRight(e);
				// MOD.log.info("[ForgeEventHandle.onPlayerInteractEvent] MOD.selectionManager.size()=" +
				// MOD.selectionManager.size());
				break;
			default:
				break;
			}
			
		} else if (item instanceof ItemBlock) {
			ItemBlock itemBlock = (ItemBlock) item;
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				break;
			case RIGHT_CLICK_AIR:
				break;
			case RIGHT_CLICK_BLOCK:
				if (Plato.selectionManager.isSelected(e.x, e.y, e.z)) {
					Block b = itemBlock.field_150939_a;
					int metadata = item.getDamage(stack);
					Plato.editStick.fillSelections(b, metadata);
					if (e.isCancelable())
						e.setCanceled(true);
				}
				break;
			default:
				break;
			}

		} else if (item instanceof AbstractSpell) {
			AbstractSpell spell = (AbstractSpell) item;
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				break;
			case RIGHT_CLICK_AIR:
				break;
			case RIGHT_CLICK_BLOCK:
				spell.onClickRight(e);
				break;
			default:
				break;
			}
			System.out.println("[ForgeEventHandler.onClick] Not Handled. item=" + item);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHightlight(DrawBlockHighlightEvent e) {
		MovingObjectPosition pos = e.target;
		if (heldStick != null && heldStick != Plato.selectionStick) {
			Pick lastPick = heldStick.lastPick();
			if (lastPick != null) {
				Vector3d d = new Vector3d();
				d.x = lastPick.x - pos.blockX;
				d.y = lastPick.y - pos.blockY;
				d.z = lastPick.z - pos.blockZ;
				if (!displacement.equals(d)) {
					displacement = d;
					Plato.log.info("[ForgeEventHandle.onDrawBlockHightlight] displacment=" + displacement);
				}
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
			updateHeldStick(p);
			Plato.updateInventoryDistribution();
		}
	}

	// Prints current state of newly held stick and clears picks of previously held stick.
	private void updateHeldStick(EntityPlayer p) {
		ItemStack is = p.getHeldItem();
		if (is != null) {
			Item item = is.getItem();
			if (item instanceof Stick) {
				if (item != heldStick) {
					Plato.clearPicks();
					heldStick = (Stick) item;
					((Stick) item).printCurrentState();
				}
			} else {
				heldStick = null;
			}
		}
		return;
	}

	@SubscribeEvent
	public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
		// System.out.println("[ForgeEventHandle.onRenderGameOverlayEvent] event.type=" + event.type);
		if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			if (heldStick != null) {
				FontRenderer r = Minecraft.getMinecraft().fontRenderer;
				int dy = r.FONT_HEIGHT + 5;
				int x = 10;
				int y = x;
				Menu d = heldStick.state.getDescription();
				r.drawStringWithShadow(d.name, x, y, 0xffffff);
				if (d.picks != "") {
					r.drawStringWithShadow(d.picks, x, y += dy, 0xaaffaa);
				}
				r.drawStringWithShadow(d.modifiers, x, y += dy, 0xaaaaff);
				if (heldStick != Plato.selectionStick && heldStick.isPicking()) {
					r.drawStringWithShadow(displacement.toString(), x, y += dy, 0xffaaaa);
				}
				// if (heldStick == MOD.selectionStick) {
				r.drawStringWithShadow("Selection size: " + Plato.selectionManager.size(), x, y += dy, 0xffaaaa);
				// }
			}
		}
	}
}
