package ds.plato.spell;

import java.util.Arrays;

import net.minecraft.item.Item;

import org.apache.commons.lang3.tuple.Pair;

public class Recipe {


	private Ingredient[] ingredients;

	public Recipe(Ingredient...ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Recipe [ingredients=");
		builder.append(Arrays.toString(ingredients));
		builder.append("]");
		return builder.toString();
	}


}
