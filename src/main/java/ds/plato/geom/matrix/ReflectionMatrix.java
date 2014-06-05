package ds.plato.geom.matrix;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import ds.plato.geom.GeomUtil;

public class ReflectionMatrix extends Matrix4d {

	public ReflectionMatrix(Point3d p0, Point3d p1, Point3d p2) {
		Matrix4d mT = new TranslationMatrix(new Vector3d(p0));
		Matrix4d mR = new RotationMatrix(new Vector3d(0, 0, 1), GeomUtil.normalToPlane(p0, p1, p2));
		setIdentity();
		mul(mT);
		mul(mR);
		mul(new ScaleMatrix(new Vector3d(0, 0, -1)));
		mR.invert();
		mul(mR);
		mT.invert();
		mul(mT);
	}
}
