package ds.plato.spell;

public interface IToggleable {
	
	public enum Direction {
		NEXT, PREVIOUS
	}
	
	public void toggle(Direction direction);

}
