package ds.plato.common;

import java.lang.reflect.Field;

public class EnumToggler {

	Enum[] constants;
	int ordinal = 0;
	int length;
	Field numPicks, description;

	public EnumToggler(Class<? extends Enum> cls) {
		this(cls, 0);
	}

	public EnumToggler(Class<? extends Enum> cls, int ordinal) {
		this.constants = cls.getEnumConstants();
		length = constants.length;
		this.ordinal = ordinal;
		try {
			numPicks = cls.getDeclaredField("numPicks");
			description = cls.getDeclaredField("description");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert numPicks != null;
		assert description != null;
	}

	public Enum next() {
		if (ordinal == length - 1)
			ordinal = 0;
		else
			ordinal = ordinal + 1;
		return constants[ordinal];
	}

	public Enum previous() {
		if (ordinal == 0)
			ordinal = length - 1;
		else
			ordinal = ordinal - 1;
		return constants[ordinal];
	}

	public Enum current() {
		return constants[ordinal];
	}

	public void set(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getNumPicks() {
		int n = -1;
		try {
			n = numPicks.getInt(constants[ordinal]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert n != -1;
		return n;
	}

	public Menu getDescription() {
		Menu d = null;
		try {
			d = (Menu) description.get(constants[ordinal]);
		} catch (Exception e) {
			e.printStackTrace();
			assert d != null;
		}
		return d;
	}

	@Override
	public String toString() {
		return "EnumToggler [current()=" + current() + "]";
	}

}
