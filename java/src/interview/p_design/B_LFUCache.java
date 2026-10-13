package interview.p_design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import interview.Check;

/*
 * Problem: Least Frequently Used cache, O(1) get/put. Ties broken by least recently used.
 *
 * Approach: three maps
 *   values:  key -> value
 *   counts:  key -> use count
 *   buckets: count -> LinkedHashSet of keys (insertion order = recency within that count)
 * plus minCount. On access move the key from bucket c to c+1; if bucket c became empty and
 * c == minCount, minCount++. On insert of a new key minCount resets to 1.
 */
public class B_LFUCache {

	private final int capacity;
	private int minCount;
	private final Map<Integer, Integer> values = new HashMap<>();
	private final Map<Integer, Integer> counts = new HashMap<>();
	private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();

	B_LFUCache(int capacity) {
		this.capacity = capacity;
	}

	int get(int key) {
		if (!values.containsKey(key)) return -1;
		touch(key);
		return values.get(key);
	}

	void put(int key, int value) {
		if (capacity == 0) return;
		if (values.containsKey(key)) {
			values.put(key, value);
			touch(key);
			return;
		}
		if (values.size() == capacity) {
			LinkedHashSet<Integer> lowest = buckets.get(minCount);
			int evict = lowest.iterator().next();
			lowest.remove(evict);
			values.remove(evict);
			counts.remove(evict);
		}
		values.put(key, value);
		counts.put(key, 1);
		buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
		minCount = 1;
	}

	private void touch(int key) {
		int c = counts.get(key);
		counts.put(key, c + 1);
		LinkedHashSet<Integer> bucket = buckets.get(c);
		bucket.remove(key);
		if (bucket.isEmpty() && c == minCount) minCount++;
		buckets.computeIfAbsent(c + 1, k -> new LinkedHashSet<>()).add(key);
	}

	public static void main(String[] args) {
		B_LFUCache c = new B_LFUCache(2);
		c.put(1, 1);
		c.put(2, 2);
		Check.eq("get 1", c.get(1), 1); // count(1)=2
		c.put(3, 3); // evicts 2 (count 1)
		Check.eq("2 evicted", c.get(2), -1);
		Check.eq("get 3", c.get(3), 3); // count(3)=2
		c.put(4, 4); // tie 1 and 3 at count 2 -> evict LRU among them = 1
		Check.eq("1 evicted", c.get(1), -1);
		Check.eq("get 3 again", c.get(3), 3);
		Check.eq("get 4", c.get(4), 4);
	}
}
