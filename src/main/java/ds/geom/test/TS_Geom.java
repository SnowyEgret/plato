package ds.geom.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ T_Circle.class, T_PointSet.class, T_Sphere.class,
		T_GeomUtil.class, TestVecmath.class, PT_GeomUtil_newTranslationMatrix.class,
		PT_GeomUtil_newRotationMatrix_VectorVector.class, PT_GeomUtil_newReflectionMatrix.class,
		PT_GeomUtil_newScaleMatrix.class })
public class TS_Geom {

}
