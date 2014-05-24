package ds.plato.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.spell.Spell;
import ds.plato.spell.SpellDescriptor;
import ds.plato.undo.IUndo;

public class ConfigHelper {

	private CreativeTabs tabMOD;
	private String modId;
	private Configuration config;

	public ConfigHelper(File file, String modId) {
		config = new Configuration(file);
		this.modId = modId;
		tabMOD = new CreativeTabs("tabMOD") {
			@Override
			public Item getTabIconItem() {
				return Items.baked_potato;
			}
		};
	}

	public Block initBlock(Class blockClass) {
		return initBlock(blockClass, null);
	}

	public Block initBlock(Class blockClass, Class tileClass) {

		// TODO: Assert that block class starts with 'Block" and tile class with 'Tile' or 'TileEntity'
		// TODO: Assert that block and tile classes end with same word
		Block b = (Block) instanciate(blockClass, null);
		String name = toName(blockClass);
		b.setBlockName(name);
		GameRegistry.registerBlock(b, modId + name);
		if (tileClass != null) {
			try {
				GameRegistry.registerTileEntity(tileClass, modId + tileClass.getSimpleName());
			} catch (IllegalArgumentException e) {
				Plato.log.info("Class " + tileClass.getSimpleName() + " already registered - Skipping.");
			}
		}

		return b;
	}

	public Stick initStick(Class itemClass) {

		String name = toName(itemClass);
		Property p = config.get("Stick", name + ".state", 0);
		Stick i = (Stick) instanciate(itemClass, p);
		i.setUnlocalizedName(name);
		i.setMaxStackSize(1);
		i.setCreativeTab(tabMOD);
		i.setTextureName(modId + ":" + name);
		i.setInitialState(config.get("Stick", name + ".state", 0));
		GameRegistry.registerItem(i, name);
		return i;
	}

	private String lastWordInCameltype(String cameltype) {
		String[] tokens = cameltype.split("(?=[A-Z])");
		// System.out.println("[ConfigHelper.lastWordinCameltype] tokens="+Arrays.toString(tokens));
		return tokens[tokens.length - 1];
	}

	private String toName(Class c) {
		String n = c.getSimpleName();
		return n.substring(0, 1).toLowerCase() + n.substring(1);
	}

	private String toLanguage(String classname) {
		String[] tokens = classname.split("(?=[A-Z])");
		String s = "";
		for (int i = 0; i < tokens.length; i++) {
			String t = tokens[i];
			if (t.equals("Block") || t.equals("Item"))
				continue;
			if (i != 0)
				s += " ";
			s += t;
		}
		return s;
	}

	private Object instanciate(Class cls, Property property) {
		Object o;
		try {
			if (property == null) {
				o = cls.getConstructor().newInstance();
			} else {
				o = cls.getConstructor(property.getClass()).newInstance(property);
			}
			Plato.log.info("Constructed: " + o);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return o;
	}

	public void save() {
		config.save();
	}
}
