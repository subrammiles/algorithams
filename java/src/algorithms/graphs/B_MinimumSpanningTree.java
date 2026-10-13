package algorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Check;

/*
 * Minimum spanning tree on "Min Cost to Connect All Points" (Manhattan distance between points).
 * The graph is COMPLETE (every pair is an edge), so the choice of algorithm matters:
 *
 *  - Prim with an array (no heap): O(V^2). Best for dense graphs like this one.
 *  - Kruskal: sort all V^2/2 edges, union-find. O(V^2 log V).
 *  - Prim with a binary heap: O(E log V) = O(V^2 log V) here; better for sparse graphs.
 *
 * Cut property (why greedy works): the cheapest edge crossing any cut belongs to some MST.
 */
public class B_MinimumSpanningTree {

	private static int dist(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}

	static int minCostPrim(int[][] points) {
		int n = points.length, total = 0;
		int[] best = new int[n]; // cheapest edge from the tree to each vertex
		Arrays.fill(best, Integer.MAX_VALUE);
		boolean[] inTree = new boolean[n];
		best[0] = 0;
		for (int step = 0; step < n; step++) {
			int u = -1;
			for (int v = 0; v < n; v++) if (!inTree[v] && (u == -1 || best[v] < best[u])) u = v;
			inTree[u] = true;
			total += best[u];
			for (int v = 0; v < n; v++) if (!inTree[v]) best[v] = Math.min(best[v], dist(points[u], points[v]));
		}
		return total;
	}

	static int minCostKruskal(int[][] points) {
		int n = points.length;
		List<int[]> edges = new ArrayList<>();
		for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) edges.add(new int[] { dist(points[i], points[j]), i, j });
		edges.sort((a, b) -> Integer.compare(a[0], b[0]));
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) parent[i] = i;
		int total = 0, used = 0;
		for (int[] e : edges) {
			int a = find(parent, e[1]), b = find(parent, e[2]);
			if (a != b) {
				parent[a] = b;
				total += e[0];
				if (++used == n - 1) break;
			}
		}
		return total;
	}

	private static int find(int[] parent, int x) {
		while (parent[x] != x) {
			parent[x] = parent[parent[x]];
			x = parent[x];
		}
		return x;
	}

	public static void main(String[] args) {
		int[][] points = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };
		Check.eq("prim", minCostPrim(points), 20);
		Check.eq("kruskal", minCostKruskal(points), 20);
		int[][] points2 = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		Check.eq("prim 2", minCostPrim(points2), 18);
		Check.eq("kruskal 2", minCostKruskal(points2), 18);
		Check.eq("single point", minCostPrim(new int[][] { { 1, 1 } }), 0);
	}
}
