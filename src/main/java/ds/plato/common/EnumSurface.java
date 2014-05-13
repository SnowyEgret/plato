package ds.plato.common;

public enum EnumSurface {

	DISK(2, new Menu("disk")),
	TORUS(3, new Menu("torus")),
	SPHERE(2, new Menu("sphere")),
	ELLIPTIC_PARABOLOID(3, new Menu("elliptic_paraboloid")),
	CONE(3, new Menu("cone")),
	DINIS_SURFACE(3, new Menu("dinis_surface")),
	FRACTAL_TERRAIN(1, new Menu("fractal_terrain")),
	IMAGE_TERRAIN(1, new Menu("image_terrain"));

	public int numPicks;
	public Menu description;

	private EnumSurface(int numPicks, Menu description) {
		this.numPicks = numPicks;
		this.description = description;
	}
}

