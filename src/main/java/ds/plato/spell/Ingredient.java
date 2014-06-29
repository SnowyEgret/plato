package ds.plato.spell;

import java.util.Arrays;

import net.minecraft.item.Item;

public class Ingredient {

	private Item item;
	private int[] positions;

	public Ingredient(Item item, int...positions) {
		this.item = item;
		this.positions = positions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ingredient [item=");
		builder.append(item);
		builder.append(", positions=");
		builder.append(Arrays.toString(positions));
		builder.append("]");
		return builder.toString();
	}

}

