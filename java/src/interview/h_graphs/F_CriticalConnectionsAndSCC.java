package interview.h_graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import interview.Check;

/*
 * Advanced DFS (asked at senior / staff level, e.g. "find single points of failure in a network").
 *
 * 1. Bridges (Critical Connections) - Tarjan's low-link:
 *    disc[u] = DFS discovery time; low[u] = smallest disc reachable from u's subtree using
 *    at most one back edge. Tree edge (u, v) is a bridge if low[v] > disc[u]. O(V + E).
 *    (Articulation points use low[v] >= disc[u].)
 *
 * 2. Strongly Connected Components - Kosaraju:
 *    DFS once to get finish order; reverse all edges; DFS in decreasing finish order.
 *    Each tree of the second pass is one SCC. O(V + E).
 */
public class F_CriticalConnectionsAndSCC {

	private static int timer;

	static List<List<Integer>> criticalConnections(int n, int[][] edges) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
		for (int[] e : edges) {
			graph.get(e[0]).add(e[1]);
			graph.get(e[1]).add(e[0]);
		}
		int[] disc = new int[n], low = new int[n];
		Arrays.fill(disc, -1);
		List<List<Integer>> bridges = new ArrayList<>();
		timer = 0;
		for (int i = 0; i < n; i++) if (disc[i] == -1) bridgeDfs(i, -1, graph, disc, low, bridges);
		return bridges;
	}

	private static void bridgeDfs(int u, int parent, List<List<Integer>> g, int[] disc, int[] low, List<List<Integer>> out) {
		disc[u] = low[u] = timer++;
		for (int v : g.get(u)) {
			if (v == parent) continue;
			if (disc[v] == -1) {
				bridgeDfs(v, u, g, disc, low, out);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] > disc[u]) out.add(Arrays.asList(Math.min(u, v), Math.max(u, v)));
			} else {
				low[u] = Math.min(low[u], disc[v]); // back edge
			}
		}
	}

	static int countSCC(int n, int[][] directedEdges) {
		List<List<Integer>> g = new ArrayList<>(), rev = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<>());
			rev.add(new ArrayList<>());
		}
		for (int[] e : directedEdges) {
			g.get(e[0]).add(e[1]);
			rev.get(e[1]).add(e[0]);
		}
		boolean[] seen = new boolean[n];
		Deque<Integer> finishOrder = new ArrayDeque<>();
		for (int i = 0; i < n; i++) if (!seen[i]) fillOrder(i, g, seen, finishOrder);
		Arrays.fill(seen, false);
		int components = 0;
		while (!finishOrder.isEmpty()) {
			int u = finishOrder.pop();
			if (!seen[u]) {
				components++;
				mark(u, rev, seen);
			}
		}
		return components;
	}

	private static void fillOrder(int u, List<List<Integer>> g, boolean[] seen, Deque<Integer> order) {
		seen[u] = true;
		for (int v : g.get(u)) if (!seen[v]) fillOrder(v, g, seen, order);
		order.push(u);
	}

	private static void mark(int u, List<List<Integer>> g, boolean[] seen) {
		seen[u] = true;
		for (int v : g.get(u)) if (!seen[v]) mark(v, g, seen);
	}

	public static void main(String[] args) {
		Check.eq("bridges", criticalConnections(4, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 1, 3 } }),
				List.of(List.of(1, 3)));
		Check.eq("scc", countSCC(5, new int[][] { { 1, 0 }, { 0, 2 }, { 2, 1 }, { 0, 3 }, { 3, 4 } }), 3);
	}
}
