package ds.plato.spell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.common.ISelect;
import ds.plato.undo.IUndo;

public class SpellLoader {

	private CreativeTabs tabSpells;
	private String modId;

	public SpellLoader(String modId) {
		this.modId = modId;
	}

	public SpellLoader() {
		tabSpells = new CreativeTabs("tabSpells") {
			@Override
			public Item getTabIconItem() {
				return Items.glass_bottle;
			}
		};
	}

	public Item loadSpell(Class<? extends AbstractSpell> spellClass, Class<? extends SpellDescriptor> descriptorClass, IUndo undoManager, ISelect selectionManager, Block blockAir) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		String name = toName(spellClass);
		// Property p = config.get("Spell", name + ".state", 0);
		SpellDescriptor d = (SpellDescriptor) descriptorClass.getConstructor().newInstance();
		Constructor<? extends AbstractSpell> c = spellClass.getConstructor(SpellDescriptor.class, IUndo.class, ISelect.class, Block.class);
		Item s = (Item) c.newInstance(d, undoManager, selectionManager, blockAir);
		s.setUnlocalizedName(name);
		s.setMaxStackSize(1);
		s.setCreativeTab(tabSpells);
		s.setTextureName(modId + ":" + name);
		// s.setInitialState(config.get("Stick", name + ".state", 0));
		//GameRegistry.registerItem(s, name);
		return s;
	}

	private String toName(Class c) {
		String n = c.getSimpleName();
		return n.substring(0, 1).toLowerCase() + n.substring(1);
	}

}
