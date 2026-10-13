package interview.p_design;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import interview.Check;

/*
 * Consistent hashing - how distributed caches / databases (Dynamo, Cassandra) assign keys to nodes.
 *
 * Problem with hash(key) % N: adding or removing one node remaps almost every key.
 * Ring approach: hash nodes and keys onto the same ring (0..2^32); a key belongs to the first
 * node clockwise. Adding/removing a node moves only ~1/N of the keys.
 * Virtual nodes: place each physical node many times on the ring to even out the load.
 *
 * TreeMap.ceilingKey / tailMap gives the "first node clockwise" in O(log(nodes * replicas)).
 */
public class D_ConsistentHashing {

	private final TreeMap<Long, String> ring = new TreeMap<>();
	private final int virtualNodes;

	D_ConsistentHashing(int virtualNodes) {
		this.virtualNodes = virtualNodes;
	}

	void addNode(String node) {
		for (int i = 0; i < virtualNodes; i++) ring.put(hash(node + "#" + i), node);
	}

	void removeNode(String node) {
		for (int i = 0; i < virtualNodes; i++) ring.remove(hash(node + "#" + i));
	}

	String nodeFor(String key) {
		if (ring.isEmpty()) throw new IllegalStateException("No nodes");
		SortedMap<Long, String> tail = ring.tailMap(hash(key));
		return tail.isEmpty() ? ring.firstEntry().getValue() : tail.get(tail.firstKey());
	}

	private static long hash(String s) {
		try {
			byte[] d = MessageDigest.getInstance("MD5").digest(s.getBytes(StandardCharsets.UTF_8));
			return ((long) (d[3] & 0xFF) << 24) | ((d[2] & 0xFF) << 16) | ((d[1] & 0xFF) << 8) | (d[0] & 0xFF);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void main(String[] args) {
		D_ConsistentHashing ch = new D_ConsistentHashing(100);
		ch.addNode("cache-A");
		ch.addNode("cache-B");
		ch.addNode("cache-C");
		int keys = 10_000;
		Map<String, String> before = new HashMap<>();
		Map<String, Integer> load = new HashMap<>();
		for (int i = 0; i < keys; i++) {
			String node = ch.nodeFor("user:" + i);
			before.put("user:" + i, node);
			load.merge(node, 1, Integer::sum);
		}
		System.out.println("Load with 3 nodes: " + load);
		for (int count : load.values()) Check.isTrue("balanced-ish (" + count + ")", count > keys / 5 && count < keys / 2);

		ch.addNode("cache-D");
		int moved = 0;
		boolean onlyToNewNode = true;
		for (int i = 0; i < keys; i++) {
			String now = ch.nodeFor("user:" + i);
			if (!now.equals(before.get("user:" + i))) {
				moved++;
				onlyToNewNode &= now.equals("cache-D");
			}
		}
		Check.isTrue("moved keys only go to the new node", onlyToNewNode);
		System.out.println("Keys moved after adding a 4th node: " + moved + " of " + keys);
		Check.isTrue("about 1/4 moved, not ~all", moved < keys / 2);
	}
}
