package ds.plato.common;

import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.geom.CircleXY;
import ds.plato.geom.Drawable;
import ds.plato.geom.Helix;
import ds.plato.geom.Line;
import ds.plato.geom.Rectangle;
import ds.plato.geom.Square;

@Deprecated
public class StickCurve extends Stick {

//	EnumCurve state = EnumCurve.values()[0];

	public StickCurve(Property initialState) {
		super(2, initialState, EnumCurve.class);
		//state.currentShape = EnumCurve.values()[initialState.getInt()];
	}

	@Override
	protected void onClickRight(PlayerInteractEvent e) {
		if (pick(e.x, e.y, e.z)) {
			Drawable drawable = null;
			switch ((EnumCurve)state.current()) {
			case LINE:
				drawable = new Line(getPick(0), getPick(1));
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					draw(drawable, false);
					clearPicks();
					resetPickManager(2);
					pick(e.x, e.y, e.z);
					if (e.isCancelable())
						e.setCanceled(true);
					return;
				} else {
					break;
				}
			case CIRCLE:
				drawable = new CircleXY(getPick(0), getPick(1));
				break;
			case ARC:
				throw new UnsupportedOperationException(
						"Method 'ItemStickCurve.onClickRight case ARC' not yet implemented");
				// drawable = new Arc(getPick(0), getPick(1), getPick(2));
				// break;
			case HELIX:
				drawable = new Helix(getPick(0), getPick(1), getPick(2), 5);
				break;
			case RECTANGLE:
				drawable = new Rectangle(getPick(0), getPick(1));
				break;
			case SQUARE:
				drawable = new Square(getPick(0), getPick(1));
				break;
			default:
				break;
			}
			draw(drawable, false);
			clearPicks();
		} else {
			Plato.clearSelections();
		}
		Plato.log.info("[ItemStickCurve.onClickRight] Num selections=" + Plato.selectionManager.size());
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
//		System.out.println("[ItemStickCurve.printCurrentState] state=" + state);
//	}
//
//	@Override
//	public void save() {
//		initialState.set(state.ordinal());
//	}
}
