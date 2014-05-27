package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Joiner;

public class ModifierDescriptor {

	List<Pair<String, String>> modifiers = new ArrayList<>();

	public ModifierDescriptor(Pair<String, String>... modifierPairs) {
		for (Pair p : modifierPairs) {
			modifiers.add(p);
		}
	}

	@Override
	public String toString() {
		List<String> pairs = new ArrayList();
		for (Pair p : modifiers) {
			pairs.add(String.format("<%s>", p.getLeft())+" "+p.getRight());
		}
		return Joiner.on(", ").join(pairs);
	}

}