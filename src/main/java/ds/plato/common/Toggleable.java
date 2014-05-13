package ds.plato.common;

public interface Toggleable {
	
	public Enum next();
	public Enum previous();
	public int numPicks();
	public String getDescription();
	public int getOrdinal();
	public void setState(int ordinal);
	public String getState();

}
