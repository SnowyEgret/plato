package ds.plato.spell;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

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

	public SpellLoader(IUndo undoManager, ISelect selectionManager, IPick pickManager, Block blockAir, String modId) {
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

	public List<Spell> loadSpells(List<Class<? extends Spell>> spellClasses) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<Spell> spells = new ArrayList<>();
		for (Class<? extends Spell> c : spellClasses) {
			spells.add(loadSpell(c));
		}
		return spells;
	}

	public Spell loadSpell(Class<? extends Spell> spellClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		String name = toName(spellClass);
		// Property p = config.get("Spell", name + ".state", 0);
		Constructor<? extends Spell> c = spellClass.getConstructor(IUndo.class, ISelect.class, IPick.class,
				BlockAir.class);
		Spell s = (Spell) c.newInstance(undoManager, selectionManager, pickManager, blockAir);
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

	public Staff loadStaff(Class<? extends Staff> staffClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = toName(staffClass);
		Constructor c = staffClass.getConstructor(IPick.class);
		Staff s = (Staff) c.newInstance(pickManager);
		s.setUnlocalizedName(name);
		s.setMaxStackSize(1);
		s.setCreativeTab(tabSpells);
		s.setTextureName(modId + ":" + name);
		GameRegistry.registerItem(s, name);
		System.out.println("[SpellLoader.loadStaff] Loaded staff=" + s);
		return s;
	}

	public List<Spell> loadSpellsFromPackage(String packageName) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		ClassPath p = ClassPath.from(this.getClass().getClassLoader());
		List<Spell> spells = new ArrayList<>();
		for (ClassInfo i : p.getTopLevelClasses(packageName)) {
			Class c = i.load();
			if (Spell.class.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
				spells.add(loadSpell(c));
			}
		}
		return spells;
	}

	public List<Class> loadClassesFromPackage(Class type, String packageName) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		ClassPath p = ClassPath.from(this.getClass().getClassLoader());
		List<Class> classes = new ArrayList<>();
		for (ClassInfo i : p.getTopLevelClasses(packageName)) {
			Class c = i.load();
			if (type.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
				classes.add(c);
			}
		}
		return classes;
	}

}
