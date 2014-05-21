package ds.plato.common;

public enum EnumCurve {

	LINE(2, new Menu("line")),
	CIRCLE(2, new Menu("circle")),
	ARC(2, new Menu("arc")),
	HELIX(3, new Menu("helix")),
	RECTANGLE(2, new Menu("rectangle")),
	SQUARE(2, new Menu("square"));

	public int numPicks;
	public Menu description;

	private EnumCurve(int numPicks, Menu description) {
		this.numPicks = numPicks;
		this.description = description;
	}

	EnumCurve(int numPicks) {
		this(numPicks, new Menu());
	}
}
