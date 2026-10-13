package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import common.Check;

/*
 * Tarjan's low-link technique (bridges are in interview/h_graphs/F_CriticalConnectionsAndSCC).
 *
 * disc[u] = DFS discovery time; low[u] = smallest disc reachable from u's subtree with one back edge.
 *
 *  - Articulation point (cut vertex), undirected:
 *      root of the DFS tree with >= 2 children, or
 *      non-root u with a child v where low[v] >= disc[u] (v's subtree cannot climb above u).
 *  - Strongly connected components, directed, ONE DFS pass (Kosaraju needs two):
 *      keep a stack of visited vertices; when low[u] == disc[u], u is the root of an SCC -
 *      pop the stack down to u.
 * Both O(V + E).
 */
public class F_ArticulationPointsAndTarjanSCC {

	private static int timer;

	static List<Integer> articulationPoints(int n, int[][] edges) {
		List<List<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) g.add(new ArrayList<>());
		for (int[] e : edges) {
			g.get(e[0]).add(e[1]);
			g.get(e[1]).add(e[0]);
		}
		int[] disc = new int[n], low = new int[n];
		Arrays.fill(disc, -1);
		boolean[] isCut = new boolean[n];
		timer = 0;
		for (int i = 0; i < n; i++) if (disc[i] == -1) cutDfs(i, -1, g, disc, low, isCut);
		List<Integer> out = new ArrayList<>();
		for (int i = 0; i < n; i++) if (isCut[i]) out.add(i);
		return out;
	}

	private static void cutDfs(int u, int parent, List<List<Integer>> g, int[] disc, int[] low, boolean[] isCut) {
		disc[u] = low[u] = timer++;
		int children = 0;
		for (int v : g.get(u)) {
			if (v == parent) continue;
			if (disc[v] == -1) {
				children++;
				cutDfs(v, u, g, disc, low, isCut);
				low[u] = Math.min(low[u], low[v]);
				if (parent != -1 && low[v] >= disc[u]) isCut[u] = true;
			} else {
				low[u] = Math.min(low[u], disc[v]);
			}
		}
		if (parent == -1 && children >= 2) isCut[u] = true;
	}

	static List<List<Integer>> tarjanSCC(int n, int[][] edges) {
		List<List<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) g.add(new ArrayList<>());
		for (int[] e : edges) g.get(e[0]).add(e[1]);
		int[] disc = new int[n], low = new int[n];
		Arrays.fill(disc, -1);
		boolean[] onStack = new boolean[n];
		Deque<Integer> stack = new ArrayDeque<>();
		List<List<Integer>> components = new ArrayList<>();
		timer = 0;
		for (int i = 0; i < n; i++) if (disc[i] == -1) sccDfs(i, g, disc, low, onStack, stack, components);
		for (List<Integer> c : components) c.sort(null);
		components.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));
		return components;
	}

	private static void sccDfs(int u, List<List<Integer>> g, int[] disc, int[] low, boolean[] onStack, Deque<Integer> stack,
			List<List<Integer>> components) {
		disc[u] = low[u] = timer++;
		stack.push(u);
		onStack[u] = true;
		for (int v : g.get(u)) {
			if (disc[v] == -1) {
				sccDfs(v, g, disc, low, onStack, stack, components);
				low[u] = Math.min(low[u], low[v]);
			} else if (onStack[v]) {
				low[u] = Math.min(low[u], disc[v]);
			}
		}
		if (low[u] == disc[u]) { // u is the root of a component
			List<Integer> component = new ArrayList<>();
			int v;
			do {
				v = stack.pop();
				onStack[v] = false;
				component.add(v);
			} while (v != u);
			components.add(component);
		}
	}

	public static void main(String[] args) {
		int[][] edges = { { 1, 0 }, { 0, 2 }, { 2, 1 }, { 0, 3 }, { 3, 4 } };
		Check.eq("articulation points", articulationPoints(5, edges), List.of(0, 3));
		Check.eq("no articulation in a cycle", articulationPoints(3, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 0 } }), List.of());
		Check.eq("tarjan scc", tarjanSCC(5, edges), List.of(List.of(0, 1, 2), List.of(3), List.of(4)));
	}
}
