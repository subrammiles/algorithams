package interview.p_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import interview.Check;

/*
 * Problem: Set with insert, remove and getRandom, all O(1) average.
 *
 * Approach: ArrayList for O(1) random index + HashMap value -> index.
 * Remove trick: overwrite the removed slot with the LAST element, fix its index, drop the last slot.
 *
 * Variant with duplicates: value -> Set of indices.
 */
public class E_InsertDeleteGetRandom {

	private final List<Integer> values = new ArrayList<>();
	private final Map<Integer, Integer> index = new HashMap<>();
	private final Random random;

	E_InsertDeleteGetRandom(long seed) {
		random = new Random(seed);
	}

	boolean insert(int val) {
		if (index.containsKey(val)) return false;
		index.put(val, values.size());
		values.add(val);
		return true;
	}

	boolean remove(int val) {
		Integer i = index.remove(val);
		if (i == null) return false;
		int last = values.remove(values.size() - 1);
		if (i < values.size()) {
			values.set(i, last);
			index.put(last, i);
		}
		return true;
	}

	int getRandom() {
		return values.get(random.nextInt(values.size()));
	}

	public static void main(String[] args) {
		E_InsertDeleteGetRandom set = new E_InsertDeleteGetRandom(7);
		Check.isTrue("insert 1", set.insert(1));
		Check.eq("remove missing", set.remove(2), false);
		Check.isTrue("insert 2", set.insert(2));
		Check.isTrue("insert 3", set.insert(3));
		Check.eq("insert dup", set.insert(2), false);
		Check.isTrue("remove 1", set.remove(1));
		int[] seen = new int[4];
		for (int i = 0; i < 1000; i++) seen[set.getRandom()]++;
		Check.eq("1 never returned", seen[1], 0);
		Check.isTrue("2 and 3 both returned", seen[2] > 300 && seen[3] > 300);
	}
}
