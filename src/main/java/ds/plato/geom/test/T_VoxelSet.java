package ds.plato.geom.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Vector3d;

import org.junit.Test;

import ds.plato.geom.Ball;
import ds.plato.geom.Cube;
import ds.plato.geom.GeomUtil;
import ds.plato.geom.IntegerDomain;
import ds.plato.geom.Primitive;
import ds.plato.geom.Sphere;
import ds.plato.geom.VoxelSet;

public class T_VoxelSet extends GeomTest {

	@Test
	public void core_shell() {
		scale = 40;
		for (int i = 0; i < 10; i++) {
			Primitive p = new Ball(p(), p());
			VoxelSet voxels = p.voxelize();
			int sizeAll = voxels.size();
			VoxelSet core = voxels.core();
			VoxelSet shell = voxels.shell();
			assertThat(sizeAll, equalTo(core.size() + shell.size()));
		}
	}

	@Test
	public void centroid() {
		for (int i = 0; i < 10; i++) {
			Point3d o = p();
			Primitive p = new Sphere(o, p());
			Point3i c = p.voxelize().centroid();
			assertThat(c, equalTo(GeomUtil.toPoint3i(o)));
		}
	}

	@Test
	public void transform() {
		for (int i = 0; i < 10; i++) {
			VoxelSet voxels = new VoxelSet();
			Point3i p = p3i();
			voxels.add(p);
			Point3i d = p3i();
			Vector3d t = new Vector3d(d.x, d.y, d.z);
			voxels = voxels.transform(GeomUtil.newTranslationMatrix(t));
			Point3i c = voxels.randomPoint();
			p.x += d.x;
			p.y += d.y;
			p.z += d.z;
			assertThat(c, equalTo(p));
		}
	}

	@Test
	public void divide() {
		// int n = 2;
		// int n = 5;
		int n = 8;
		Primitive cube = new Cube(p(0), p(n));
		// System.out.println("[T_VoxelSet.divide] cube=" + cube);
		VoxelSet voxels = cube.voxelize();
		assertThat(voxels.size(), equalTo((int) Math.pow(n + 1, 3)));

		int size = voxels.size();
		Iterable<VoxelSet> sets = voxels.divide(3);
		int i = 0;
		for (VoxelSet s : sets) {
			// System.out.println("[T_VoxelSet.divide] s.size()=" + s.size());
			assertThat(s.size(), equalTo(size / 27));
			i++;
		}
		assertThat(i, equalTo(27));
	}

	@Test
	public void divideDomain() {
		// int n = 2;
		// int n = 5;
		// int n = 8;
		int n = 9;
		Primitive cube = new Cube(p(1), p(9));
		// System.out.println("[T_VoxelSet.divideDomain] cube=" + cube);
		VoxelSet voxels = cube.voxelize();
		assertThat(voxels.size(), equalTo((int) Math.pow(n, 3)));

		int size = voxels.size();
		Iterable<IntegerDomain> domains = voxels.divideDomain(3);
		int i = 0;
		for (IntegerDomain d : domains) {
			// System.out.println("[T_VoxelSet.divideDomain] d=" + d);
			// assertThat(s., equalTo(size / 27));
			i++;
		}
		assertThat(i, equalTo(27));
	}

	@Test
	public void getDomain() {
		double a = d();
		double b = d();
		System.out.println("[T_VoxelSet.getDomain] a=" + a);
		System.out.println("[T_VoxelSet.getDomain] b=" + b);
		Primitive cube = new Cube(p(a), p(b));
		System.out.println("[T_VoxelSet.getDomain] cube=" + cube);

		IntegerDomain d = cube.voxelize().getDomain();
		System.out.println("[T_VoxelSet.getDomain] d.rx=" + d.rx);
		assertThat(d.rx.getMinimum(), equalTo(a < b ? (int) a : (int) b));
		assertThat(d.rx.getMaximum(), equalTo(b > a ? (int) b : (int) a));
	}
}
