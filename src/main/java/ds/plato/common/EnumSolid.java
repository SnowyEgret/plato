package ds.plato.common;

import org.apache.commons.lang3.tuple.Pair;

public enum EnumSolid {

	BALL(2, new Menu("ball", new String[] { "center", "point on surface" }, Pair.of("ctrl", "hollow"))),
	BOX(2, new Menu("box")),
	PYRAMID(2, new Menu("pyramid")),
	TETRAHEDRON(2, new Menu("tetrahedron")),
	WATER(1, new Menu("water")),
	CUBE(2, new Menu("cube", new String[] { "center", "diagonal corner" }, Pair.of("ctrl", "hollow"))),
	SPONGE(2, new Menu("sponge", new String[] { "center", "diagonal corner" }, Pair.of("ctrl", "hollow")));

	public int numPicks;
	public Menu description;

	EnumSolid(int numPicks, Menu description) {
		this.numPicks = numPicks;
		this.description = description;
	}
}
