package ds.plato.test;


import com.google.inject.Provider;

import ds.plato.IWorld;

public class PlatoTestFactory {

	private final MockWorld world = new MockWorld();
	
	protected IWorld mockWorld() {
		return world;
	}
	
	protected Provider<IWorld> mockWorldProvider() {
		return new Provider() {
			@Override
			public Object get() {
				return world;
			}			
		};
	}
}
