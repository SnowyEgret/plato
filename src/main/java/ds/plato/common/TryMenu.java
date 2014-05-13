package ds.plato.common;

import java.util.ArrayList;
import java.util.List;

public class TryMenu {

	TryMenu() {
		Menu m = new Menu(new Move(), new Move());
		System.out.println("[TryMenu.TryMenu] m=" + m);
	}

	public class Menu {
		public Menu(MenuItem... meunuItems) {
			for (MenuItem i : meunuItems) {
				this.items.add(i);
			}
		}

		List<MenuItem> items = new ArrayList();

		@Override
		public String toString() {
			return "Menu [items=" + items + "]";
		}
		
	}

	public class MenuItem {
		public MenuItem(int i, String string) {
			this.numPicks = i;
			this.name = string;
		}

		int numPicks;
		String name;
		@Override
		public String toString() {
			return "MenuItem [numPicks=" + numPicks + ", name=" + name + "]";
		}
		
	}

	public class Move extends MenuItem {
		Move() {
			super(2, "Move");
		}
	}

	public static void main(String[] args) {
		new TryMenu();

	}
}
