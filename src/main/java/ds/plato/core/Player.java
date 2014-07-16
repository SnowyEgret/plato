package ds.plato.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class Player {

	private EntityPlayer player;

	public Player(EntityPlayer player) {
		this.player = player;
	}

	public static Player client() {
		return new Player(Minecraft.getMinecraft().thePlayer);
	}

	public IWorld getWorldServer() {
		WorldServer w = null;
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.getIntegratedServer() != null) {
			w = mc.getIntegratedServer().worldServerForDimension(player.dimension);
		} else if (MinecraftServer.getServer() != null) {
			w = MinecraftServer.getServer().worldServerForDimension(player.dimension);
		}
		return new WorldWrapper(w);
	}

	public SlotEntry[] getSlotEntries() {
		List<SlotEntry> entries = new ArrayList<>();
		InventoryPlayer inventory = player.inventory;
		for (int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				Item item = stack.getItem();
				int metadata = item.getDamage(stack);

				Block b = null;
				if (item instanceof ItemBlock) {
					ItemBlock itemBlock = (ItemBlock) item;
					b = itemBlock.field_150939_a;
					// TODO how to get color name from sub block?
					// if (b instanceof BlockColored) {
					// if (stack.getHasSubtypes()) {
					// List<ItemStack> subBlocks = new ArrayList<>();
					// ((BlockColored) b).getSubBlocks(item, getCreativeTab(), subBlocks);
					// ItemStack is = subBlocks.get(metadata);
					// b = ((ItemBlock) is.getItem()).field_150939_a;
					// System.out.println("[Spell.getSlotEntriesFromPlayer] is=" + is);
					// }
					// MapColor c = ((BlockColored) b).getMapColor(metadata);
					// System.out.println("[Spell.getSlotEntriesFromPlayer] c=" + c.colorValue);
					// }
				} else if (item == Items.water_bucket) {
					b = Blocks.water;
				} else if (item == Items.lava_bucket) {
					b = Blocks.lava;
				}

				if (b != null) {
					SlotEntry entry = new SlotEntry(b, metadata, i + 1);
					entries.add(entry);
				}
			}
		}
		if (entries.isEmpty()) {
			entries.add(new SlotEntry(Blocks.dirt));
		}
		SlotEntry[] array = new SlotEntry[entries.size()];
		return entries.toArray(array);
	}

	public Direction getDirection() {
		int yaw = (int) (player.rotationYawHead);
		yaw += (yaw >= 0) ? 45 : -45;
		yaw /= 90;
		//System.out.println("[Player.getDirection] yaw=" + yaw);
		int modulus = yaw % 4;
		//System.out.println("[Player.getDirection] modulus=" + modulus);
		Direction direction = null;
		switch (modulus) {
		case 0:
			direction =  Direction.SOUTH;
			break;
		case 1:
			direction = Direction.WEST;
			break;
		case -1:
			direction = Direction.EAST;
			break;
		case 2:
			direction = Direction.NORTH;
			break;
		case -2:
			direction = Direction.NORTH;
			break;
		case 3:
			direction = Direction.EAST;
			break;
		case -3:
			direction = Direction.WEST;
			break;
		default:
			throw new RuntimeException("Unexpected modulus. Got " + modulus);
		}
		//System.out.println("[Player.getDirection] direction=" + direction);
		return direction;
	}

	public SlotDistribution slotDistribution() {
		return new SlotDistribution(getSlotEntries());
	}

	public ItemStack getHeldItem() {
		return player.getCurrentEquippedItem();
	}
}