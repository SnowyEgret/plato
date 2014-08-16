package ds.plato.spell.descriptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Joiner;

public class ModifierDescriptor {

	List<Pair<String, String>> modifiers = new ArrayList<>();

	public ModifierDescriptor(String... commaSeparatedKeyActionPairs) {
		for (String keyActionPair : commaSeparatedKeyActionPairs) {
			addModifier(keyActionPair);
		}
	}

	@Override
	public String toString() {
		List<String> pairs = new ArrayList();
		for (Pair p : modifiers) {
			pairs.add(String.format("<%s>", p.getLeft()) + " " + p.getRight());
		}
		return Joiner.on(", ").join(pairs);
	}

	public void addModifier(String keyActionPair) {
		String[] strings = keyActionPair.split(",");
		if (strings.length == 2) {
			modifiers.add(Pair.of(strings[0].trim(), strings[1].trim()));
		} else {
			modifiers.add(Pair.of("Bad format:", keyActionPair));
		}
	}

}