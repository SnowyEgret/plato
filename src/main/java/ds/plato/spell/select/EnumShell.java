package ds.plato.spell.select;

import org.apache.commons.lang3.tuple.Pair;

import ds.plato.common.Menu;
import ds.plato.common.Messages;

public enum EnumShell {

	ALL(Messages.selection_expand_all),
	HORIZONTAL(Messages.selection_expand_horizontal),
	UP(Messages.selection_expand_up),
	DOWN(Messages.selection_expand_down),
	TOP(Messages.selection_expand_top),
	BOTTOM(Messages.selection_expand_bottom),
	VERTICAL_XY(Messages.selection_expand_xy),
	VERTICAL_ZY(Messages.selection_expand_zy),
	FLOOR(Messages.selection_expand_floor, Messages.selection_expand_floor_pick_1),
	CEILING(Messages.selection_expand_ceiling, Messages.selection_expand_ceiling_pick_1),
	FLOOR_EDGE(Messages.selection_expand_floor_edge),
	CEILING_EDGE(Messages.selection_expand_ceiling_edge);

	public int numPicks;
	public Menu description;

	private EnumShell(int numPicks, Menu description) {
		this.numPicks = numPicks;
		this.description = description;
	}
	private EnumShell(String s) {
		this(0, new Menu(s, Pair.of("alt", "heterogenius selection"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private EnumShell(String name, String pick) {
		this(0, new Menu(name, new String[] { pick }, Pair.of("alt", "heterogenius selection"))); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
