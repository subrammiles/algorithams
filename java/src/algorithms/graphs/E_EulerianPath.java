package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import common.Check;

/*
 * Eulerian path: use EVERY EDGE exactly once (Hamiltonian = every vertex once, which is NP-hard;
 * Eulerian is linear time).
 *
 * Existence (directed): at most one vertex with out - in = 1 (start), at most one with in - out = 1
 * (end), all others balanced, and the edges connected.
 *
 * Hierholzer's algorithm: walk unused edges until stuck; when stuck, the vertex is appended to the
 * route; the route is built in REVERSE. O(E log E) here because of the lexical ordering.
 *
 * Problem: Reconstruct Itinerary - use all tickets once starting at JFK, lexically smallest route.
 * Same idea: De Bruijn sequences / cracking the safe, DNA fragment assembly.
 */
public class E_EulerianPath {

	static List<String> findItinerary(List<List<String>> tickets) {
		Map<String, PriorityQueue<String>> graph = new HashMap<>();
		for (List<String> t : tickets) graph.computeIfAbsent(t.get(0), k -> new PriorityQueue<>()).add(t.get(1));
		List<String> route = new ArrayList<>();
		Deque<String> stack = new ArrayDeque<>();
		stack.push("JFK");
		while (!stack.isEmpty()) {
			PriorityQueue<String> next = graph.get(stack.peek());
			if (next != null && !next.isEmpty()) {
				stack.push(next.poll()); // follow the smallest unused edge
			} else {
				route.add(stack.pop()); // dead end: this vertex comes last among the remaining
			}
		}
		Collections.reverse(route);
		return route;
	}

	public static void main(String[] args) {
		Check.eq("itinerary", findItinerary(List.of(List.of("MUC", "LHR"), List.of("JFK", "MUC"), List.of("SFO", "SJC"), List.of("LHR", "SFO"))),
				List.of("JFK", "MUC", "LHR", "SFO", "SJC"));
		Check.eq("itinerary lexical", findItinerary(List.of(List.of("JFK", "SFO"), List.of("JFK", "ATL"), List.of("SFO", "ATL"), List.of("ATL", "JFK"), List.of("ATL", "SFO"))),
				List.of("JFK", "ATL", "JFK", "SFO", "ATL", "SFO"));
		Check.eq("dead end first", findItinerary(List.of(List.of("JFK", "KUL"), List.of("JFK", "NRT"), List.of("NRT", "JFK"))),
				List.of("JFK", "NRT", "JFK", "KUL"));
	}
}
