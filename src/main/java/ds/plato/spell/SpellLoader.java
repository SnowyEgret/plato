package ds.plato.spell;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import cpw.mods.fml.common.registry.GameRegistry;
import ds.plato.Plato;
import ds.plato.pick.IPick;
import ds.plato.select.ISelect;
import ds.plato.undo.IUndo;

public class SpellLoader {

	public static CreativeTabs tabSpells;
	private String modId;
	IUndo undoManager;
	ISelect selectionManager;
	IPick pickManager;
	private Configuration config;

	public SpellLoader(
			Configuration config,
			IUndo undoManager,
			ISelect selectionManager,
			IPick pickManager,
			String modId) {
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
		this.pickManager = pickManager;
		this.modId = modId;
		this.config = config;

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

		System.out.print("[SpellLoader.loadSpell] Loading spell=" + spellClass.getSimpleName() + "...");
		String name = toName(spellClass);
		Constructor<? extends Spell> c = spellClass.getConstructor(IUndo.class, ISelect.class, IPick.class);
		Spell s = (Spell) c.newInstance(undoManager, selectionManager, pickManager);
		s.setUnlocalizedName(name);
		s.setMaxStackSize(1);
		s.setCreativeTab(tabSpells);
		s.setTextureName(modId + ":" + name);
		GameRegistry.registerItem(s, name);
		if (s.hasRecipe()) {
			//System.out.println("[SpellLoader.loadSpell] s.getRecipe()=" + s.getRecipe());
			GameRegistry.addRecipe(new ItemStack(s), s.getRecipe());
		}
		System.out.println("done.");
		return s;
	}

	public Staff loadStaff(Class<? extends Staff> staffClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.print("[SpellLoader.loadStaff] Loading staff=" + staffClass.getSimpleName() + "...");
		String name = toName(staffClass);
		Property propertyOrdinal = config.get("Staff", name + ".ordinal", 0);
		Constructor c = staffClass.getConstructor(Property.class, IPick.class);
		Staff s = (Staff) c.newInstance(propertyOrdinal, pickManager);
		s.setUnlocalizedName(name);
		s.setMaxStackSize(1);
		s.setCreativeTab(tabSpells);
		s.setTextureName(modId + ":" + name);
		s.setOrdinal(propertyOrdinal.getInt());
		GameRegistry.registerItem(s, name);
		if (s.hasRecipe()) {
			GameRegistry.addRecipe(new ItemStack(s), s.getRecipe());
		}
		System.out.println("done.");
		return s;
	}

	public List<Spell> loadSpellsFromPackage(String packageName) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		ClassPath p = ClassPath.from(this.getClass().getClassLoader());
		List<Spell> spells = new ArrayList<>();
		for (ClassInfo i : p.getTopLevelClassesRecursive(packageName)) {
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

	public void save() {
		config.save();
	}

	private String toName(Class c) {
		String n = c.getSimpleName();
		return n.substring(0, 1).toLowerCase() + n.substring(1);
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

}
