package interview.p_design;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import interview.Check;

/*
 * Problem: set(key, value, timestamp); get(key, timestamp) returns the value with the largest
 * timestamp <= the given one (a tiny version of MVCC / snapshot reads in databases).
 *
 * Approach: key -> TreeMap<timestamp, value>; floorEntry is O(log n).
 * If timestamps arrive strictly increasing, an ArrayList + binary search is lighter.
 *
 * Senior extensions: snapshot isolation, garbage-collecting old versions,
 * persisting with an append-only log + in-memory index (LSM-tree idea).
 */
public class F_TimeBasedKeyValueStore {

	private final Map<String, TreeMap<Integer, String>> store = new HashMap<>();

	void set(String key, String value, int timestamp) {
		store.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
	}

	String get(String key, int timestamp) {
		TreeMap<Integer, String> versions = store.get(key);
		if (versions == null) return "";
		Map.Entry<Integer, String> e = versions.floorEntry(timestamp);
		return e == null ? "" : e.getValue();
	}

	public static void main(String[] args) {
		F_TimeBasedKeyValueStore kv = new F_TimeBasedKeyValueStore();
		kv.set("foo", "bar", 1);
		Check.eq("t=1", kv.get("foo", 1), "bar");
		Check.eq("t=3", kv.get("foo", 3), "bar");
		kv.set("foo", "bar2", 4);
		Check.eq("t=4", kv.get("foo", 4), "bar2");
		Check.eq("t=0 before any", kv.get("foo", 0), "");
		Check.eq("missing key", kv.get("nope", 5), "");
	}
}
