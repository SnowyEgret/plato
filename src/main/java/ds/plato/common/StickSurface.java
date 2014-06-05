package ds.plato.common;

import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ds.plato.geom.Cone;
import ds.plato.geom.DinisSurface;
import ds.plato.geom.DiskXZ;
import ds.plato.geom.Drawable;
import ds.plato.geom.EllipticParaboloid;
import ds.plato.geom.FractalTerrain;
import ds.plato.geom.ImageBasedTerrain;
import ds.plato.geom.Sphere;
import ds.plato.geom.Torus;

@Deprecated
public class StickSurface extends Stick {

//	EnumSurface state = EnumSurface.values()[0];
//	int x = 0, y = 0, z = 0;
//	boolean drawn = false;

	public StickSurface(Property initialState) {
		super(2, initialState, EnumSurface.class);
		//state.currentShape = EnumSurface.values()[initialState.getInt()];
	}

	@Override
	protected void onClickRight(PlayerInteractEvent e) {
//		if (drawn) {
//			if (e.isCancelable())
//				e.setCanceled(true);
//			return;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
//			x = (int) Minecraft.getMinecraft().thePlayer.posX;
//			y = (int) Minecraft.getMinecraft().thePlayer.posY;
//			z = (int) Minecraft.getMinecraft().thePlayer.posZ;
//			MOD.log.info("[ItemStickSurface.onClickRight] y=" + y);
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			this.onClickRight(e);
//		} else {
//			if (x == 0) {
//				x = e.x;
//				y = e.y;
//				z = e.z;
//			}
//		}

		if (pick(e.x, e.y, e.z)) {
			Drawable drawable = null;
			switch ((EnumSurface)state.current()) {
			case DISK:
				drawable = new DiskXZ(getPick(0), getPick(1));
				break;
			case TORUS:
				drawable = new Torus(getPick(0), getPick(1), getPick(2));
				break;
			case SPHERE:
				drawable = new Sphere(getPick(0), getPick(1));
				break;
			case ELLIPTIC_PARABOLOID:
				drawable = new EllipticParaboloid(getPick(0), getPick(1), getPick(2));
				break;
			case CONE:
				drawable = new Cone(getPick(0), getPick(1), getPick(2));
				break;
			case DINIS_SURFACE:
				drawable = new DinisSurface(getPick(0), getPick(1), getPick(2));
				break;
			case FRACTAL_TERRAIN:
				drawable = new FractalTerrain(getPick(0), 7, 20);
				break;
			case IMAGE_TERRAIN:
				drawable = new ImageBasedTerrain(getPick(0), 8, 75, "heightmap256.png");
				break;
			default:
				break;
			}
			draw(drawable, false);
			//drawn = true;
			clearPicks();
		} else {
//			x = 0;
//			y = 0;
//			z = 0;
			Plato.clearSelections();
		}
		if (e.isCancelable())
			e.setCanceled(true);
	}

//	@Override
//	public void toggle() {
//		state = state.next();
//		resetPickManager(state.numPicks);
//		printCurrentState();
//	}
//
//	@Override
//	public void printCurrentState() {
//		System.out.println("[ItemStickSurface.printCurrentState] state=" + state);
//	}
//
//	@Override
//	public void save() {
//		initialState.set(state.ordinal());
//	}
}
