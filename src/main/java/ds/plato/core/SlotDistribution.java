package ds.plato.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;

public class SlotDistribution {

	private final SlotEntry[] slotEntries;
	private final List<Integer> indices = new ArrayList<>();
	private final Random random = new Random();
	private final Map<Integer, Block> mapPercentBlock = new TreeMap<>();

	public SlotDistribution(SlotEntry... slotEntries) {
		this.slotEntries = slotEntries;
		int j = 0;
		for (SlotEntry e : slotEntries) {
			for (int i = 0; i < e.slotNumber; i++) {
				indices.add(j);
			}
			j++;
		}
		if (!indices.isEmpty()) {
			for (int i = 0; i < slotEntries.length; i++) {
				int percentage = 100 * slotEntries[i].slotNumber / indices.size();
				if (i == slotEntries.length - 1) {
					int sum = 0;
					for (Integer p : getPecentages()) {
						sum += p;
					}
					sum += percentage;
					int d = 100 - sum;
					percentage += d;
				}
				mapPercentBlock.put(percentage, slotEntries[i].block);
			}
		}
	}

	public Set<Integer> getPecentages() {
		return mapPercentBlock.keySet();
	}

	public SlotEntry randomEntry() {
		int i = random.nextInt(indices.size() - 1);
		return slotEntries[indices.get(i)];
	}

	@Override
	public String toString() {
		List<String> tokens = new ArrayList();
		for (Entry<Integer, Block> e : mapPercentBlock.entrySet()) {
			// int color = b.getBlockColor();
			String name = e.getValue().getLocalizedName();
			tokens.add(String.format("%s: %d%%", name, e.getKey()));
		}
		return Joiner.on(", ").join(tokens);
	}
}
