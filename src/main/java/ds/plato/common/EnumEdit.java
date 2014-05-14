package ds.plato.common;

import org.apache.commons.lang3.tuple.Pair;

public enum EnumEdit {

	MOVE(2, new Menu("move selection", new String[] { "from", "to" }, Pair.of("ctrl", "delete original"))),
	ROTATE(3, new Menu("rotate selection", new String[] { "center of rotation", "angle from", "angle to" }, Pair.of("ctrl", "delete original"))),
	ROT90(1, new Menu("rotate 90 degrees clockwise", new String[] { "center of rotation" }, Pair.of("alt",
			"rotate around centroid"), Pair.of("X:, Y: <default>, Z", "axis of rotation"))),
	MIRROR(2, new Menu("mirror")),
	MIRROR_PLANE(1, new Menu("mirror_plane")),
	SCALE(2, new Menu("scale")),
	HOLLOW(1, new Menu("hollow selection (Attention: Hostile mobs may spawn inside!)", "anywhere")),
	DELETE(1, new Menu("Delete selection", "anywhere")),
	FILL(1, new Menu("Fill selection with first block in inventory", "anywhere")),
	FILL_CHECKER(1, new Menu("fill selection with CHECKER pattern with first two blocks in inventory", "anywhere")),
	FILL_RANDOM(1, new Menu("fill selection with RANDOM pattern with first two blocks in inventory", "anywhere")),
	FILL_BELOW(0, new Menu("fill_below")),
	DROP(1, new Menu("drop")),
	SAVE(1, new Menu("save")),
	LOAD(1, new Menu("load"));

	public int numPicks;
	public Menu description;

	EnumEdit(int numPicks, Menu description) {
		this.numPicks = numPicks;
		this.description = description;
	}
}
