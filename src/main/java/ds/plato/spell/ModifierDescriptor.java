package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Joiner;

public class ModifierDescriptor {

	List<Pair<String, String>> modifiers = new ArrayList<>();

	public ModifierDescriptor(String... commaSeparatedKeyActionPairs) {
		for (String keyActionPair : commaSeparatedKeyActionPairs) {
			String[] strings = keyActionPair.split(",");
			modifiers.add(Pair.of(strings[0].trim(), strings[1].trim()));
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