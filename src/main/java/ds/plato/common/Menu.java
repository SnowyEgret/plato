package ds.plato.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Joiner;

public class Menu {

	public String name = "Name: ";
	public String picks = "Picks: ";
	public String modifiers = "Modifiers: ";

	public Menu(String name, String[] picks, Pair<String, String>... modifierPairs) {
		//this.name = name.toUpperCase();
		String[] tokens = name.split("(?=[ ])");
		StringBuffer b = new StringBuffer(tokens[0].toUpperCase());
		for (int i = 1; i < tokens.length; i++) {
			b.append(tokens[i]);
		}
		this.name = b.toString();
		if (picks == null || picks.length == 0) {
			this.picks = "";
		} else {
			List l = new ArrayList();
			int i = 0;
			for (String p : picks) {
				l.add(String.format("<pick%d> %s", ++i, p));
			}
			this.picks = Joiner.on(", ").join(l);
		}
		if (modifierPairs != null) {
			List l = new ArrayList();
			for (Pair<String, String> p : modifierPairs) {
				//l.add("<" + p.getLeft().toLowerCase() + "> " + p.getRight());
				l.add(String.format("<%s> %s", p.getLeft().toLowerCase(), p.getRight()));
			}
			this.modifiers = Joiner.on(", ").join(l);
		}
	}

	public Menu() {
	}

	public Menu(String name) {
		this(name, new String[] {}, (Pair<String, String>[]) null);
	}

	public Menu(String name, Pair<String, String>... modifiers) {
		this(name, new String[] {}, modifiers);
	}

	public Menu(String name, String pick) {
		this(name, new String[] {pick}, (Pair<String, String>[]) null);
	}

	@Override
	public String toString() {
		// return name + "\n" + picks + "\n" + modifiers;
		return name + " " + picks + " " + modifiers;
	}

}
