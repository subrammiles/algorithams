package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import common.Check;

/*
 * Maximum flow and bipartite matching (staff-level topics, but common in system-design flavoured
 * questions: assigning tasks to workers, network capacity, project selection).
 *
 *  - Ford-Fulkerson idea: while there is an augmenting path in the RESIDUAL graph, push the
 *    bottleneck capacity along it. Residual reverse edges let later paths "undo" earlier choices.
 *  - Edmonds-Karp = Ford-Fulkerson with BFS (shortest augmenting path): O(V * E^2).
 *  - Max-flow min-cut theorem: the max flow equals the capacity of the minimum s-t cut.
 *  - Bipartite matching = max flow with unit capacities; Kuhn's algorithm does it directly with
 *    augmenting-path DFS: O(V * E). (Hopcroft-Karp: O(E sqrt V).)
 */
public class H_MaxFlowAndMatching {

	static int maxFlow(int[][] capacity, int source, int sink) {
		int n = capacity.length;
		int[][] residual = new int[n][];
		for (int i = 0; i < n; i++) residual[i] = capacity[i].clone();
		int flow = 0;
		int[] parent = new int[n];
		while (true) {
			Arrays.fill(parent, -1);
			parent[source] = source;
			Deque<Integer> queue = new ArrayDeque<>();
			queue.add(source);
			while (!queue.isEmpty() && parent[sink] == -1) {
				int u = queue.poll();
				for (int v = 0; v < n; v++) {
					if (parent[v] == -1 && residual[u][v] > 0) {
						parent[v] = u;
						queue.add(v);
					}
				}
			}
			if (parent[sink] == -1) return flow; // no augmenting path left
			int bottleneck = Integer.MAX_VALUE;
			for (int v = sink; v != source; v = parent[v]) bottleneck = Math.min(bottleneck, residual[parent[v]][v]);
			for (int v = sink; v != source; v = parent[v]) {
				residual[parent[v]][v] -= bottleneck;
				residual[v][parent[v]] += bottleneck; // reverse edge: allows undoing
			}
			flow += bottleneck;
		}
	}

	/* adj[left] = right vertices it may be matched with. */
	static int maxBipartiteMatching(int[][] adj, int rightCount) {
		int[] matchOfRight = new int[rightCount];
		Arrays.fill(matchOfRight, -1);
		int matches = 0;
		for (int left = 0; left < adj.length; left++) {
			if (tryAugment(left, adj, matchOfRight, new boolean[rightCount])) matches++;
		}
		return matches;
	}

	private static boolean tryAugment(int left, int[][] adj, int[] matchOfRight, boolean[] seen) {
		for (int right : adj[left]) {
			if (seen[right]) continue;
			seen[right] = true;
			// right is free, or its current partner can be moved to another right vertex
			if (matchOfRight[right] == -1 || tryAugment(matchOfRight[right], adj, matchOfRight, seen)) {
				matchOfRight[right] = left;
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int[][] capacity = { { 0, 16, 13, 0, 0, 0 }, { 0, 0, 10, 12, 0, 0 }, { 0, 4, 0, 0, 14, 0 }, { 0, 0, 9, 0, 0, 20 }, { 0, 0, 0, 7, 0, 4 }, { 0, 0, 0, 0, 0, 0 } };
		Check.eq("max flow (CLRS)", maxFlow(capacity, 0, 5), 23);
		Check.eq("matching 2", maxBipartiteMatching(new int[][] { { 0, 1 }, { 0 }, { 0 } }, 2), 2);
		Check.eq("matching perfect", maxBipartiteMatching(new int[][] { { 0, 1 }, { 0, 2 }, { 1 } }, 3), 3);
	}
}
