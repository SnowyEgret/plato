package ds.plato.spell.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import ds.plato.core.IWorld;

public class Shell implements Iterable<Point3i> {

	public enum Type {
		ALL,
		XYZ,
		HORIZONTAL,
		UP,
		DOWN,
		ABOVE,
		BELLOW,
		VERTICAL_XY,
		VERTICAL_ZY,
		FLOOR,
		CEILING,
		FLOOR_EDGE,
		CEILING_EDGE,
		X,
		Y,
		Z,
		XY,
		XZ,
		YZ
	}

	private List<Point3i> points = new ArrayList<>();
	private final Type type;

	public Shell(Type type, Point3i p0, IWorld w) {
		this.type = type;
		
		List<Point3i> noCorners = new ArrayList<>();
		noCorners.add(new Point3i(0, -1, 0));
		noCorners.add(new Point3i(0, 0, -1));
		noCorners.add(new Point3i(-1, 0, 0));
		noCorners.add(new Point3i(1, 0, 0));
		noCorners.add(new Point3i(0, 0, 1));
		noCorners.add(new Point3i(0, 1, 0));
		for (Point3i p : noCorners) {
			p.add(p0);
		}
		
		List<Point3i> allPoints = new ArrayList<>();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					Point3i p = new Point3i(p0.x + x - 1, p0.y + y - 1, p0.z + z - 1);
					if (!p.equals(p0))
						allPoints.add(p);
				}
			}
		}

		switch (type) {
		case ALL:
			points = allPoints;
			break;
		case HORIZONTAL:
			for (Point3i p : noCorners) {
				if (p.y == p0.y)
					points.add(p);
			}
			break;
		case ABOVE:
			for (Point3i p : noCorners) {
				if (p.y >= p0.y)
					points.add(p);
			}
			break;
		case BELLOW:
			for (Point3i p : noCorners) {
				if (p.y <= p0.y)
					points.add(p);
			}
			break;
		case XYZ:
			points = noCorners;
			break;
		case VERTICAL_XY:
			for (Point3i p : noCorners) {
				if (p.x == p0.x)
					points.add(p);
			}
			break;
		case VERTICAL_ZY:
			for (Point3i p : noCorners) {
				if (p.z == p0.z)
					points.add(p);
			}
			break;
		case DOWN:
			for (Point3i p : noCorners) {
				if (p.z == p0.z && p.x == p0.x && p.y < p0.y)
					points.add(p);
			}
			break;
		case UP:
			for (Point3i p : noCorners) {
				if (p.z == p0.z && p.x == p0.x && p.y > p0.y)
					points.add(p);
			}
			break;
		case FLOOR:
			for (Point3i p : noCorners) {
				Block b = w.getBlock(p.x, p.y + 1, p.z);
				if (p.y == p0.y && b == Blocks.air)
					points.add(p);
			}
			break;
		case CEILING:
			for (Point3i p : noCorners) {
				Block b = w.getBlock(p.x, p.y - 1, p.z);
				if (p.y == p0.y && b == Blocks.air)
					points.add(p);
			}
			break;
		case FLOOR_EDGE:
			for (Point3i p : allPoints) {
				if (p.y == p0.y) {
					if (w.getBlock(p.x, p.y + 1, p.z) == Blocks.air) {
						Shell s = new Shell(Type.ABOVE, p, w);
						for (Point3i pp : s) {
							if (pp.y > p.y && w.getBlock(pp.x, pp.y, pp.z) != Blocks.air) {
								points.add(p);
								break;
							}
						}
					}
				}
			}
			break;
		case CEILING_EDGE:
			for (Point3i p : allPoints) {
				if (p.y == p0.y) {
					if (w.getBlock(p.x, p.y - 1, p.z) == Blocks.air) {
						Shell s = new Shell(Type.BELLOW, p, w);
						for (Point3i pp : s) {
							if (pp.y < p.y && w.getBlock(pp.x, pp.y, pp.z) != Blocks.air) {
								points.add(p);
								break;
							}
						}
					}
				}
			}
			break;
		case X:
			for (Point3i p : noCorners) {
				if (p.z == p0.z && p.y == p0.y)
					points.add(p);
			}
			break;
		case Y:
			for (Point3i p : noCorners) {
				if (p.z == p0.z && p.x == p0.x)
					points.add(p);
			}
			break;
		case Z:
			for (Point3i p : noCorners) {
				if (p.x == p0.x && p.y == p0.y)
					points.add(p);
			}
			break;
		case XY:
			for (Point3i p : noCorners) {
				if (p.z == p0.z)
					points.add(p);
			}
			break;
		case XZ:
			for (Point3i p : noCorners) {
				if (p.y == p0.y)
					points.add(p);
			}
			break;
		case YZ:
			for (Point3i p : noCorners) {
				if (p.x == p0.x)
					points.add(p);
			}
			break;
		default:
			break;
		}
	}

//	private static List<Point3i> initPoints() {
//		System.out.println("[Shell.initPoints]");
//		List<Point3i> pts = new ArrayList<>();
//		pts.add(new Point3i(0, -1, 0));
//		pts.add(new Point3i(0, 0, -1));
//		pts.add(new Point3i(-1, 0, 0));
//		pts.add(new Point3i(1, 0, 0));
//		pts.add(new Point3i(0, 0, 1));
//		pts.add(new Point3i(0, 1, 0));
//		return pts;
//	}

	@Override
	public String toString() {
		return "Shell [type=" + type + ", points=" + points + "]";
	}

	@Override
	public Iterator iterator() {
		return points.iterator();
	}

}
