package ds.plato.spell.select;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Point3i;

import ds.plato.core.IWorld;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Shell implements Iterable<Point3i> {

	private List<Point3i> points = new ArrayList();
	private final EnumShell type;

	public Shell(EnumShell type, Point3i p0, IWorld w) {
		this.type = type;

		List<Point3i> pts = new ArrayList<>();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					Point3i p = new Point3i(p0.x + x - 1, p0.y + y - 1, p0.z + z - 1);
					if (!p.equals(p0))
						pts.add(p);
				}
			}
		}

		switch (type) {
		case HORIZONTAL:
			for (Point3i p : pts) {
				if (p.y == p0.y)
					points.add(p);
			}
			break;
		case FLOOR:
			for (Point3i p : pts) {
				Block b = w.getBlock(p.x, p.y + 1, p.z);
				if (p.y == p0.y && b == Blocks.air)
					points.add(p);
			}
			break;
		case CEILING:
			for (Point3i p : pts) {
				Block b = w.getBlock(p.x, p.y - 1, p.z);
				if (p.y == p0.y && b == Blocks.air)
					points.add(p);
			}
			break;
		case TOP:
			for (Point3i p : pts) {
				if (p.y >= p0.y)
					points.add(p);
			}
			break;
		case BOTTOM:
			for (Point3i p : pts) {
				if (p.y <= p0.y)
					points.add(p);
			}
			break;
		case ALL:
			points = pts;
			break;
		case VERTICAL_XY:
			for (Point3i p : pts) {
				if (p.x == p0.x)
					points.add(p);
			}
			break;
		case VERTICAL_ZY:
			for (Point3i p : pts) {
				if (p.z == p0.z)
					points.add(p);
			}
			break;
		case DOWN:
			for (Point3i p : pts) {
				if (p.z == p0.z && p.x == p0.x && p.y < p0.y)
					points.add(p);
			}
			break;
		case UP:
			for (Point3i p : pts) {
				if (p.z == p0.z && p.x == p0.x && p.y > p0.y)
					points.add(p);
			}
			break;
		case FLOOR_EDGE:
			for (Point3i p : pts) {
				if (p.y == p0.y) {
					if (w.getBlock(p.x, p.y + 1, p.z) == Blocks.air) {
						Shell s = new Shell(EnumShell.TOP, p, w);
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
			for (Point3i p : pts) {
				if (p.y == p0.y) {
					if (w.getBlock(p.x, p.y - 1, p.z) == Blocks.air) {
						Shell s = new Shell(EnumShell.BOTTOM, p, w);
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
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return "Shell [type=" + type + ", points=" + points + "]";
	}

	@Override
	public Iterator iterator() {
		return points.iterator();
	}

}
