package ds.plato.common;

public class Toggler {

	int i = 0;
	int length;

	public Toggler(int length) {
		this.length = length;
	}

	public int next() {
		if (i == length - 1)
			i = 0;
		else
			i = i + 1;
		return i;
	}

	public int previous() {
		if (i == 0)
			i = length - 1;
		else
			i = i - 1;
		return i;
	}
}
