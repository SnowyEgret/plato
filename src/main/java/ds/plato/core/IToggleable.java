package ds.plato.core;

public interface IToggleable {
	
	public enum Direction {
		NEXT, PREVIOUS
	}
	
	public void toggle(Direction direction);

}
