package interview.h_graphs;

import java.util.ArrayDeque;
import java.util.Deque;

import common.Check;

/*
 * Grid graphs: every cell is a node, neighbours are the 4 directions.
 *
 * 1. Number of islands: flood fill each unvisited land cell (DFS or BFS). O(rows*cols).
 * 2. Rotting oranges: MULTI-SOURCE BFS - push every rotten orange at time 0, BFS level = minute.
 *
 * Senior notes:
 *  - Recursive DFS on a 1000x1000 grid can overflow the stack; prefer BFS / explicit stack.
 *  - Islands added online (Number of Islands II) -> union-find (see i_unionfind).
 */
public class A_NumberOfIslands {

	static final int[][] DIRS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	static int numIslands(char[][] grid) {
		int rows = grid.length, cols = grid[0].length, count = 0;
		boolean[][] seen = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] != '1' || seen[r][c]) continue;
				count++;
				Deque<int[]> stack = new ArrayDeque<>();
				stack.push(new int[] { r, c });
				seen[r][c] = true;
				while (!stack.isEmpty()) {
					int[] cell = stack.pop();
					for (int[] d : DIRS) {
						int nr = cell[0] + d[0], nc = cell[1] + d[1];
						if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1' && !seen[nr][nc]) {
							seen[nr][nc] = true;
							stack.push(new int[] { nr, nc });
						}
					}
				}
			}
		}
		return count;
	}

	/* 0 empty, 1 fresh, 2 rotten. Minutes until no fresh orange remains, or -1. */
	static int orangesRotting(int[][] grid) {
		int rows = grid.length, cols = grid[0].length, fresh = 0;
		Deque<int[]> queue = new ArrayDeque<>();
		int[][] g = new int[rows][];
		for (int r = 0; r < rows; r++) {
			g[r] = grid[r].clone();
			for (int c = 0; c < cols; c++) {
				if (g[r][c] == 2) queue.offer(new int[] { r, c });
				else if (g[r][c] == 1) fresh++;
			}
		}
		int minutes = 0;
		while (!queue.isEmpty() && fresh > 0) {
			minutes++;
			for (int size = queue.size(); size > 0; size--) {
				int[] cell = queue.poll();
				for (int[] d : DIRS) {
					int nr = cell[0] + d[0], nc = cell[1] + d[1];
					if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && g[nr][nc] == 1) {
						g[nr][nc] = 2;
						fresh--;
						queue.offer(new int[] { nr, nc });
					}
				}
			}
		}
		return fresh == 0 ? minutes : -1;
	}

	public static void main(String[] args) {
		char[][] grid = { "11000".toCharArray(), "11000".toCharArray(), "00100".toCharArray(), "00011".toCharArray() };
		Check.eq("islands", numIslands(grid), 3);
		Check.eq("rotting", orangesRotting(new int[][] { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } }), 4);
		Check.eq("rotting impossible", orangesRotting(new int[][] { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } }), -1);
	}
}
