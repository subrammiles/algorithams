package algorithms.graphs;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Multi-source BFS: push ALL sources at distance 0, then BFS once. Each cell gets the distance to
 * its NEAREST source in O(rows * cols) - instead of one BFS per source.
 *
 *  - 01 matrix: distance of each cell to the nearest 0.
 *  - Walls and gates: fill each empty room with the distance to the nearest gate.
 *  - Shortest bridge: DFS to mark the first island, then multi-source BFS from all its cells
 *    until the second island is reached.
 */
public class D_MultiSourceBFS {

	static final int[][] DIRS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
	static final int INF = Integer.MAX_VALUE;

	static int[][] updateMatrix(int[][] mat) {
		int rows = mat.length, cols = mat[0].length;
		int[][] dist = new int[rows][cols];
		Deque<int[]> queue = new ArrayDeque<>();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (mat[r][c] == 0) queue.add(new int[] { r, c });
				else dist[r][c] = -1; // unvisited
			}
		}
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			for (int[] d : DIRS) {
				int r = cell[0] + d[0], c = cell[1] + d[1];
				if (r >= 0 && c >= 0 && r < rows && c < cols && dist[r][c] == -1) {
					dist[r][c] = dist[cell[0]][cell[1]] + 1;
					queue.add(new int[] { r, c });
				}
			}
		}
		return dist;
	}

	/* -1 wall, 0 gate, INF empty room. Fills rooms in place. */
	static void wallsAndGates(int[][] rooms) {
		Deque<int[]> queue = new ArrayDeque<>();
		for (int r = 0; r < rooms.length; r++) for (int c = 0; c < rooms[0].length; c++) if (rooms[r][c] == 0) queue.add(new int[] { r, c });
		while (!queue.isEmpty()) {
			int[] cell = queue.poll();
			for (int[] d : DIRS) {
				int r = cell[0] + d[0], c = cell[1] + d[1];
				if (r >= 0 && c >= 0 && r < rooms.length && c < rooms[0].length && rooms[r][c] == INF) {
					rooms[r][c] = rooms[cell[0]][cell[1]] + 1;
					queue.add(new int[] { r, c });
				}
			}
		}
	}

	static int shortestBridge(int[][] grid) {
		int n = grid.length;
		int[][] g = new int[n][];
		for (int i = 0; i < n; i++) g[i] = grid[i].clone();
		Deque<int[]> queue = new ArrayDeque<>();
		outer:
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (g[r][c] == 1) {
					markIsland(g, r, c, queue);
					break outer;
				}
			}
		}
		for (int steps = 0; !queue.isEmpty(); steps++) {
			for (int size = queue.size(); size > 0; size--) {
				int[] cell = queue.poll();
				for (int[] d : DIRS) {
					int r = cell[0] + d[0], c = cell[1] + d[1];
					if (r < 0 || c < 0 || r >= n || c >= n || g[r][c] == 2) continue;
					if (g[r][c] == 1) return steps; // reached the second island
					g[r][c] = 2;
					queue.add(new int[] { r, c });
				}
			}
		}
		return -1;
	}

	private static void markIsland(int[][] g, int r, int c, Deque<int[]> queue) {
		Deque<int[]> stack = new ArrayDeque<>();
		stack.push(new int[] { r, c });
		g[r][c] = 2;
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			queue.add(cell);
			for (int[] d : DIRS) {
				int nr = cell[0] + d[0], nc = cell[1] + d[1];
				if (nr >= 0 && nc >= 0 && nr < g.length && nc < g.length && g[nr][nc] == 1) {
					g[nr][nc] = 2;
					stack.push(new int[] { nr, nc });
				}
			}
		}
	}

	public static void main(String[] args) {
		Check.eq("01 matrix", updateMatrix(new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } }), new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 2, 1 } });
		int[][] rooms = { { INF, -1, 0, INF }, { INF, INF, INF, -1 }, { INF, -1, INF, -1 }, { 0, -1, INF, INF } };
		wallsAndGates(rooms);
		Check.eq("walls and gates", rooms, new int[][] { { 3, -1, 0, 1 }, { 2, 2, 1, -1 }, { 1, -1, 2, -1 }, { 0, -1, 3, 4 } });
		Check.eq("bridge 1", shortestBridge(new int[][] { { 0, 1 }, { 1, 0 } }), 1);
		Check.eq("bridge 2", shortestBridge(new int[][] { { 0, 1, 0 }, { 0, 0, 0 }, { 0, 0, 1 } }), 2);
		Check.eq("bridge ring", shortestBridge(new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } }), 1);
	}
}
