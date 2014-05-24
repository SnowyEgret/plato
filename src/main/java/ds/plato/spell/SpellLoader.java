package ds.plato.spell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.common.ISelect;
import ds.plato.pick.IPick;
import ds.plato.undo.IUndo;

public class SpellLoader {

	private CreativeTabs tabSpells;
	private String modId;
	IUndo undoManager;
	ISelect selectionManager;
	IPick pickManager;
	Block blockAir;

	public SpellLoader( IUndo undoManager, ISelect selectionManager, IPick pickManager, Block blockAir, String modId) {
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
		this.blockAir = blockAir;
		this.modId = modId;
		
		tabSpells = new CreativeTabs("tabSpells") {
			@Override
			public Item getTabIconItem() {
				return Items.glass_bottle;
			}
		};
	}

	public Iterable<AbstractSpell> loadSpells(Class<? extends AbstractSpell>... spellClasses) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<AbstractSpell> spells = new ArrayList<>();
		for (Class<? extends AbstractSpell> c : spellClasses) {
			spells.add(loadSpell(c));
		}
		return spells;
	}

	// public Item loadSpell(Class<? extends AbstractSpell> spellClass, Class<? extends SpellDescriptor>
	// descriptorClass, IUndo undoManager, ISelect selectionManager, Block blockAir) throws InstantiationException,
	// IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
	// SecurityException {
	public AbstractSpell loadSpell(Class<? extends AbstractSpell> spellClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		String name = toName(spellClass);
		String descriptorClassname = spellClass.getName() + "Descriptor";
		Class descriptorClass = Class.forName(descriptorClassname);
		// Property p = config.get("Spell", name + ".state", 0);
		SpellDescriptor d = (SpellDescriptor) descriptorClass.getConstructor().newInstance();
		Constructor<? extends AbstractSpell> c = spellClass.getConstructor(SpellDescriptor.class, IUndo.class,
				ISelect.class, IPick.class, Block.class);
		AbstractSpell s = (AbstractSpell) c.newInstance(d, undoManager, selectionManager, pickManager, blockAir);
		s.setUnlocalizedName(name);
		s.setMaxStackSize(1);
		s.setCreativeTab(tabSpells);
		s.setTextureName(modId + ":" + name);
		// s.setInitialState(config.get("Stick", name + ".state", 0));
		GameRegistry.registerItem(s, name);
		System.out.println("[SpellLoader.loadSpell] Loaded spell=" + s);
		return s;
	}

	private String toName(Class c) {
		String n = c.getSimpleName();
		return n.substring(0, 1).toLowerCase() + n.substring(1);
	}

}
