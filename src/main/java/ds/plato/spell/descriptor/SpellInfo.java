package ds.plato.spell.descriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.resources.I18n;

import com.google.common.base.Joiner;

import ds.plato.core.StringUtils;
import ds.plato.spell.Spell;

public class SpellInfo {

	private String name;
	private String description;
	private String root;
	private List<String> picks = new ArrayList<>();
	private Map<String, String> modifiers = new HashMap<>();

	public SpellInfo(Class<? extends Spell> spellClass) {
		root = "item." + StringUtils.toCamelCase(spellClass) + ".";
		name = format("name");
		description = format("description");
	}

	private String format(String string) {
		return I18n.format(root + string);
	}

	public void addPick() {
		picks.add(format("pick." + picks.size()));
	}

	public void addModifier(Modifier modifier) {
		//String s = format("modifier." + suffix);
		switch (modifier) {
		case CTRL:
			modifiers.put("ctrl", format("modifier.ctrl"));
			break;
		case ALT:
			modifiers.put("alt", format("modifier.alt"));
			break;
		case SHIFT:
			modifiers.put("shift", format("modifier.shift"));
			break;
		default:
			break;
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPicks() {
		List l = new ArrayList();
		int i = 0;
		for (String p : picks) {
			l.add(String.format("<pick%d> %s", ++i, p));
		}
		return Joiner.on(", ").join(l);
	}

	public String getModifiers() {
		List<String> l = new ArrayList();
		for (Map.Entry p : modifiers.entrySet()) {
			l.add(String.format("<%s>", p.getKey()) + " " + p.getValue());
		}
		return Joiner.on(", ").join(l);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellInfo [name=");
		builder.append(getName());
		builder.append(", description=");
		builder.append(getDescription());
		builder.append(", picks=");
		builder.append(getPicks());
		builder.append(", modifiers=");
		builder.append(getModifiers());
		builder.append("]");
		return builder.toString();
	}

}
