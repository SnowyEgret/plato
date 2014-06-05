package ds.plato.geom.surface;

import javax.vecmath.Point3d;

import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.plugins.AWTLoader;

public class ImageBasedTerrain extends Terrain {

	private final String filename;

	public ImageBasedTerrain(Point3d origin, int sizeAsPowerOfTwo, double range, String filename) {
		super(origin, sizeAsPowerOfTwo, range);
		this.filename = filename;
		init();
	}

	@Override
	protected HeightMap createHeightMap() {
		AssetManager assetManager = new DesktopAssetManager();
		assetManager.registerLoader(AWTLoader.class, "png");
		assetManager.registerLocator("", FileLocator.class);
		Texture texture = assetManager.loadTexture(filename);
		//TODO check size of texture against parameter sizeAsPowerOfTwo
		System.out.println("[ImageBasedTerrain.createHeightMap] texture=" + texture);
		HeightMap map = new ImageBasedHeightMap(texture.getImage());
		map.load();
		return map;
	}

	@Override
	public String toString() {
		return "ImageBasedTerrain [rU=" + rU + ", rV=" + rV + ", p0=" + p0 + "]";
	}

}
