package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

import common.Check;

/*
 * Shortest-path variants that change WHAT is minimised (Dijkstra/Bellman-Ford basics are in
 * interview/h_graphs/D_ShortestPaths).
 *
 *  - 0-1 BFS: edge weights are only 0 or 1 -> deque; push 0-edges to the FRONT, 1-edges to the back.
 *    O(V + E). Example: minimum obstacles to remove to cross a grid.
 *  - Max probability path: Dijkstra with "max product" instead of "min sum" (valid because all
 *    probabilities are <= 1, so a path never improves by getting longer).
 *  - Swim in rising water (minimax path): path cost = the MAX cell on it; Dijkstra on that cost.
 *    (Also solvable with binary search + BFS, or union-find adding cells in increasing height.)
 *  - A* search: Dijkstra ordered by g + h, where h is an admissible heuristic (never overestimates;
 *    Manhattan distance on a 4-direction grid). Explores far fewer nodes toward the goal.
 */
public class C_ShortestPathVariants {

	static final int[][] DIRS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	static int minimumObstacles(int[][] grid) {
		int rows = grid.length, cols = grid[0].length;
		int[][] dist = new int[rows][cols];
		for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
		Deque<int[]> dq = new ArrayDeque<>();
		dist[0][0] = 0;
		dq.add(new int[] { 0, 0 });
		while (!dq.isEmpty()) {
			int[] cell = dq.pollFirst();
			for (int[] d : DIRS) {
				int r = cell[0] + d[0], c = cell[1] + d[1];
				if (r < 0 || c < 0 || r >= rows || c >= cols) continue;
				int nd = dist[cell[0]][cell[1]] + grid[r][c];
				if (nd < dist[r][c]) {
					dist[r][c] = nd;
					if (grid[r][c] == 0) dq.addFirst(new int[] { r, c });
					else dq.addLast(new int[] { r, c });
				}
			}
		}
		return dist[rows - 1][cols - 1];
	}

	static double maxProbability(int n, int[][] edges, double[] prob, int start, int end) {
		List<List<double[]>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) g.add(new ArrayList<>());
		for (int i = 0; i < edges.length; i++) {
			g.get(edges[i][0]).add(new double[] { edges[i][1], prob[i] });
			g.get(edges[i][1]).add(new double[] { edges[i][0], prob[i] });
		}
		double[] best = new double[n];
		best[start] = 1;
		PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[1], a[1])); // max-heap
		pq.add(new double[] { start, 1 });
		while (!pq.isEmpty()) {
			double[] top = pq.poll();
			int u = (int) top[0];
			if (u == end) return top[1];
			if (top[1] < best[u]) continue;
			for (double[] e : g.get(u)) {
				int v = (int) e[0];
				if (top[1] * e[1] > best[v]) {
					best[v] = top[1] * e[1];
					pq.add(new double[] { v, best[v] });
				}
			}
		}
		return 0;
	}

	static int swimInWater(int[][] grid) {
		int n = grid.length;
		boolean[][] seen = new boolean[n][n];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
		pq.add(new int[] { grid[0][0], 0, 0 });
		seen[0][0] = true;
		while (!pq.isEmpty()) {
			int[] top = pq.poll();
			if (top[1] == n - 1 && top[2] == n - 1) return top[0];
			for (int[] d : DIRS) {
				int r = top[1] + d[0], c = top[2] + d[1];
				if (r >= 0 && c >= 0 && r < n && c < n && !seen[r][c]) {
					seen[r][c] = true;
					pq.add(new int[] { Math.max(top[0], grid[r][c]), r, c });
				}
			}
		}
		return -1;
	}

	/* Shortest number of steps from top-left to bottom-right; '#' is a wall. -1 if unreachable. */
	static int aStar(String[] grid) {
		int rows = grid.length, cols = grid[0].length(), tr = rows - 1, tc = cols - 1;
		if (grid[0].charAt(0) == '#') return -1;
		int[][] g = new int[rows][cols];
		for (int[] row : g) Arrays.fill(row, Integer.MAX_VALUE);
		g[0][0] = 0;
		PriorityQueue<int[]> open = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0])); // [f, r, c]
		open.add(new int[] { tr + tc, 0, 0 });
		while (!open.isEmpty()) {
			int[] top = open.poll();
			int r = top[1], c = top[2];
			if (r == tr && c == tc) return g[r][c];
			if (top[0] > g[r][c] + (tr - r) + (tc - c)) continue; // stale
			for (int[] d : DIRS) {
				int nr = r + d[0], nc = c + d[1];
				if (nr < 0 || nc < 0 || nr >= rows || nc >= cols || grid[nr].charAt(nc) == '#') continue;
				if (g[r][c] + 1 < g[nr][nc]) {
					g[nr][nc] = g[r][c] + 1;
					open.add(new int[] { g[nr][nc] + (tr - nr) + (tc - nc), nr, nc });
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Check.eq("0-1 bfs", minimumObstacles(new int[][] { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } }), 2);
		Check.eq("0-1 bfs free path", minimumObstacles(new int[][] { { 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0 } }), 0);
		int[][] edges = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		Check.near("max probability", maxProbability(3, edges, new double[] { 0.5, 0.5, 0.2 }, 0, 2), 0.25);
		Check.near("max probability direct", maxProbability(3, edges, new double[] { 0.5, 0.5, 0.3 }, 0, 2), 0.3);
		Check.eq("swim 2x2", swimInWater(new int[][] { { 0, 2 }, { 1, 3 } }), 3);
		Check.eq("swim spiral", swimInWater(new int[][] { { 0, 1, 2, 3, 4 }, { 24, 23, 22, 21, 5 }, { 12, 13, 14, 15, 16 }, { 11, 17, 18, 19, 20 }, { 10, 9, 8, 7, 6 } }), 16);
		Check.eq("a* open", aStar(new String[] { ".....", ".###.", "...#.", "##.#.", "....." }), 8);
		Check.eq("a* snake detour", aStar(new String[] { ".....", "####.", ".....", ".####", "....." }), 16);
		Check.eq("a* blocked", aStar(new String[] { ".#", "#." }), -1);
	}
}
