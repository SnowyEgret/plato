package ds.plato.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;

public class SlotDistribution {

	private final List<SlotEntry> slotEntryList;
	private final List<Integer> indices = new ArrayList<>();
	private final Random random = new Random();
	private final Map<Integer, Block> distributionPercentage = new TreeMap<>();

	public SlotDistribution(List<SlotEntry> slotEntryList) {
		this.slotEntryList = slotEntryList;
		int i = 0;
		for (SlotEntry e : slotEntryList) {
			for (int j = 0; j < e.slotNumber; j++) {
				indices.add(i);
			}
			i++;
		}
		if (!indices.isEmpty()) {
			for (SlotEntry e : slotEntryList) {
				int percentage = 100 * e.slotNumber / indices.size();
				distributionPercentage.put(percentage, e.block);
			}
		}
	}

	public SlotDistribution(SlotEntry[] slotEntries) {
		this(Lists.asList(slotEntries[0], slotEntries));
	}

	public SlotEntry randomEntry() {
		int i = random.nextInt(indices.size() - 1);
		int ii = indices.get(i);
		return slotEntryList.get(ii);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		Set<Entry<Integer, Block>> entries = distributionPercentage.entrySet();
		// Arrays.s
		for (Entry<Integer, Block> e : entries) {
			s.append(":\n");
			s.append(e.getValue().getLocalizedName());
			s.append(": ");
			s.append(e.getKey());
			s.append("%");
		}
		return s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((slotEntryList == null) ? 0 : slotEntryList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlotDistribution other = (SlotDistribution) obj;
		if (slotEntryList == null) {
			if (other.slotEntryList != null)
				return false;
		} else if (!slotEntryList.equals(other.slotEntryList))
			return false;
		return true;
	}

}
