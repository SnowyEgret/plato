package ds.plato.common;

public enum EnumCurve {

	LINE(2), CIRCLE(2), ARC(2), HELIX(3), RECTANGLE(2), SQUARE(2);

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
