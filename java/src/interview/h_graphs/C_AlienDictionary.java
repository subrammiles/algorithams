package interview.h_graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import common.Check;

/*
 * Problem: Words are sorted in an unknown alphabet. Derive a valid letter order, or "" if invalid.
 *
 * Approach: Compare each adjacent pair of words; the FIRST differing letter gives one edge u -> v.
 * Then topological sort (Kahn). Two traps interviewers look for:
 *   1. Prefix rule: "abc" before "ab" is invalid -> return "".
 *   2. Letters with no edges must still appear in the output.
 * O(total characters).
 * (TreeMap/TreeSet only to make the output deterministic for the checks.)
 */
public class C_AlienDictionary {

	static String alienOrder(String[] words) {
		Map<Character, Set<Character>> graph = new TreeMap<>();
		Map<Character, Integer> indegree = new HashMap<>();
		for (String w : words) {
			for (char c : w.toCharArray()) {
				graph.putIfAbsent(c, new TreeSet<>());
				indegree.putIfAbsent(c, 0);
			}
		}
		for (int i = 0; i + 1 < words.length; i++) {
			String a = words[i], b = words[i + 1];
			if (a.length() > b.length() && a.startsWith(b)) return "";
			for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
				char u = a.charAt(j), v = b.charAt(j);
				if (u != v) {
					if (graph.get(u).add(v)) indegree.merge(v, 1, Integer::sum);
					break;
				}
			}
		}
		Deque<Character> queue = new ArrayDeque<>();
		for (char c : graph.keySet()) if (indegree.get(c) == 0) queue.offer(c);
		StringBuilder order = new StringBuilder();
		while (!queue.isEmpty()) {
			char c = queue.poll();
			order.append(c);
			for (char next : graph.get(c)) if (indegree.merge(next, -1, Integer::sum) == 0) queue.offer(next);
		}
		return order.length() == graph.size() ? order.toString() : "";
	}

	public static void main(String[] args) {
		Check.eq("example", alienOrder(new String[] { "wrt", "wrf", "er", "ett", "rftt" }), "wertf");
		Check.eq("cycle", alienOrder(new String[] { "z", "x", "z" }), "");
		Check.eq("prefix trap", alienOrder(new String[] { "abc", "ab" }), "");
	}
}
