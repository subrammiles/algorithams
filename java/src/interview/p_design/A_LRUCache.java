package interview.p_design;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import interview.Check;

/*
 * Problem: Least Recently Used cache with O(1) get and put.
 *
 * Approach: HashMap key -> node + doubly linked list ordered by recency.
 *   get: move node to front. put: insert at front; if over capacity, evict the tail.
 * Sentinel head/tail nodes remove all null checks.
 *
 * Also know the one-liner: LinkedHashMap(accessOrder = true) + removeEldestEntry.
 *
 * Senior follow-ups:
 *  - Thread safety: one lock around get/put (simple), or segment the cache by key hash.
 *  - TTL expiry: store expiresAt per node; lazy check on get + background sweeper.
 *  - Distributed cache: consistent hashing across nodes (see D_ConsistentHashing).
 *  - LRU vs LFU vs ARC trade-offs (scan resistance).
 */
public class A_LRUCache {

	private static class Node {
		int key, value;
		Node prev, next;

		Node(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	private final int capacity;
	private final Map<Integer, Node> map = new HashMap<>();
	private final Node head = new Node(0, 0), tail = new Node(0, 0);

	A_LRUCache(int capacity) {
		this.capacity = capacity;
		head.next = tail;
		tail.prev = head;
	}

	int get(int key) {
		Node n = map.get(key);
		if (n == null) return -1;
		unlink(n);
		addFront(n);
		return n.value;
	}

	void put(int key, int value) {
		Node n = map.get(key);
		if (n != null) {
			n.value = value;
			unlink(n);
			addFront(n);
			return;
		}
		if (map.size() == capacity) {
			Node lru = tail.prev;
			unlink(lru);
			map.remove(lru.key);
		}
		n = new Node(key, value);
		map.put(key, n);
		addFront(n);
	}

	private void unlink(Node n) {
		n.prev.next = n.next;
		n.next.prev = n.prev;
	}

	private void addFront(Node n) {
		n.next = head.next;
		n.prev = head;
		head.next.prev = n;
		head.next = n;
	}

	/* The library version - good to mention, but interviewers usually want the manual one. */
	static class LinkedHashMapLRU<K, V> extends LinkedHashMap<K, V> {
		private static final long serialVersionUID = 1L;
		private final int capacity;

		LinkedHashMapLRU(int capacity) {
			super(16, 0.75f, true);
			this.capacity = capacity;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			return size() > capacity;
		}
	}

	public static void main(String[] args) {
		A_LRUCache c = new A_LRUCache(2);
		c.put(1, 1);
		c.put(2, 2);
		Check.eq("get 1", c.get(1), 1);
		c.put(3, 3); // evicts 2
		Check.eq("get 2 evicted", c.get(2), -1);
		c.put(4, 4); // evicts 1
		Check.eq("get 1 evicted", c.get(1), -1);
		Check.eq("get 3", c.get(3), 3);
		Check.eq("get 4", c.get(4), 4);

		LinkedHashMapLRU<Integer, Integer> lib = new LinkedHashMapLRU<>(2);
		lib.put(1, 1);
		lib.put(2, 2);
		lib.get(1);
		lib.put(3, 3);
		Check.eq("library LRU keys", lib.keySet().toString(), "[1, 3]");
	}
}
