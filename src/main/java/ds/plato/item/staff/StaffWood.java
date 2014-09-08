package ds.plato.item.staff;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.input.Keyboard;

import container.test.MyMod;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.api.IItem;
import ds.plato.api.IPick;
import ds.plato.api.IStaff;
import ds.plato.core.Player;
import ds.plato.item.spell.ISelector;
import ds.plato.item.spell.Spell;
import ds.plato.pick.PickManager;
import ds.plato.util.StringUtils;

public class StaffWood extends Item implements ISelector, IStaff, IItem {

	int size = 9;
	IPick pickManager;
	protected IModelCustom model;
	private final String modelPath = "models/" + StringUtils.toCamelCase(getClass());
	private final ResourceLocation modelLocation = new ResourceLocation("plato", modelPath + ".obj");
	private final ResourceLocation modelTextureLocation = new ResourceLocation("plato", modelPath + ".png");
	
	public StaffWood(IPick pickManager) {
		this.pickManager = pickManager;
		try {
			model = AdvancedModelLoader.loadModel(modelLocation);
		} catch (Exception e) {
			// ClientProxy.setCustomRenderers logs missing model
		}
	}

	/* (non-Javadoc)
	 * @see ds.plato.item.staff.IItem#getModel()
	 */
	@Override
	public IModelCustom getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see ds.plato.item.staff.IItem#getTextureResourceLocation()
	 */
	@Override
	public ResourceLocation getTextureResourceLocation() {
		return modelTextureLocation;
	}

	// https://github.com/TheGreyGhost/ItemRendering/blob/master/src/TestItemRendering/items/ItemLampshade.java
	@Override
	public int getSpriteNumber() {
		return model == null ? 1 : 0;
	}

	/* (non-Javadoc)
	 * @see ds.plato.item.staff.IItem#getRecipe()
	 */
	@Override
	public Object[] getRecipe() {
		return new Object[] { "#  ", " # ", "  #", '#', Items.bone };
	}

	/* (non-Javadoc)
	 * @see ds.plato.item.staff.IItem#hasRecipe()
	 */
	@Override
	public boolean hasRecipe() {
		return getRecipe() != null;
	}

	// Adds information to rollover in creative tab
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List rollOver, boolean par4) {
		if (isEmpty(stack)) {
			rollOver.add(EnumChatFormatting.RED + "No spells on staff");
		} else {
			rollOver.add(EnumChatFormatting.GREEN + " " + numSpells(stack) + " spells on staff");
		}
	}

	@Override
	public void select(ItemStack stack, int x, int y, int z, int side) {
		if (!isEmpty(stack)) {
			getSpell(stack).select(stack, x, y, z, side);
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float sx, float sy, float sz) {
		if (!world.isRemote && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			player.openGui(Plato.instance, 3, world, 0, 0, 0);
			// } else if (!world.isRemote && !isEmpty(stack)) {
			// } else if (world.isRemote && !isEmpty(stack)) {
			// getSpell(stack).onMouseClickRight(stack, x, y, z, side);
			// }
		} else if (!world.isRemote && !isEmpty(stack)) {
			Spell s = getSpell(stack);
			System.out.println("[StaffWood.onItemUse] s=" + s);
			s.onItemUse(stack, player, world, x, y, z, side, sx, sy, sz);
		}
		return true;
	}

	// @Override
	// public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	// if (!world.isRemote) {
	// player.openGui(Plato.instance, 3, world, 0, 0, 0);
	// }
	// return stack;
	// }

	@Override
	public Spell getSpell(ItemStack stack) {
		if (isEmpty(stack)) {
			return null;
		} else {
			Spell s = getSpellAtIndex(stack, getOrdinal(stack));
			if (s == null) {
				s = nextSpell(stack);
			}
			return s;
		}
	}

	@Override
	public Spell nextSpell(ItemStack stack) {
		Spell s = null;
		for (int i = 0; i < size; i++) {
			if (getOrdinal(stack) == size - 1) {
				setOrdinal(stack, 0);
			} else {
				incrementOrdinal(stack, 1);
			}
			s = getSpellAtIndex(stack, getOrdinal(stack));
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				break;
			}
		}
		// System.out.println("[StaffWood.nextSpell] s=" + s);
		return s;
	}

	@Override
	public Spell previousSpell(ItemStack stack) {
		Spell s = null;
		for (int i = 0; i < size; i++) {
			if (getOrdinal(stack) == 0) {
				setOrdinal(stack, size - 1);
			} else {
				incrementOrdinal(stack, -1);

			}
			s = getSpellAtIndex(stack, getOrdinal(stack));
			if (s == null) {
				continue;
			} else {
				pickManager.reset(s.getNumPicks());
				break;
			}
		}
		return s;
	}

	@Override
	public int numSpells(ItemStack stack) {
		int numSpells = 0;
		for (int i = 0; i < size; i++) {
			Spell s = getSpellAtIndex(stack, i);
			if (s != null)
				numSpells++;
		}
		return numSpells;
	}

	@Override
	public boolean isEmpty(ItemStack stack) {
		for (int i = 0; i < size; i++) {
			Spell s = getSpellAtIndex(stack, i);
			if (s != null) {
				return false;
			}
		}
		return true;
	}

	// Private methods ///////////////////////////

	private Spell getSpellAtIndex(ItemStack stack, int i) {
		NBTTagCompound t = getTag(stack);
		String name = t.getString(String.valueOf(i));
		if (name != null && !name.equals("")) {
			Spell spell = (Spell) GameRegistry.findItem(Plato.ID, name);
			if (spell == null) {
				throw new RuntimeException("Game registry could not find item.  itemSimpleClassName=" + name);
			}
			return spell;
		}
		return null;
	}

	private int getOrdinal(ItemStack stack) {
		NBTTagCompound t = getTag(stack);
		int ordinal = t.getInteger("o");
		return ordinal;
	}

	private void setOrdinal(ItemStack stack, int i) {
		NBTTagCompound t = getTag(stack);
		t.setInteger("o", i);
	}

	private void incrementOrdinal(ItemStack stack, int increment) {
		NBTTagCompound t = getTag(stack);
		int i = t.getInteger("o");
		i = i + increment;
		t.setInteger("o", i);
	}

	private NBTTagCompound getTag(ItemStack stack) {
		NBTTagCompound t = stack.getTagCompound();
		if (t == null) {
			t = new NBTTagCompound();
			stack.setTagCompound(t);
		}
		return t;
	}
}
