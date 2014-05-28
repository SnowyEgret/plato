package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class PickDescriptor {

	List<String> picks = new ArrayList<>();

	public PickDescriptor(String... picks) {
		for (String string : picks) {
			this.picks.add(string);
		}
	}

	@Override
	public String toString() {
		List l = new ArrayList();
		int i = 0;
		for (String p : picks) {
			l.add(String.format("<pick%d> %s", ++i, p));
		}
		return Joiner.on(", ").join(l);
	}

}
