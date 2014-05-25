package ds.geom.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PT_GeomUtil_newReflectionMatrix.class, PT_GeomUtil_newRotationMatrix_VectorVector.class,
		PT_GeomUtil_newScaleMatrix.class, PT_GeomUtil_newTranslationMatrix.class, PT_Line_containsPoint.class,
		PT_Line_distancePerp.class, PT_Plane_containsPoint.class, PT_Plane_distancePerp.class,
		PT_Plane_intersectLine.class, PT_Plane_normal.class, PT_Plane_project.class, T_Ball.class, T_Box.class,
		T_Circle.class, T_Cube.class, T_Disk.class, T_EllipticParaboloid.class, T_FractalTerrain.class,
		T_GeomTest.class, T_GeomUtil.class, T_Helix.class, T_ImageBasedTerrain.class, T_InfinatePlane.class,
		T_IntegerDomain.class, T_Line.class, T_MengerSponge.class, T_PointSet.class, T_Sphere.class,
		T_Tetrahedron.class, T_Torus.class, T_VoxelSet.class })
public class TS_Geom {

}
