package ds.plato.spell.descriptor;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

@Deprecated
public class PickDescriptor {

	List<String> picks = new ArrayList<>();

	public PickDescriptor(String...picks) {
		//String[] picks = commaSeparatedPicks.split(",");
		for (String string : picks) {
			this.picks.add(string.trim());
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
