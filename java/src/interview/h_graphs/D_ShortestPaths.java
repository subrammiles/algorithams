package interview.h_graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import interview.Check;

/*
 * Choosing a shortest path algorithm is a common senior-level discussion:
 *
 *  | Graph                         | Algorithm              | Time            |
 *  |-------------------------------|------------------------|-----------------|
 *  | Unweighted                    | BFS                    | O(V + E)        |
 *  | Weights 0 or 1                | 0-1 BFS (deque)        | O(V + E)        |
 *  | Non-negative weights          | Dijkstra (binary heap) | O(E log V)      |
 *  | Negative weights / <= K edges | Bellman-Ford           | O(V * E)        |
 *  | All pairs, small V            | Floyd-Warshall         | O(V^3)          |
 *  | DAG                           | Topo order + relax     | O(V + E)        |
 *
 * 1. Network Delay Time - Dijkstra with lazy deletion (skip stale heap entries).
 * 2. Cheapest Flights Within K Stops - Bellman-Ford limited to k+1 rounds. Copy the
 *    distance array each round so one round never uses edges relaxed in the same round.
 */
public class D_ShortestPaths {

	static int networkDelayTime(int[][] times, int n, int source) {
		List<List<int[]>> graph = new ArrayList<>();
		for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
		for (int[] t : times) graph.get(t[0]).add(new int[] { t[1], t[2] });
		int[] dist = new int[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[source] = 0;
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
		heap.offer(new int[] { source, 0 });
		while (!heap.isEmpty()) {
			int[] top = heap.poll();
			int u = top[0], d = top[1];
			if (d > dist[u]) continue; // stale entry
			for (int[] e : graph.get(u)) {
				if (d + e[1] < dist[e[0]]) {
					dist[e[0]] = d + e[1];
					heap.offer(new int[] { e[0], dist[e[0]] });
				}
			}
		}
		int max = 0;
		for (int i = 1; i <= n; i++) {
			if (dist[i] == Integer.MAX_VALUE) return -1;
			max = Math.max(max, dist[i]);
		}
		return max;
	}

	static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		int[] cost = new int[n];
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[src] = 0;
		for (int round = 0; round <= k; round++) {
			int[] next = cost.clone();
			for (int[] f : flights) {
				if (cost[f[0]] != Integer.MAX_VALUE && cost[f[0]] + f[2] < next[f[1]]) next[f[1]] = cost[f[0]] + f[2];
			}
			cost = next;
		}
		return cost[dst] == Integer.MAX_VALUE ? -1 : cost[dst];
	}

	public static void main(String[] args) {
		Check.eq("network delay", networkDelayTime(new int[][] { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } }, 4, 2), 2);
		Check.eq("unreachable", networkDelayTime(new int[][] { { 1, 2, 1 } }, 2, 2), -1);
		int[][] flights = { { 0, 1, 100 }, { 1, 2, 100 }, { 2, 0, 100 }, { 1, 3, 600 }, { 2, 3, 200 } };
		Check.eq("cheapest k=1", findCheapestPrice(4, flights, 0, 3, 1), 700);
		Check.eq("cheapest k=2", findCheapestPrice(4, flights, 0, 3, 2), 400);
	}
}
