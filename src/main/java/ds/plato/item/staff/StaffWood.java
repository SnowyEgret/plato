package ds.plato.item.staff;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.api.IPick;
import ds.plato.api.ISpell;
import ds.plato.api.IStaff;
import ds.plato.item.ItemBase;
import ds.plato.item.spell.Spell;
import ds.plato.util.StringUtils;

public class StaffWood extends ItemBase implements IStaff {

	int size = 9;
	IPick pickManager;
	protected IModelCustom model;
	private final String modelPath = "models/" + StringUtils.toCamelCase(getClass());
	private final ResourceLocation modelLocation = new ResourceLocation("plato", modelPath + ".obj");
	private final ResourceLocation modelTextureLocation = new ResourceLocation("plato", modelPath + ".png");
	
	public StaffWood(IPick pickManager) {
		this.pickManager = pickManager;
//		try {
//			model = AdvancedModelLoader.loadModel(modelLocation);
//		} catch (Exception e) {
//			// ClientProxy.setCustomRenderers logs missing model
//		}
	}

@Override
	public void onMouseClickLeft(ItemStack stack, int x, int y, int z, int side) {
		if (!isEmpty(stack)) {
			getSpell(stack).onMouseClickLeft(stack, x, y, z, side);
		}
	}

//	@Override
//	public IModelCustom getModel() {
//		return model;
//	}

//	@Override
//	public ResourceLocation getTextureResourceLocation() {
//		return modelTextureLocation;
//	}

//	// https://github.com/TheGreyGhost/ItemRendering/blob/master/src/TestItemRendering/items/ItemLampshade.java
//	@Override
//	public int getSpriteNumber() {
//		return model == null ? 1 : 0;
//	}

//	@Override
//	public Object[] getRecipe() {
//		return new Object[] { "#  ", " # ", "  #", '#', Items.bone };
//	}

//	@Override
//	public boolean hasRecipe() {
//		return getRecipe() != null;
//	}

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
	
	// ISpell ----------------------------------------------------------------------

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
	public ISpell previousSpell(ItemStack stack) {
		ISpell s = null;
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
			ISpell s = getSpellAtIndex(stack, i);
			if (s != null)
				numSpells++;
		}
		return numSpells;
	}

	@Override
	public boolean isEmpty(ItemStack stack) {
		for (int i = 0; i < size; i++) {
			ISpell s = getSpellAtIndex(stack, i);
			if (s != null) {
				return false;
			}
		}
		return true;
	}

	// Private ---------------------------------------------------

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
